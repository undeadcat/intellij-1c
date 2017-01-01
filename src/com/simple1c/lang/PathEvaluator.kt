package com.simple1c.lang

import com.intellij.psi.PsiFile
import coreUtils.equalsIgnoreCase

private val idPropertyName = "Ссылка"

fun evaluatePath(file: PsiFile, schemaStore: ISchemaStore, context: QueryContext, path: List<String>): List<String> {
    fun evalChildPath(segments: List<String>, schema: TableSchema): List<PropertyInfo> {
        if (segments.isEmpty())
            return emptyList()
        val first = segments[0]
        val remainingPath = segments.subList(1, segments.size)
        if (remainingPath.isEmpty())
            return schema.properties.filter { it.name.startsWith(first, true) }
        if (first.equalsIgnoreCase(idPropertyName))
            return evalChildPath(remainingPath, schema)
        return schema.properties.filter { it.name.equalsIgnoreCase(first) }
                .flatMap { it.referencedTables }
                .flatMap { childTable ->
                    val childSchema = schemaStore.getSchemaOrNull(file, childTable)
                    if (childSchema != null)
                        evalChildPath(remainingPath, childSchema)
                    else emptyList()
                }
    }

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
        return evalChildPath(tablePath, rootSchema).map { it.name }
    }
    return context.getSources()
            .flatMap { evalChildPath(path, it.schema) }
            .map { it.name }

}