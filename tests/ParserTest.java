import com.intellij.testFramework.ParsingTestCase;
import com.simple1c._1cParserDefinition;
import com.simple1c.boilerplate._1cLanguage;

/*
TODO:
Parenthesis
QueryFunction
UnaryNotOperator
InOperator
AllowExtraneousBracesInExpressions
LikeOperator
StringLiteralWithEscapedQuote
AggregateWithWildcard
AggregateWithColumnExpression
FilterByNullCondition (is null)
SelectFromSubquery
EmbeddedQueryInFilterExpression

EmbeddedQueryCanReferToOuterTables -- need to rewrite references*/
public class ParserTest extends ParsingTestCase {

    public ParserTest() {
        super("parser", "1c", new _1cParserDefinition());
    }

    public void testSimple() {
        doTest(true);
    }

    public void testMultipleQueries() {
        doTest(true);
    }

    public void testInvalidSymbolsBetweenQueries() {
        doTest(true);
    }

    public void testErrorsInQuery() {
        doTest(true);
    }

    public void testPartialQuery() {
        doTest(true);
    }

    protected boolean skipSpaces() {
        return true;
    }

    @Override
    protected String getTestDataPath() {
        return "testData";
    }
}
