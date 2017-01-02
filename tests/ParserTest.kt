import com.intellij.testFramework.ParsingTestCase
import com.simple1c.lang._1cParserDefinition
import org.junit.Assert

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

    fun testOptionalClauses() {
        doTest(true)
    }

    fun testQueriesSeparatedBySemicolons() {
        doTest(true)
    }

    fun testCanRecoverToSeparator() {
        doTest(true)
    }

    fun testCanSeparateQueriesWithNewLine() {
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

    fun testUnaryNotPriority() {
        doTest(true)
    }

    fun testBinaryOperatorPriorities() {
        doTest(true)
    }

    fun testAllowExtraneousBracesInExpressions() {
        doTest(true)
    }

    fun testUnions() {
        doTest(true)
    }

    fun testSelectFromSubquery() {
        doTest(true)
    }

    fun testInExpression() {
        doTest(true)
    }

    fun testAggregateFunctions() {
        doTest(true)
    }

    fun testCaseExpression() {
        doTest(true)
    }

    fun testColumnNameMatchesAggregationFunction() {
        doTest(true)
    }

    fun testIncludeIncompleteOptionalClausesInParseTree() {
        doTest(true)
    }

    fun testQueryFunctionExpressions() {
        doTest(true)
    }

    override fun skipSpaces(): Boolean {
        return true
    }

    override fun getTestDataPath(): String {
        return "testData"
    }
}
