import com.intellij.codeInsight.completion.CompletionType
import com.intellij.openapi.application.ApplicationManager
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase
import com.simple1c.lang.ISchemaStore
import com.simple1c.lang.PropertyInfo
import com.simple1c.lang.TableSchema
import org.hamcrest.core.Is
import org.junit.Assert
import org.picocontainer.MutablePicoContainer

//TODO. Ordering.
//      Have aliases -> first aliases, then columns
//      No aliases -> first columns, then tables
//TODO. do we need fully qualified names?

//TODO. trigger completion before bracket.
//todo. TableSection. Do I need to do anything?
class CompletionTest : LightCodeInsightFixtureTestCase() {

    companion object {
        private val schemaStore = FakeSchemaStore()
    }

    override fun setUp() {
        super.setUp()
        registerImplementation(schemaStore, ISchemaStore::class.java)
        schemaStore.clear()
    }

    fun testCompleteTableNames() {
        schemaStore.addColumns("Table1", "TColumn1")
        schemaStore.addColumns("Table2")
        schemaStore.addColumns("Other")
        testEquivalentTo("select * from <caret>", listOf("Other", "Table1", "Table2"))
        testEquivalentTo("select * from Tab<caret>", listOf("Table1", "Table2"))
    }

    fun testAfterSelectKeyword_completeTablesAndColumns() {
        schemaStore.addColumns("Table", "Column1", "Column2")
        schemaStore.addColumns("T", "TC1", "TC2")
        testEquivalentTo("select <caret>", listOf("T", "Table", "Column1", "Column2", "TC1", "TC2"))
        testEquivalentTo("select T<caret>", listOf("T", "Table", "TC1", "TC2"))
    }

    fun testCompleteTableNamesAfterJoinKeyword() {
        schemaStore.addColumns("Table1", "TColumn1")
        schemaStore.addColumns("Table2", "TColumn2")
        testEquivalentTo("select * from Table1 join T<caret>", listOf("Table1", "Table2"))
        testEquivalentTo("select * from Table1 join <caret>", listOf("Table1", "Table2"))
    }

    fun _testIdentifierStartsWithFullyQualifiedTable_ResolveColumnsFromTable() {
        schemaStore.addColumns("Справочник.Сотрудники", "Column1")
        schemaStore.addColumns("Справочник.Контрагенты", "Column2")
        testEquivalentTo("select Справочник.Контрагенты.<caret> from Справочник.Сотрудники join Справочник.Контрагенты", listOf("Column2"))
    }

    fun testCannotResolveAlias() {
        schemaStore.addColumns("Table1", "Column1")
        schemaStore.addColumns("Table2", "Column2")
        testEquivalentTo("select * from Table1 t where d.<caret>", emptyList())
    }

    fun testNameStartsWithAlias_ResolveColumnsFromAliasedTable() {
        schemaStore.addColumns("Table1", "Column1", "Another1", "Another2")
        schemaStore.addColumns("Table2", "Column2")
        testEquivalentTo("select * from Table1 t1 where t1.<caret>", listOf("Column1", "Another1", "Another2"))
        testEquivalentTo("select * from Table1 t1 where t1.A<caret>", listOf("Another1", "Another2"))
    }

    fun testHasDefinedAliases_IncludeInCompletion() {
        schemaStore.addColumns("Table", "Column1", "Column2")
        schemaStore.addColumns("Table2", "Column3")
        testEquivalentTo("select * from Table t where <caret>", listOf("t", "Column1", "Column2"))
        testEquivalentTo("select * from Table t where t.<caret>", listOf("Column1", "Column2"))
    }

    fun testUnqualifiedColumnName_ResolveFromDefinedTables() {
        schemaStore.addColumns("Table1", "Column1")
        schemaStore.addColumns("Table2", "Column2")
        schemaStore.addColumns("Table3", "Column3")
        testEquivalentTo("select * from Table1 " +
                "left join Table2 on " +
                "t1.Id = t2.Id " +
                "where <caret>", listOf("Column1", "Column2"))
    }

    fun testNestedTables() {
        schemaStore.addColumns("Документ.ПоступлениеНаРасчетныйСчет",
                PropertyInfo("Контрагент", listOf("Справочник.Контрагенты", "Справочник.ФизическиеЛица")))
        schemaStore.addColumns("Справочник.Контрагенты",
                PropertyInfo("Владелец", listOf("Справочник.Контрагенты")),
                PropertyInfo("ИНН", emptyList()))
        schemaStore.addColumns("Справочник.ФизическиеЛица",
                PropertyInfo("Фамилия", emptyList()),
                PropertyInfo("Имя", emptyList()),
                PropertyInfo("Отчество", emptyList()))
        testEquivalentTo("select * from Документ.ПоступлениеНаРасчетныйСчет d where d.<caret>", listOf("Контрагент"))
        testEquivalentTo("select * from Документ.ПоступлениеНаРасчетныйСчет d where d.Контрагент.<caret>", listOf("Владелец", "ИНН", "Фамилия", "Имя", "Отчество"))
        testEquivalentTo("select * from Документ.ПоступлениеНаРасчетныйСчет d where d.Контрагент.Владелец.<caret>", listOf("Владелец", "ИНН"))
    }

