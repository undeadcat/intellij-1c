// This is a generated file. Not intended for manual editing.
package generated;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.simple1c.boilerplate._1cElementType;

public interface GeneratedTypes {

  IElementType AGGREGATE_EXPRESSION = new _1cElementType("AGGREGATE_EXPRESSION");
  IElementType ALIAS = new _1cElementType("ALIAS");
  IElementType BINARY_EXPRESSION = new _1cElementType("BINARY_EXPRESSION");
  IElementType BOOL_LITERAL = new _1cElementType("BOOL_LITERAL");
  IElementType CASE_ELEMENT = new _1cElementType("CASE_ELEMENT");
  IElementType CASE_EXPRESSION = new _1cElementType("CASE_EXPRESSION");
  IElementType COLUMN_SOURCE = new _1cElementType("COLUMN_SOURCE");
  IElementType EXPRESSION = new _1cElementType("EXPRESSION");
  IElementType EXPRESSION_LIST = new _1cElementType("EXPRESSION_LIST");
  IElementType IDENTIFIER = new _1cElementType("IDENTIFIER");
  IElementType IN_EXPRESSION = new _1cElementType("IN_EXPRESSION");
  IElementType JOIN_ITEM = new _1cElementType("JOIN_ITEM");
  IElementType JOIN_KIND = new _1cElementType("JOIN_KIND");
  IElementType NULL_LITERAL = new _1cElementType("NULL_LITERAL");
  IElementType NUMBER_LITERAL = new _1cElementType("NUMBER_LITERAL");
  IElementType ORDER_ITEM = new _1cElementType("ORDER_ITEM");
  IElementType QUERY_FUNCTION_EXPRESSION = new _1cElementType("QUERY_FUNCTION_EXPRESSION");
  IElementType ROOT_QUERY = new _1cElementType("ROOT_QUERY");
  IElementType SELECTION_ITEM = new _1cElementType("SELECTION_ITEM");
  IElementType SELECTION_LIST = new _1cElementType("SELECTION_LIST");
  IElementType SELECT_STATEMENT = new _1cElementType("SELECT_STATEMENT");
  IElementType SQL_QUERY = new _1cElementType("SQL_QUERY");
  IElementType STRING_LITERAL = new _1cElementType("STRING_LITERAL");
  IElementType SUBQUERY = new _1cElementType("SUBQUERY");
  IElementType SUBQUERY_TABLE = new _1cElementType("SUBQUERY_TABLE");
  IElementType TABLE_DECLARATION = new _1cElementType("TABLE_DECLARATION");
  IElementType TOP_OPT = new _1cElementType("TOP_OPT");
  IElementType UNARY_EXPRESSION = new _1cElementType("UNARY_EXPRESSION");
  IElementType UNION_CLAUSE = new _1cElementType("UNION_CLAUSE");

