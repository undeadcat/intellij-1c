import com.simple1c.lang.PropertyInfo

class HighlightingTest : ContainerTestBase() {

    fun testCannotResolveTable() {
        schemaStore.addColumns("Table1", "Column1")
        doTestHighlighting("select * from <warning>NonExistent</warning> as someAlias")
    }

    fun testCannotResolveSimpleColumnIdentifier() {
        schemaStore.addColumns("Table1", "Column1")
        doTestHighlighting("select Column1 alias1, <warning>Column2</warning> alias2 " +
                "from Table1 " +
                "where Column1 = 1 and <warning>Column2</warning> = 2")
    }

    fun testCannotResolveNestedTableIdentifier() {
        schemaStore.addColumns("Документ.ПоступлениеНаРасчетныйСчет",
                PropertyInfo("Контрагент", listOf("Справочник.Контрагенты")))
        schemaStore.addColumns("Справочник.Контрагенты",
                PropertyInfo("Владелец", listOf("Справочник.Контрагенты")),
                PropertyInfo("ИНН", emptyList()))
        doTestHighlighting("select * from Документ.ПоступлениеНаРасчетныйСчет docs where\n" +
                "docs.Контрагент.ИНН = 123\n" +
                "and docs.Контрагент.Владелец.Инн = 456\n" +
                "and docs.<warning descr=\"Could not resolve identifier 'Nonexistent.Владелец.ИНН'\">Nonexistent.Владелец.ИНН</warning> = 123\n" +
                "and docs.Контрагент.Владелец.<warning descr=\"Could not resolve identifier 'Nonexistent'\">Nonexistent</warning> = 123")
    }

    fun testDoNotHighlightFunctionNames() {
        schemaStore.addColumns("Table")
        doTestHighlighting("select count(<warning>NonExistent</warning>) from Table where length(<warning>Inn</warning>) > 0")
    }

    fun testHighlightPrefixOfValidColumn() {
        schemaStore.addColumns("Table", "Column")
        doTestHighlighting("select <warning>Col</warning> from Table")
    }

    private fun doTestHighlighting(text: String) {
        myFixture.configureByText("test.1c", text)
        myFixture.testHighlighting(true, true, true)
    }
}