    fun testNestedTableWithQualifiedName() {
        schemaStore.addColumns("Документ.ПоступлениеНаРасчетныйСчет",
                PropertyInfo("Контрагент", listOf("Справочник.Контрагенты")))
        schemaStore.addColumns("Справочник.Контрагенты",
                PropertyInfo("ИНН", emptyList()))
        testEquivalentTo("select * from Документ.ПоступлениеНаРасчетныйСчет d where Документ.ПоступлениеНаРасчетныйСчет.Контрагент.<caret>", listOf("ИНН"))
    }

    fun testSubqueryCanReferToItemsFromOuterQuery() {
        schemaStore.addColumns("table1", "id1", "table2Key")
        schemaStore.addColumns("table2", "id2", "table3Key")
        schemaStore.addColumns("table3", "id3", "table1Key")
        testEquivalentTo("""SELECT *
FROM table1 t1
WHERE table2Key in
      (SELECT id
       FROM table2 t2
       WHERE table3Key in
                 (SELECT id
                  FROM table3 t3
                  WHERE id = t2.table3Key AND table1Key = t1.table3Key)
        AND i<caret> )""", listOf("id1", "id2"))

        testEquivalentTo("""SELECT *
FROM table1 t1
WHERE table2Key in
      (SELECT id
       FROM table2 t2
       WHERE table3Key in
                 (SELECT id
                  FROM table3 t3
                  WHERE id = t2.table3Key AND table1Key = i<caret> ))""", listOf("id3", "id2", "id1"))
    }

    fun testCannotReferToTableUsedInSubqueryOutsideScope() {
        schemaStore.addColumns("table1", "id1", "table1Column")
        schemaStore.addColumns("table2", "id2", "table2Column")
        testEquivalentTo("select * from " +
                "table1 t1 " +
                "where table1Column in (select id2 from table2 t2) " +
                "and <caret>", listOf("t1", "id1", "table1Column"))

    }

    fun testSelectFromSubquery_NamesMustBeQualifiedWithAlias() {
        schemaStore.addColumns("table2", "a", "b", "c")
        //TODO. should suggest qualified names
        testEquivalentTo("select * from (select a as alias1, b as alias2 from table2) t where <caret>", listOf("t"))
        testEquivalentTo("select * from (select a as alias1, b as alias2 from table2) t where t.<caret>", listOf("alias1", "alias2"))
    }

    fun testSelectFromSubqueryWithWildcard() {
        schemaStore.addColumns("table2", "a", "b", "c")
        schemaStore.addColumns("table3", "d", "e", "f")
        testEquivalentTo("""select * from
            (select * from table2 t2
            join (select * from table3) t3 on t2.id = t3.id) t
            where t.<caret>""", listOf("a", "b", "c", "d", "e", "f"))
    }

    fun testSelectFromSubquery_TryExtractColumnNamesFromExpressions() {
        schemaStore.addColumns("table2", "a", "b", "c")
        testEquivalentTo("""select * from
            (select a, sum(b), sum(c + d) from table) as subquery
            where subquery.<caret>""", listOf("a", "b"))
    }

    fun testSelectFromSubquery_TryExtractColumnsFromQualifiedNames() {
        schemaStore.addColumns("table2", PropertyInfo("Nested", listOf("NestedTable")))
        schemaStore.addColumns("NestedTable", "a", "b", "c")
        testEquivalentTo("""select * from
            (select t.Nested.a, t.Nested.b, t.Nested.c from table as t) as subquery
            where subquery.<caret>""", listOf("a", "b", "c"))
    }

    private fun testEquivalentTo(input: String, expected: List<String>) {
        Assert.assertThat(getCompletionResult(input).toSet(), Is.`is`(expected.toSet()))
    }

    private fun testEqualTo(query: String, expected: List<String>) {
        Assert.assertThat(getCompletionResult(query), Is.`is`(expected))
    }

    private fun getCompletionResult(content: String): List<String> {
        myFixture.configureByText("test.1c", content)
        myFixture.complete(CompletionType.BASIC, 1)
        return myFixture.lookupElementStrings.orEmpty()
    }

    private fun <T> registerImplementation(implementation: T, clazz: Class<T>) {
        val container = ApplicationManager.getApplication().picoContainer as MutablePicoContainer
        container.unregisterComponent(clazz)
        container.registerComponentInstance(clazz, implementation)
    }

    class FakeSchemaStore : ISchemaStore {
        private val tables = hashMapOf<String, List<PropertyInfo>>()

        override fun getSchema(tableName: String): TableSchema {
            return TableSchema(tableName, tables.getOrElse(tableName, { emptyList<PropertyInfo>() }))
        }

        override fun getTables(): List<String> {
            return tables.keys.toList()
        }

        fun addColumns(tableName: String) {
            tables.put(tableName, emptyList<PropertyInfo>())
        }

        fun addColumns(tableName: String, vararg columns: PropertyInfo) {
            tables.put(tableName, columns.toList())
        }

        fun addColumns(tableName: String, vararg columns: String) {
            tables.put(tableName, columns.map { PropertyInfo(it, emptyList()) })
        }

        fun clear() {
            tables.clear()
        }
    }

}

