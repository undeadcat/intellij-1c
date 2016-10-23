// This is a generated file. Not intended for manual editing.
package generated;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.simple1c.boilerplate._1cElementType;

public interface GeneratedTypes {

  IElementType ALIAS = new _1cElementType("ALIAS");
  IElementType BINARY_EXPRESSION = new _1cElementType("BINARY_EXPRESSION");
  IElementType BOOL_LITERAL = new _1cElementType("BOOL_LITERAL");
  IElementType COLUMN_SOURCE = new _1cElementType("COLUMN_SOURCE");
  IElementType EXPRESSION = new _1cElementType("EXPRESSION");
  IElementType EXPRESSION_LIST = new _1cElementType("EXPRESSION_LIST");
  IElementType IDENTIFIER = new _1cElementType("IDENTIFIER");
  IElementType JOIN_ITEM = new _1cElementType("JOIN_ITEM");
  IElementType JOIN_KIND = new _1cElementType("JOIN_KIND");
  IElementType NUMBER_LITERAL = new _1cElementType("NUMBER_LITERAL");
  IElementType ORDER_ITEM = new _1cElementType("ORDER_ITEM");
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

  IElementType ALL = new IElementType("all", null);
  IElementType AS = new IElementType("as", null);
  IElementType ASC = new IElementType("asc", null);
  IElementType ASTERISK = new IElementType("*", null);
  IElementType BY = new IElementType("by", null);
  IElementType COMMA = new IElementType(",", null);
  IElementType DESC = new IElementType("desc", null);
  IElementType DISTINCT = new IElementType("distinct", null);
  IElementType DOT = new IElementType(".", null);
  IElementType FALSE = new IElementType("false", null);
  IElementType FROM = new IElementType("from", null);
  IElementType FULL = new IElementType("full", null);
  IElementType GROUP = new IElementType("group", null);
  IElementType HAVING = new IElementType("having", null);
  IElementType IDSIMPLE = new IElementType("idSimple", null);
  IElementType INNER = new IElementType("inner", null);
  IElementType JOIN = new IElementType("join", null);
  IElementType LEFT = new IElementType("left", null);
  IElementType LINE_COMMENT = new IElementType("line_comment", null);
  IElementType LPAREN = new IElementType("(", null);
  IElementType NUMBER = new IElementType("number", null);
  IElementType ON = new IElementType("on", null);
  IElementType OP_AND = new IElementType("and", null);
  IElementType OP_DIV = new IElementType("/", null);
  IElementType OP_EQ = new IElementType("=", null);
  IElementType OP_GT = new IElementType(">", null);
  IElementType OP_GTE = new IElementType(">=", null);
  IElementType OP_LIKE = new IElementType("like", null);
  IElementType OP_LT = new IElementType("<", null);
  IElementType OP_LTE = new IElementType("<=", null);
  IElementType OP_MINUS = new IElementType("-", null);
  IElementType OP_NEQ = new IElementType("OP_NEQ", null);
  IElementType OP_NOT = new IElementType("not", null);
  IElementType OP_OR = new IElementType("or", null);
  IElementType OP_PLUS = new IElementType("+", null);
  IElementType OP_REM = new IElementType("%", null);
  IElementType ORDER = new IElementType("order", null);
  IElementType OUTER = new IElementType("outer", null);
  IElementType RIGHT = new IElementType("right", null);
  IElementType RPAREN = new IElementType(")", null);
  IElementType SELECT = new IElementType("select", null);
  IElementType SEMICOLON = new IElementType(";", null);
  IElementType STRING = new IElementType("string", null);
  IElementType TOP = new IElementType("top", null);
  IElementType TRUE = new IElementType("true", null);
  IElementType UNION = new IElementType("union", null);
  IElementType WHERE = new IElementType("where", null);
  IElementType ВНЕШНЕЕ = new IElementType("внешнее", null);
  IElementType ВНУТРЕННЕЕ = new IElementType("внутреннее", null);
  IElementType ВОЗР = new IElementType("возр", null);
  IElementType ВСЕ = new IElementType("все", null);
  IElementType ГДЕ = new IElementType("где", null);
  IElementType ИЗ = new IElementType("из", null);
  IElementType ИМЕЮЩИЕ = new IElementType("имеющие", null);
  IElementType ИСТИНА = new IElementType("истина", null);
  IElementType ЛЕВОЕ = new IElementType("левое", null);
  IElementType ЛОЖЬ = new IElementType("ложь", null);
  IElementType ОБЪЕДИНИТЬ = new IElementType("объединить", null);
  IElementType ПО = new IElementType("по", null);
  IElementType ПОЛНОЕ = new IElementType("полное", null);
  IElementType ПРАВОЕ = new IElementType("правое", null);
  IElementType СГРУППИРОВАТЬ = new IElementType("сгруппировать", null);
  IElementType СОЕДИНЕНИЕ = new IElementType("соединение", null);
  IElementType УБЫВ = new IElementType("убыв", null);
  IElementType УПОРЯДОЧИТЬ = new IElementType("упорядочить", null);

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
       if (type == ALIAS) {
        return new AliasImpl(node);
      }
      else if (type == BINARY_EXPRESSION) {
        return new BinaryExpressionImpl(node);
      }
      else if (type == BOOL_LITERAL) {
        return new BoolLiteralImpl(node);
      }
      else if (type == COLUMN_SOURCE) {
        return new ColumnSourceImpl(node);
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
      else if (type == JOIN_ITEM) {
        return new JoinItemImpl(node);
      }
      else if (type == JOIN_KIND) {
        return new JoinKindImpl(node);
      }
      else if (type == NUMBER_LITERAL) {
        return new NumberLiteralImpl(node);
      }
      else if (type == ORDER_ITEM) {
        return new OrderItemImpl(node);
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
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
