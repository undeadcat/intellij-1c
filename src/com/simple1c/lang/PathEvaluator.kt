package com.simple1c.lang

import com.intellij.psi.PsiFile
import coreUtils.equalsIgnoreCase

class PathEvaluator(private val schemaStore: ISchemaStore) {
    private val idPropertyName = "Ссылка"

    fun eval(file: PsiFile, context: QueryContext, path: List<String>): List<String> {
        if (path.isEmpty())
            throw RuntimeException("Expected non-empty path")

        val candidateNames = path.withIndex().map { path.take(it.index + 1) }
        val rootTable = candidateNames
                .map { Pair(it, context.resolve(it.joinToString("."))) }
                .filter { it.second != null }
                .firstOrNull()
        if (rootTable != null) {
            val rootSchema = rootTable.second!!
            val tablePath = path.subList(rootTable.first.size, path.size)
            return evalChildPath(file, tablePath, rootSchema).map { it.name }
        }
        return context.getSources()
                .flatMap { evalChildPath(file, path, it.schema) }
                .map { it.name }
    }

    private fun evalChildPath(file: PsiFile, segments: List<String>, schema: TableSchema): List<PropertyInfo> {
        fun evalWithSchemaName(name: String, path: List<String>): List<PropertyInfo> {
            val childSchema = schemaStore.getSchemaOrNull(file, name)
            if (childSchema != null)
                return evalChildPath(file, path, childSchema)
            else return emptyList()
        }
        if (segments.isEmpty())
            return emptyList()
        val first = segments[0]
        val remainingPath = segments.subList(1, segments.size)
        if (remainingPath.isEmpty())
            return schema.properties.filter { it.name.startsWith(first, true) }
        if (first.equalsIgnoreCase(idPropertyName)) {
            if (schema.type == TableType.TableSection) {
                return evalWithSchemaName(getMainTableName(schema.name), remainingPath)
            } else {
                return evalChildPath(file, remainingPath, schema)
            }
        }
        return schema.properties.filter { it.name.equalsIgnoreCase(first) }
                .flatMap { it.referencedTables }
                .flatMap { childTable -> evalWithSchemaName(childTable, remainingPath) }
    }

    private fun getMainTableName(name: String): String {
        val lastDot = name.lastIndexOf('.')
        if (lastDot <= 0)
            throw RuntimeException("Assertion failed. Expected TableSection name to contain a '.': $name")
        return name.substring(0, lastDot)
    }
}

