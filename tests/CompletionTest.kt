import com.intellij.codeInsight.completion.CompletionType
import com.intellij.openapi.application.ApplicationManager
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase
import com.simple1c.lang.ISchemaStore
import org.hamcrest.core.Is
import org.junit.Assert
import org.junit.Ignore
import org.picocontainer.MutablePicoContainer

class CompletionTest : LightCodeInsightFixtureTestCase() {

    companion object {
        private var schemaStore = FakeSchemaStore()
    }

    override fun setUp() {
        super.setUp()
        registerImplementation(schemaStore, ISchemaStore::class.java)
        schemaStore.clear()
    }

    override fun getTestDataPath(): String {
        return "testData"
    }

    fun testTableDeclaration() {
        schemaStore.addColumns("Table1", "TColumn1")
        schemaStore.addColumns("Table2")
        schemaStore.addColumns("Other")
        val strings = getCompletionResult("select * from <caret>")
        Assert.assertThat(strings, Is.`is`(listOf("Other", "Table1", "Table2")))

        Assert.assertThat(getCompletionResult("select * from Tab<caret>"), Is.`is`(listOf("Table1", "Table2")))
    }

    fun testCompleteTableNamesAfterJoinKeyword() {
        schemaStore.addColumns("Table1", "TColumn1")
        schemaStore.addColumns("Table2", "TColumn2")
        Assert.assertThat(getCompletionResult("select * from Table1 join Tab<caret>"), Is.`is`(listOf("Table1", "Table2")))
        Assert.assertThat(getCompletionResult("select * from Table1 join <caret>"), Is.`is`(listOf("Table1", "Table2")))
    }

    fun testAfterSelectKeyword_completeTablesAndColumns() {
        schemaStore.addColumns("Table", "Column1", "Column2")
        val strings = getCompletionResult("select <caret>").toSet()
        Assert.assertThat(strings, Is.`is`(listOf("Table", "Column1", "Column2").toSet()))
    }

    @Ignore
    fun testIdentifierStartsWithFullyQualifiedTable_completeColumnsBelongingToTable() {
        //TODO. does 1c support fully-qualified names (Справочник.Контрагенты.ИНН?)
        Assert.fail()
    }

    fun testIdentifierStartsWithDefinedAlias_completeColumnsBelongingToTable() {
        schemaStore.addColumns("Table1", "Column1")
        schemaStore.addColumns("Table2", "Column2")
        val strings = getCompletionResult("select * from Table1 t1 where t1.<caret>")
        Assert.assertThat(strings, Is.`is`(listOf("Column1")))
    }

    fun testStatementContainsTable_limitSelectionToTable() {
        schemaStore.addColumns("Table1", "Column1")
        schemaStore.addColumns("Table2", "Column2")
        val strings = getCompletionResult("select * from Table1 where <caret>")
        Assert.assertThat(strings, Is.`is`(listOf("Column1")))
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
        private val tables = hashMapOf<String, List<String>>()

        override fun getColumns(tableName: String?): Iterable<String> {
            if (tableName == null)
                return tables.flatMap { it.value }
            return tables.getOrElse(tableName, { emptyList<String>() })
        }

        override fun getTables(): Iterable<String> {
            return tables.keys
        }

        fun addColumns(tableName: String, vararg columns: String) {
            tables.put(tableName, columns.toList())
        }

        fun clear() {
            tables.clear()
        }
    }

}

