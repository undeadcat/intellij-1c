import com.intellij.testFramework.ParsingTestCase
import com.simple1c.lang._1cParserDefinition
import org.junit.Assert

/*
TODO:
unions don't work
Parenthesis
not equals operators
subqueries don't work
QueryFunction
UnaryNotOperator
InOperator
AllowExtraneousBracesInExpressions
LikeOperator
AggregateWithWildcard
AggregateWithColumnExpression
FilterByNullCondition (is null)
SelectFromSubquery
EmbeddedQueryInFilterExpression

EmbeddedQueryCanReferToOuterTables -- need to rewrite references
Сумма - название колонки vs функция
*/
class ParserTest : ParsingTestCase("parser", "1c", _1cParserDefinition()) {

    fun testNoErrorsOnEmptyFile() {
        doTest(true)
    }

    fun testCombineRussianAndEnglishSyntaxWithDifferentCase() {
        doTest(true)
    }

    fun testSimple() {
        doTest(true)
    }

    fun testQueriesSeparatedBySemicolons() {
        doTest(true)
    }

    fun testCanRecoverToSeparator() {
        doTest(true)
    }

    fun testPartialQuery() {
        doTest(true)
    }

    fun testLiterals() {
        doTest(true)
    }

    fun testIsNull() {
        doTest(true)
    }

    fun testUnaryOperatorPriorities() {

    }

    fun testBinaryOperatorPriorities() {

    }

    override fun skipSpaces(): Boolean {
        return true
    }

    override fun getTestDataPath(): String {
        return "testData"
    }
}
