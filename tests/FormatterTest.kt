import com.intellij.psi.formatter.FormatterTestCase
import com.simple1c.boilerplate._1cLanguage

class FormatterTest : FormatterTestCase() {
    override fun getFileExtension(): String {
        return "1c"
    }

    override fun getBasePath(): String {
        return "formatter"
    }

    override fun setUp() {
        super.setUp()
        settings.indentOptions!!.INDENT_SIZE = 2
    }

    fun testSpacesAroundEquality() {
        val settings = getSettings(_1cLanguage.INSTANCE)

        settings.SPACE_AROUND_EQUALITY_OPERATORS = true
        doTextTest("select * from Table where 1=1", """select
  *
from Table
where 1 = 1""")

        settings.SPACE_AROUND_EQUALITY_OPERATORS = false

        doTextTest("select     *    from  Table   where 1    =   1", """select
  *
from Table
where 1=1""")
    }

    fun testSpacesAfterComma() {
        val settings = getSettings(_1cLanguage.INSTANCE)

        settings.SPACE_AFTER_COMMA = true
        doTextTest("select a,b,c from Table", """select
  a,
  b,
  c
from Table""")

        settings.SPACE_AFTER_COMMA = false

        doTextTest("select a,b,c from Table", """select
  a,
  b,
  c
from Table""")
    }

    fun testDoNotAddSpacesInsideParentheses() {
        doTextTest("select (a+1) from Table", """select
  (a + 1)
from Table""")
    }

    fun testIndentSelectionList() {
        doTextTest("select a,b,           d+1 from Table",
                """select
  a,
  b,
  d + 1
from Table""")
    }

    fun testStartMajorClausesOnNewLines() {

        doTextTest("select a from Table " +
                "left join Table2 on id = id " +
                "left join Table3 on id = id where b > 0 group by a", """select
  a
from Table
left join Table2 on id = id
left join Table3 on id = id
where b > 0
group by a""")
    }

}