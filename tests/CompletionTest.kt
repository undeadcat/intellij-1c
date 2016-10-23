import com.intellij.codeInsight.completion.CompletionType
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase
import org.hamcrest.core.Is
import org.junit.Assert

class CompletionTest : LightCodeInsightFixtureTestCase() {

    override fun getTestDataPath(): String {
        return "testData"
    }

    fun testTableDeclaration() {
        myFixture.configureByFiles("completion/tableDeclaration.1c")
        myFixture.complete(CompletionType.BASIC, 1)
        val strings = myFixture.lookupElementStrings
        Assert.assertThat(strings, Is.`is`(listOf("Справочник.Контрагенты", "Справочник.Сотрудники")))
    }
}

