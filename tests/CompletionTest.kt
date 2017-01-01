import com.intellij.codeInsight.completion.CompletionType
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupManager
import com.intellij.codeInsight.lookup.impl.LookupImpl
import com.intellij.openapi.application.Result
import com.intellij.openapi.command.WriteCommandAction
import com.simple1c.lang.PropertyInfo
import org.hamcrest.core.Is
import org.junit.Assert

//todo. TableSection. Do I need to do anything?
class CompletionTest : ContainerTestBase() {
    fun testCompleteTableNames() {
        schemaStore.addColumns("Table1", "TColumn1")
        schemaStore.addColumns("Table2")
        schemaStore.addColumns("Other")
        testEquivalentTo("select * from <caret>", listOf("Other", "Table1", "Table2"))
        testEquivalentTo("select * from Tab<caret>", listOf("Table1", "Table2"))
    }

    fun testAcceptColumnCompletion_Bug() {
        schemaStore.addColumns("Table", "Col1", "Col2")
        selectElement(getLookupElements("select <caret> from Table").first())
        myFixture.checkResult("select Col1 from Table")
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

    fun testIdentifierStartsWithFullyQualifiedTable_ResolveColumnsFromTable() {
        schemaStore.addColumns("Справочник.Сотрудники", "Column1")
        schemaStore.addColumns("Справочник.Контрагенты", "Column2")
        testEquivalentTo("select Справочник.Контрагенты.<caret> from Справочник.Сотрудники join Справочник.Контрагенты", listOf("Column2"))
    }

    fun testCannotResolveAlias() {
        schemaStore.addColumns("Table1", "Column1")
        testEquivalentTo("select * from Table1 t where d.<caret>", emptyList())
    }

    fun testCompleteAlias_InsertPeriod() {
        schemaStore.addColumns("Table1", "Column1")
        selectElement(getLookupElements("select * from Table1 t where <caret>").first { it.lookupString.orEmpty() == "t" })
        myFixture.checkResult("select * from Table1 t where t.")
    }

    fun testNameStartsWithAlias_ResolveColumnsFromAliasedTable() {
        schemaStore.addColumns("Table1", "Column1", "Another1", "Another2")
        schemaStore.addColumns("Table2", "Column2")
        testEquivalentTo("select * from Table1 t1 where t1.<caret>", listOf("Column1", "Another1", "Another2"))
        testEquivalentTo("select * from Table1 t1 where t1.A<caret>", listOf("Another1", "Another2"))
    }

    fun testHasDefinedAliases_IncludeAliasQualifiedNames() {
        schemaStore.addColumns("Table1", "Column1", "Column2")
        schemaStore.addColumns("Table2", "Column3")
        testOrdered("select * from Table1 t left join Table2 t2 on t1.Column1 = t2.Column2 where <caret>", listOf("t.Column1", "t.Column2", "t2.Column3", "t", "t2", "Table1", "Table2"))
        testOrdered("select * from Table1 t left join Table2 t2 on t1.Column1 = t2.Column2 where t.<caret>", listOf("Column1", "Column2"))
    }

    fun testNoAliases_SuggestSimpleAndFullyQualifiedNames() {
        schemaStore.addColumns("Table1", "Column1")
        testOrdered("select * from Table1 where <caret>", listOf("Column1", "Table1.Column1", "Table1"))
    }

    fun testNestedTables() {
        schemaStore.addColumns("Документ.ПоступлениеНаРасчетныйСчет",
                PropertyInfo("Контрагент", listOf("Справочник.Контрагенты")))
        schemaStore.addColumns("Справочник.Контрагенты",
                PropertyInfo("Владелец", listOf("Справочник.Контрагенты")),
                PropertyInfo("ИНН", emptyList()))
        testEquivalentTo("select * from Документ.ПоступлениеНаРасчетныйСчет where <caret>", listOf("Контрагент", "Документ.ПоступлениеНаРасчетныйСчет.Контрагент", "Документ.ПоступлениеНаРасчетныйСчет"))
        testEquivalentTo("select * from Документ.ПоступлениеНаРасчетныйСчет d where Контрагент.<caret>", listOf("Владелец", "ИНН"))
    }

    fun testNestedTablesWithAlias() {
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
        testEquivalentTo("select * from Документ.ПоступлениеНаРасчетныйСчет d where D.КоНТрагент.<caret>", listOf("Владелец", "ИНН", "Фамилия", "Имя", "Отчество"))
        testEquivalentTo("select * from ДокумеНТ.ПоступлениеНаРасчетныйСчет d where d.Контрагент.ВЛаделец.<caret>", listOf("Владелец", "ИНН"))
    }

    fun testNestedTableWithQualifiedName() {
        schemaStore.addColumns("Документ.ПоступлениеНаРасчетныйСчет",
                PropertyInfo("Контрагент", listOf("Справочник.Контрагенты")))
        schemaStore.addColumns("Справочник.Контрагенты",
                PropertyInfo("ИНН", emptyList()))
        testEquivalentTo("select * from Документ.ПоступлениеНаРасчетныйСчет d where Документ.ПоступлениеНаРасчетныйСчет.Контрагент.<caret>", listOf("ИНН"))
    }

    fun testNestedTableWithSelfReference(){
        schemaStore.addColumns("Справочник.Контрагенты",
                PropertyInfo("Ссылка", listOf("Справочник.Контрагенты")),
                PropertyInfo("ИНН", emptyList()))
        testEquivalentTo("select * from Справочник.Контрагенты d where d.СсЫЛКА.Ссылка.<caret>", listOf("ИНН", "Ссылка"))
    }

    fun testFullyQualifiedName_Bug() {
        schemaStore.addColumns("Справочник.Контрагенты",
                PropertyInfo("ИНН", emptyList()))

        testEquivalentTo("select * from Справочник.Контрагенты where Справочник.Контрагенты<caret>", emptyList())
        testEquivalentTo("select * from Справочник.Контрагенты where Справочник.Контрагенты.<caret>", listOf("ИНН"))
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
        AND i<caret> )""", listOf("t1.id1", "t2.id2"))

        testEquivalentTo("""SELECT *
FROM table1 t1
WHERE table2Key in
      (SELECT id
       FROM table2 t2
       WHERE table3Key in
                 (SELECT id
                  FROM table3 t3
                  WHERE id = t2.table3Key AND table1Key = i<caret> ))""", listOf("t3.id3", "t2.id2", "t1.id1"))
    }

    fun testCannotReferToTableUsedInSubqueryOutsideScope() {
        schemaStore.addColumns("table1", "id1", "table1Column")
        schemaStore.addColumns("table2", "id2", "table2Column")
        testEquivalentTo("select * from " +
                "table1 t1 " +
                "where table1Column in (select id2 from table2 t2) " +
                "and <caret>", listOf("t1", "t1.id1", "t1.table1Column", "table1"))

    }

    fun testSelectFromSubquery_SuggestNamesQualifiedWithAlias() {
        schemaStore.addColumns("table2", "a", "b", "c")
        testEquivalentTo("select * from (select a as alias1, b as alias2 from table2) t where <caret>", listOf("t", "t.alias1", "t.alias2"))
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

    fun testTriggerCompletionOutsideQuery() {
        testEquivalentTo("select * from Table where;   <caret>", emptyList())
    }

    fun testTriggerDotCompletionOnPrimitiveColumn() {
        testEquivalentTo("select * from Справочник.Контрагенты c where c.ИНН.<caret>", emptyList())
    }

    private fun testEquivalentTo(input: String, expected: List<String>) {
        Assert.assertThat(getLookupStrings(input).toSet(), Is.`is`(expected.toSet()))
    }

    private fun testOrdered(query: String, expected: List<String>) {
        Assert.assertThat(getLookupStrings(query), Is.`is`(expected))
    }

    private fun getLookupStrings(content: String): List<String> {
        return getLookupElements(content).map { it.lookupString }
    }

    private fun getLookupElements(content: String): Array<LookupElement> {
        myFixture.configureByText("test.1c", content)
        val lookupElements = myFixture.complete(CompletionType.BASIC, 1)
        return lookupElements
    }

    private fun selectElement(item: LookupElement) {
        object : WriteCommandAction<Unit>(project, file) {
            override fun run(result: Result<Unit>) {
                val lookup = (LookupManager.getInstance(project).activeLookup as LookupImpl)
                lookup.currentItem = item
                lookup.finishLookup(0.toChar())
            }

        }.execute()
    }
}