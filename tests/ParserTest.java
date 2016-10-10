import com.intellij.testFramework.ParsingTestCase;
import com.simple1c._1cParserDefinition;

public class ParserTest extends ParsingTestCase {

    public ParserTest() {
        super("parser", "1c", new _1cParserDefinition());
    }

    public void testSimple() {
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