  IElementType AGGREGATIONFUNCTION = new IElementType("aggregationFunction", null);
  IElementType ALLKEYWORD = new IElementType("allKeyword", null);
  IElementType ASCKEYWORD = new IElementType("ascKeyword", null);
  IElementType ASKEYWORD = new IElementType("asKeyword", null);
  IElementType ASTERISK = new IElementType("*", null);
  IElementType BOOL = new IElementType("bool", null);
  IElementType BYKEYWORD = new IElementType("by", null);
  IElementType CASEKEYWORD = new IElementType("caseKeyword", null);
  IElementType COMMA = new IElementType(",", null);
  IElementType DESCKEYWORD = new IElementType("descKeyword", null);
  IElementType DISTINCTKEYWORD = new IElementType("distinctKeyword", null);
  IElementType DOT = new IElementType(".", null);
  IElementType ELSEKEYWORD = new IElementType("elseKeyword", null);
  IElementType ENDKEYWORD = new IElementType("endKeyword", null);
  IElementType FROMKEYWORD = new IElementType("fromKeyword", null);
  IElementType FULLKEYWORD = new IElementType("fullKeyword", null);
  IElementType GROUPKEYWORD = new IElementType("groupKeyword", null);
  IElementType HAVINGKEYWORD = new IElementType("havingKeyword", null);
  IElementType ID_TOKEN = new IElementType("ID_TOKEN", null);
  IElementType INNERKEYWORD = new IElementType("innerKeyword", null);
  IElementType JOINKEYWORD = new IElementType("joinKeyword", null);
  IElementType LEFTKEYWORD = new IElementType("leftKeyword", null);
  IElementType LINE_COMMENT = new IElementType("line_comment", null);
  IElementType LPAREN = new IElementType("(", null);
  IElementType NULL = new IElementType("null", null);
  IElementType NUMBER = new IElementType("number", null);
  IElementType ONKEYWORD = new IElementType("on", null);
  IElementType OP_AND = new IElementType("OP_AND", null);
  IElementType OP_DIV = new IElementType("/", null);
  IElementType OP_EQ = new IElementType("=", null);
  IElementType OP_GT = new IElementType(">", null);
  IElementType OP_GTE = new IElementType(">=", null);
  IElementType OP_IN = new IElementType("OP_IN", null);
  IElementType OP_IS = new IElementType("OP_IS", null);
  IElementType OP_LIKE = new IElementType("OP_LIKE", null);
  IElementType OP_LT = new IElementType("<", null);
  IElementType OP_LTE = new IElementType("<=", null);
  IElementType OP_MINUS = new IElementType("-", null);
  IElementType OP_NEQ = new IElementType("OP_NEQ", null);
  IElementType OP_NOT = new IElementType("OP_NOT", null);
  IElementType OP_OR = new IElementType("OP_OR", null);
  IElementType OP_PLUS = new IElementType("+", null);
  IElementType ORDERKEYWORD = new IElementType("orderKeyword", null);
  IElementType OUTERKEYWORD = new IElementType("outerKeyword", null);
  IElementType RIGHTKEYWORD = new IElementType("rightKeyword", null);
  IElementType RPAREN = new IElementType(")", null);
  IElementType SELECTKEYWORD = new IElementType("selectKeyword", null);
  IElementType SEMICOLON = new IElementType(";", null);
  IElementType STRING = new IElementType("string", null);
  IElementType THENKEYWORD = new IElementType("thenKeyword", null);
  IElementType TOPKEYWORD = new IElementType("topKeyword", null);
  IElementType UNIONKEYWORD = new IElementType("unionKeyword", null);
  IElementType WHENKEYWORD = new IElementType("whenKeyword", null);
  IElementType WHEREKEYWORD = new IElementType("whereKeyword", null);
  IElementType ПОKEYWORD = new IElementType("по", null);

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
       if (type == AGGREGATE_EXPRESSION) {
        return new AggregateExpressionImpl(node);
      }
      else if (type == ALIAS) {
        return new AliasImpl(node);
      }
      else if (type == BINARY_EXPRESSION) {
        return new BinaryExpressionImpl(node);
      }
      else if (type == BOOL_LITERAL) {
        return new BoolLiteralImpl(node);
      }
      else if (type == CASE_ELEMENT) {
        return new CaseElementImpl(node);
      }
      else if (type == CASE_EXPRESSION) {
        return new CaseExpressionImpl(node);
      }
      else if (type == EXPRESSION) {
        return new ExpressionImpl(node);
      }
      else if (type == EXPRESSION_LIST) {
        return new ExpressionListImpl(node);
      }
      else if (type == IDENTIFIER) {
        return new IdentifierImpl(node);
      }
      else if (type == IN_EXPRESSION) {
        return new InExpressionImpl(node);
      }
      else if (type == JOIN_ITEM) {
        return new JoinItemImpl(node);
      }
      else if (type == JOIN_KIND) {
        return new JoinKindImpl(node);
      }
      else if (type == NULL_LITERAL) {
        return new NullLiteralImpl(node);
      }
      else if (type == NUMBER_LITERAL) {
        return new NumberLiteralImpl(node);
      }
      else if (type == ORDER_ITEM) {
        return new OrderItemImpl(node);
      }
      else if (type == QUERY_FUNCTION_EXPRESSION) {
        return new QueryFunctionExpressionImpl(node);
      }
      else if (type == ROOT_QUERY) {
        return new RootQueryImpl(node);
      }
      else if (type == SELECTION_ITEM) {
        return new SelectionItemImpl(node);
      }
      else if (type == SELECTION_LIST) {
        return new SelectionListImpl(node);
      }
      else if (type == SELECT_STATEMENT) {
        return new SelectStatementImpl(node);
      }
      else if (type == SQL_QUERY) {
        return new SqlQueryImpl(node);
      }
      else if (type == STRING_LITERAL) {
        return new StringLiteralImpl(node);
      }
      else if (type == SUBQUERY) {
        return new SubqueryImpl(node);
      }
      else if (type == SUBQUERY_TABLE) {
        return new SubqueryTableImpl(node);
      }
      else if (type == TABLE_DECLARATION) {
        return new TableDeclarationImpl(node);
      }
      else if (type == TOP_OPT) {
        return new TopOptImpl(node);
      }
      else if (type == UNARY_EXPRESSION) {
        return new UnaryExpressionImpl(node);
      }
      else if (type == UNION_CLAUSE) {
        return new UnionClauseImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
