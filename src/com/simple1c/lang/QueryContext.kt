package com.simple1c.lang

import com.intellij.psi.util.PsiTreeUtil
import coreUtils.equalsIgnoreCase
import generated.*
import java.util.*

sealed class QuerySource(val schema: TableSchema) {
    class Table(val alias: String?, schema: TableSchema) : QuerySource(schema)
    class Subquery(schema: TableSchema) : QuerySource(schema)
}

data class QueryContext private constructor(
        private val sources: ArrayList<QuerySource> = arrayListOf(),
        private val parent: QueryContext? = null) {

    private fun child(): QueryContext {
        return QueryContext(sources = arrayListOf(), parent = this)
    }

    fun resolve(name: String): TableSchema? {
        return sources.firstOrNull {
            when (it) {
                is QuerySource.Table -> it.alias.orEmpty().equalsIgnoreCase(name) || it.schema.name.equalsIgnoreCase(name)
                is QuerySource.Subquery -> it.schema.name.equalsIgnoreCase(name)
            }
        }?.schema ?: parent?.resolve(name)
    }

    fun getSources(): List<QuerySource> {
        return sources.plus(parent?.getSources() ?: emptyList())
    }

    private fun registerSubquery(schema: TableSchema) {
        sources.add(QuerySource.Subquery(schema))
    }

    private fun registerTable(schema: TableSchema, alias: String?) {
        sources.add(QuerySource.Table(alias, schema))
    }

    companion object {
        fun createForQuery(sqlQuery: SqlQuery, resolver: (String) -> TableSchema?): QueryContext {
            val visitor = ContextVisitor(sqlQuery, resolver)
            val rootQuery = PsiTreeUtil.getParentOfType(sqlQuery, RootQuery::class.java)
            rootQuery!!.accept(visitor)
            return visitor.result!!
        }

        class ContextVisitor(private val targetQuery: SqlQuery, private val resolver: (String) -> TableSchema?) : RecursiveVisitor() {
            var current: QueryContext? = null
            var result: QueryContext? = null

            override fun visitTableDeclaration(o: TableDeclaration) {
                super.visitTableDeclaration(o)
                val context = current!!
                val schema = resolver(o.tableName.text)
                if (schema == null)
                    return
                context.registerTable(schema, o.alias?.text)
            }

            override fun visitSubqueryTable(o: SubqueryTable) {
                val parent = current
                val subqueryContext = QueryContext()
                current = subqueryContext
                val query = o.subquery.sqlQuery
                if (query != null)
                    super.visitSqlQuery(query)
                current = parent
                val schema = createSubquerySchema(o, subqueryContext)
                if (schema != null)
                    current!!.registerSubquery(schema)
            }

            override fun visitSqlQuery(o: SqlQuery) {
                val previous = current
                current = previous?.child() ?: QueryContext()
                super.visitSqlQuery(o)
                if (o == targetQuery && result == null) {
                    result = current
                }
                current = previous
            }

            private fun createSubquerySchema(subquery: SubqueryTable, subqueryContext: QueryContext): TableSchema? {
                val query = subquery.subquery.sqlQuery
                if (query == null)
                    return null
                val selectList = query.selectStatement.selectionList
                if (selectList == null)
                    return null
                val columns = if (selectList.isSelectAll()) {
                    subqueryContext.sources
                            .flatMap { it.schema.properties }
                } else {
                    selectList.selectionItemList
                            .map { it.alias?.text ?: extractColumnNameFromExpressionOrNull(it.expression) }
                            .filterNotNull()
                            .map { PropertyInfo(it, emptyList()) }
                }
                return TableSchema(subquery.alias!!.text, columns, TableType.Main)
            }
        }

        private fun extractColumnNameFromExpressionOrNull(selectionItem: Expression): String? {

            val identifiers = arrayListOf<Identifier>()
            val visitor = object : RecursiveVisitor() {
                override fun visitIdentifier(o: Identifier) {
                    super.visitIdentifier(o)
                    identifiers.add(o)
                }
            }
            selectionItem.accept(visitor)
            if (identifiers.size != 1)
                return null
            return identifiers.single()
                    .text.split(".")
                    .last()

        }
    }
}