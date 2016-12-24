package com.simple1c.lang

import com.intellij.psi.util.PsiTreeUtil
import generated.*
import java.util.*

data class QueryContext(
        private val tables: ArrayList<TableSchema> = arrayListOf(),
        private val subqueries: ArrayList<TableSchema> = arrayListOf(),
        private val aliases: HashMap<String, String> = hashMapOf(),
        private val parent: QueryContext? = null) {

    fun child(): QueryContext {
        return QueryContext(parent = this)
    }

    fun resolve(tableName: String): TableSchema? {
        return subqueries.firstOrNull { it.name == tableName }
                ?: tables.firstOrNull { it.name == aliases[tableName] ?: tableName }
                ?: parent?.resolve(tableName)
    }

    fun getUsedTables(): Iterable<String> {
        return tables.map { it.name }.plus(parent?.getUsedTables() ?: emptyList())
    }

    fun getIntroducedNames(): Iterable<String> {
        return aliases.keys.plus(subqueries.map { it.name }).plus(parent?.getIntroducedNames() ?: emptyList())
    }

    private fun registerSubquery(schema: TableSchema) {
        subqueries.add(schema)
    }

    private fun registerTable(schema: TableSchema, alias: String?) {
        tables.add(schema)
        if (alias != null) {
            aliases[alias] = schema.name
        }
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
                super.visitSqlQuery(o.subquery.sqlQuery!!)
                current = parent
                current!!.registerSubquery(createSubquerySchema(o, subqueryContext))
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

            private fun createSubquerySchema(subquery: SubqueryTable, subqueryContext: QueryContext): TableSchema {
                val selectList = subquery.subquery.sqlQuery!!
                        .selectStatement
                        .selectionList!!
                val columns = if (selectList.isSelectAll()) {
                    subqueryContext.getUsedTables()
                            .plus(subqueryContext.subqueries.map { it.name })
                            .flatMap { subqueryContext.resolve(it)?.properties ?: emptyList() }
                } else {
                    selectList.selectionItemList
                            .map { it.alias?.text ?: extractColumnNameFromExpressionOrNull(it.expression) }
                            .filterNotNull()
                            .map { PropertyInfo(it, emptyList()) }
                }
                return TableSchema(subquery.alias!!.text, columns)
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