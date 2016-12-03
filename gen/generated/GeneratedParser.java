// This is a generated file. Not intended for manual editing.
package generated;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static generated.GeneratedTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class GeneratedParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType type, PsiBuilder builder) {
    parseLight(type, builder);
    return builder.getTreeBuilt();
  }

  public void parseLight(IElementType type, PsiBuilder builder) {
    boolean result;
    builder = adapt_builder_(type, builder, this, EXTENDS_SETS_);
    Marker marker = enter_section_(builder, 0, _COLLAPSE_, null);
    if (type == ALIAS) {
      result = alias(builder, 0);
    }
    else if (type == CASE_ELEMENT) {
      result = caseElement(builder, 0);
    }
    else if (type == COLUMN_SOURCE) {
      result = columnSource(builder, 0);
    }
    else if (type == EXPRESSION) {
      result = expression(builder, 0, -1);
    }
    else if (type == EXPRESSION_LIST) {
      result = expressionList(builder, 0);
    }
    else if (type == JOIN_ITEM) {
      result = joinItem(builder, 0);
    }
    else if (type == JOIN_KIND) {
      result = joinKind(builder, 0);
    }
    else if (type == ORDER_ITEM) {
      result = orderItem(builder, 0);
    }
    else if (type == ROOT_QUERY) {
      result = rootQuery(builder, 0);
    }
    else if (type == SELECT_STATEMENT) {
      result = select_statement(builder, 0);
    }
    else if (type == SELECTION_ITEM) {
      result = selectionItem(builder, 0);
    }
    else if (type == SELECTION_LIST) {
      result = selectionList(builder, 0);
    }
    else if (type == SQL_QUERY) {
      result = sqlQuery(builder, 0);
    }
    else if (type == SUBQUERY) {
      result = subquery(builder, 0);
    }
    else if (type == SUBQUERY_TABLE) {
      result = subqueryTable(builder, 0);
    }
    else if (type == TABLE_DECLARATION) {
      result = tableDeclaration(builder, 0);
    }
    else if (type == TOP_OPT) {
      result = topOpt(builder, 0);
    }
    else if (type == UNION_CLAUSE) {
      result = unionClause(builder, 0);
    }
    else {
      result = parse_root_(type, builder, 0);
    }
    exit_section_(builder, 0, marker, type, result, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType type, PsiBuilder builder, int level) {
    return grammar(builder, level + 1);
  }

  public static final TokenSet[] EXTENDS_SETS_ = new TokenSet[] {
    create_token_set_(COLUMN_SOURCE, SUBQUERY_TABLE, TABLE_DECLARATION),
    create_token_set_(AGGREGATE_EXPRESSION, BINARY_EXPRESSION, BOOL_LITERAL, CASE_EXPRESSION,
      EXPRESSION, IDENTIFIER, IN_EXPRESSION, NULL_LITERAL,
      NUMBER_LITERAL, QUERY_FUNCTION_EXPRESSION, STRING_LITERAL, UNARY_EXPRESSION),
  };

  /* ********************************************************** */
  // asKeyword? identifier
  public static boolean alias(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "alias")) return false;
    if (!nextTokenIs(builder, "<alias>", ID_TOKEN, ASKEYWORD)) return false;
    boolean result;
    Marker marker = enter_section_(builder, level, _NONE_, ALIAS, "<alias>");
    result = alias_0(builder, level + 1);
    result = result && identifier(builder, level + 1);
    exit_section_(builder, level, marker, result, false, null);
    return result;
  }

  // asKeyword?
  private static boolean alias_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "alias_0")) return false;
    consumeToken(builder, ASKEYWORD);
    return true;
  }

  /* ********************************************************** */
  // whenKeyword expression thenKeyword expression
  public static boolean caseElement(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "caseElement")) return false;
    if (!nextTokenIs(builder, WHENKEYWORD)) return false;
    boolean result, pinned;
    Marker marker = enter_section_(builder, level, _NONE_, CASE_ELEMENT, null);
    result = consumeToken(builder, WHENKEYWORD);
    pinned = result; // pin = 1
    result = result && report_error_(builder, expression(builder, level + 1, -1));
    result = pinned && report_error_(builder, consumeToken(builder, THENKEYWORD)) && result;
    result = pinned && expression(builder, level + 1, -1) && result;
    exit_section_(builder, level, marker, result, pinned, null);
    return result || pinned;
  }

  /* ********************************************************** */
  // caseElement+
  static boolean caseElementList(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "caseElementList")) return false;
    if (!nextTokenIs(builder, WHENKEYWORD)) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = caseElement(builder, level + 1);
    int pos = current_position_(builder);
    while (result) {
      if (!caseElement(builder, level + 1)) break;
      if (!empty_element_parsed_guard_(builder, "caseElementList", pos)) break;
      pos = current_position_(builder);
    }
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // tableDeclaration | subqueryTable
  public static boolean columnSource(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "columnSource")) return false;
    if (!nextTokenIs(builder, "<column source>", ID_TOKEN, LPAREN)) return false;
    boolean result;
    Marker marker = enter_section_(builder, level, _COLLAPSE_, COLUMN_SOURCE, "<column source>");
    result = tableDeclaration(builder, level + 1);
    if (!result) result = subqueryTable(builder, level + 1);
    exit_section_(builder, level, marker, result, false, null);
    return result;
  }

  /* ********************************************************** */
  // distinctKeyword?
  static boolean distinctOpt(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "distinctOpt")) return false;
    consumeToken(builder, DISTINCTKEYWORD);
    return true;
  }

  /* ********************************************************** */
  // expression (COMMA expression)*
  public static boolean expressionList(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "expressionList")) return false;
    boolean result;
    Marker marker = enter_section_(builder, level, _NONE_, EXPRESSION_LIST, "<expression list>");
    result = expression(builder, level + 1, -1);
    result = result && expressionList_1(builder, level + 1);
    exit_section_(builder, level, marker, result, false, null);
    return result;
  }

  // (COMMA expression)*
  private static boolean expressionList_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "expressionList_1")) return false;
    int pos = current_position_(builder);
    while (true) {
      if (!expressionList_1_0(builder, level + 1)) break;
      if (!empty_element_parsed_guard_(builder, "expressionList_1", pos)) break;
      pos = current_position_(builder);
    }
    return true;
  }

  // COMMA expression
  private static boolean expressionList_1_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "expressionList_1_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, COMMA);
    result = result && expression(builder, level + 1, -1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // (queryItem)*
  static boolean grammar(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "grammar")) return false;
    int pos = current_position_(builder);
    while (true) {
      if (!grammar_0(builder, level + 1)) break;
      if (!empty_element_parsed_guard_(builder, "grammar", pos)) break;
      pos = current_position_(builder);
    }
    return true;
  }

  // (queryItem)
  private static boolean grammar_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "grammar_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = queryItem(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // groupKeyword (byKeyword| поKeyword) expressionList [havingKeyword expression]
  static boolean group_expression(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "group_expression")) return false;
    if (!nextTokenIs(builder, GROUPKEYWORD)) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, GROUPKEYWORD);
    result = result && group_expression_1(builder, level + 1);
    result = result && expressionList(builder, level + 1);
    result = result && group_expression_3(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // byKeyword| поKeyword
  private static boolean group_expression_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "group_expression_1")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, BYKEYWORD);
    if (!result) result = consumeToken(builder, ПОKEYWORD);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // [havingKeyword expression]
  private static boolean group_expression_3(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "group_expression_3")) return false;
    group_expression_3_0(builder, level + 1);
    return true;
  }

  // havingKeyword expression
  private static boolean group_expression_3_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "group_expression_3_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, HAVINGKEYWORD);
    result = result && expression(builder, level + 1, -1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // joinKind? joinKeyword columnSource (onKeyword | поKeyword) expression
  public static boolean joinItem(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "joinItem")) return false;
    boolean result, pinned;
    Marker marker = enter_section_(builder, level, _NONE_, JOIN_ITEM, "<join item>");
    result = joinItem_0(builder, level + 1);
    result = result && consumeToken(builder, JOINKEYWORD);
    pinned = result; // pin = 2
    result = result && report_error_(builder, columnSource(builder, level + 1));
    result = pinned && report_error_(builder, joinItem_3(builder, level + 1)) && result;
    result = pinned && expression(builder, level + 1, -1) && result;
    exit_section_(builder, level, marker, result, pinned, null);
    return result || pinned;
  }

  // joinKind?
  private static boolean joinItem_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "joinItem_0")) return false;
    joinKind(builder, level + 1);
    return true;
  }

  // onKeyword | поKeyword
  private static boolean joinItem_3(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "joinItem_3")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, ONKEYWORD);
    if (!result) result = consumeToken(builder, ПОKEYWORD);
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // innerKeyword | ((fullKeyword | leftKeyword| rightKeyword) (outerKeyword)?)
  public static boolean joinKind(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "joinKind")) return false;
    boolean result;
    Marker marker = enter_section_(builder, level, _NONE_, JOIN_KIND, "<join kind>");
    result = consumeToken(builder, INNERKEYWORD);
    if (!result) result = joinKind_1(builder, level + 1);
    exit_section_(builder, level, marker, result, false, null);
    return result;
  }

  // (fullKeyword | leftKeyword| rightKeyword) (outerKeyword)?
  private static boolean joinKind_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "joinKind_1")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = joinKind_1_0(builder, level + 1);
    result = result && joinKind_1_1(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // fullKeyword | leftKeyword| rightKeyword
  private static boolean joinKind_1_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "joinKind_1_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, FULLKEYWORD);
    if (!result) result = consumeToken(builder, LEFTKEYWORD);
    if (!result) result = consumeToken(builder, RIGHTKEYWORD);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // (outerKeyword)?
  private static boolean joinKind_1_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "joinKind_1_1")) return false;
    consumeToken(builder, OUTERKEYWORD);
    return true;
  }

  /* ********************************************************** */
  // orderItem (COMMA orderItem) *
  static boolean orderByExpressionList(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "orderByExpressionList")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = orderItem(builder, level + 1);
    result = result && orderByExpressionList_1(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // (COMMA orderItem) *
  private static boolean orderByExpressionList_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "orderByExpressionList_1")) return false;
    int pos = current_position_(builder);
    while (true) {
      if (!orderByExpressionList_1_0(builder, level + 1)) break;
      if (!empty_element_parsed_guard_(builder, "orderByExpressionList_1", pos)) break;
      pos = current_position_(builder);
    }
    return true;
  }

  // COMMA orderItem
  private static boolean orderByExpressionList_1_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "orderByExpressionList_1_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, COMMA);
    result = result && orderItem(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // expression [ascKeyword | descKeyword]
  public static boolean orderItem(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "orderItem")) return false;
    boolean result;
    Marker marker = enter_section_(builder, level, _NONE_, ORDER_ITEM, "<order item>");
    result = expression(builder, level + 1, -1);
    result = result && orderItem_1(builder, level + 1);
    exit_section_(builder, level, marker, result, false, null);
    return result;
  }

  // [ascKeyword | descKeyword]
  private static boolean orderItem_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "orderItem_1")) return false;
    orderItem_1_0(builder, level + 1);
    return true;
  }

  // ascKeyword | descKeyword
  private static boolean orderItem_1_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "orderItem_1_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, ASCKEYWORD);
    if (!result) result = consumeToken(builder, DESCKEYWORD);
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // rootQuery querySeparator?
  static boolean queryItem(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "queryItem")) return false;
    if (!nextTokenIs(builder, SELECTKEYWORD)) return false;
    boolean result, pinned;
    Marker marker = enter_section_(builder, level, _NONE_);
    result = rootQuery(builder, level + 1);
    pinned = result; // pin = 1
    result = result && queryItem_1(builder, level + 1);
    exit_section_(builder, level, marker, result, pinned, null);
    return result || pinned;
  }

  // querySeparator?
  private static boolean queryItem_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "queryItem_1")) return false;
    querySeparator(builder, level + 1);
    return true;
  }

  /* ********************************************************** */
  // SEMICOLON
  static boolean querySeparator(PsiBuilder builder, int level) {
    return consumeToken(builder, SEMICOLON);
  }

  /* ********************************************************** */
  // sqlQuery
  public static boolean rootQuery(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "rootQuery")) return false;
    if (!nextTokenIs(builder, SELECTKEYWORD)) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = sqlQuery(builder, level + 1);
    exit_section_(builder, marker, ROOT_QUERY, result);
    return result;
  }

  /* ********************************************************** */
  // selectKeyword topOpt distinctOpt selectionList
  // fromKeyword columnSource
  // joinItem*
  // [where_expression] [group_expression]
  public static boolean select_statement(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "select_statement")) return false;
    if (!nextTokenIs(builder, SELECTKEYWORD)) return false;
    boolean result, pinned;
    Marker marker = enter_section_(builder, level, _NONE_, SELECT_STATEMENT, null);
    result = consumeToken(builder, SELECTKEYWORD);
    pinned = result; // pin = 1
    result = result && report_error_(builder, topOpt(builder, level + 1));
    result = pinned && report_error_(builder, distinctOpt(builder, level + 1)) && result;
    result = pinned && report_error_(builder, selectionList(builder, level + 1)) && result;
    result = pinned && report_error_(builder, consumeToken(builder, FROMKEYWORD)) && result;
    result = pinned && report_error_(builder, columnSource(builder, level + 1)) && result;
    result = pinned && report_error_(builder, select_statement_6(builder, level + 1)) && result;
    result = pinned && report_error_(builder, select_statement_7(builder, level + 1)) && result;
    result = pinned && select_statement_8(builder, level + 1) && result;
    exit_section_(builder, level, marker, result, pinned, null);
    return result || pinned;
  }

  // joinItem*
  private static boolean select_statement_6(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "select_statement_6")) return false;
    int pos = current_position_(builder);
    while (true) {
      if (!joinItem(builder, level + 1)) break;
      if (!empty_element_parsed_guard_(builder, "select_statement_6", pos)) break;
      pos = current_position_(builder);
    }
    return true;
  }

  // [where_expression]
  private static boolean select_statement_7(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "select_statement_7")) return false;
    where_expression(builder, level + 1);
    return true;
  }

  // [group_expression]
  private static boolean select_statement_8(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "select_statement_8")) return false;
    group_expression(builder, level + 1);
    return true;
  }

  /* ********************************************************** */
  // expression alias?
  public static boolean selectionItem(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "selectionItem")) return false;
    boolean result, pinned;
    Marker marker = enter_section_(builder, level, _NONE_, SELECTION_ITEM, "<selection item>");
    result = expression(builder, level + 1, -1);
    pinned = result; // pin = 1
    result = result && selectionItem_1(builder, level + 1);
    exit_section_(builder, level, marker, result, pinned, null);
    return result || pinned;
  }

  // alias?
  private static boolean selectionItem_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "selectionItem_1")) return false;
    alias(builder, level + 1);
    return true;
  }

  /* ********************************************************** */
  // ASTERISK | selectionItem (COMMA selectionItem)*
  public static boolean selectionList(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "selectionList")) return false;
    boolean result;
    Marker marker = enter_section_(builder, level, _NONE_, SELECTION_LIST, "<selection list>");
    result = consumeToken(builder, ASTERISK);
    if (!result) result = selectionList_1(builder, level + 1);
    exit_section_(builder, level, marker, result, false, null);
    return result;
  }

  // selectionItem (COMMA selectionItem)*
  private static boolean selectionList_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "selectionList_1")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = selectionItem(builder, level + 1);
    result = result && selectionList_1_1(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // (COMMA selectionItem)*
  private static boolean selectionList_1_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "selectionList_1_1")) return false;
    int pos = current_position_(builder);
    while (true) {
      if (!selectionList_1_1_0(builder, level + 1)) break;
      if (!empty_element_parsed_guard_(builder, "selectionList_1_1", pos)) break;
      pos = current_position_(builder);
    }
    return true;
  }

  // COMMA selectionItem
  private static boolean selectionList_1_1_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "selectionList_1_1_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, COMMA);
    result = result && selectionItem(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // unionList [orderKeyword (byKeyword | поKeyword) orderByExpressionList]
  public static boolean sqlQuery(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "sqlQuery")) return false;
    if (!nextTokenIs(builder, SELECTKEYWORD)) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = unionList(builder, level + 1);
    result = result && sqlQuery_1(builder, level + 1);
    exit_section_(builder, marker, SQL_QUERY, result);
    return result;
  }

  // [orderKeyword (byKeyword | поKeyword) orderByExpressionList]
  private static boolean sqlQuery_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "sqlQuery_1")) return false;
    sqlQuery_1_0(builder, level + 1);
    return true;
  }

  // orderKeyword (byKeyword | поKeyword) orderByExpressionList
  private static boolean sqlQuery_1_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "sqlQuery_1_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, ORDERKEYWORD);
    result = result && sqlQuery_1_0_1(builder, level + 1);
    result = result && orderByExpressionList(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // byKeyword | поKeyword
  private static boolean sqlQuery_1_0_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "sqlQuery_1_0_1")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, BYKEYWORD);
    if (!result) result = consumeToken(builder, ПОKEYWORD);
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // LPAREN sqlQuery RPAREN
  public static boolean subquery(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "subquery")) return false;
    if (!nextTokenIs(builder, LPAREN)) return false;
    boolean result, pinned;
    Marker marker = enter_section_(builder, level, _NONE_, SUBQUERY, null);
    result = consumeToken(builder, LPAREN);
    pinned = result; // pin = 1
    result = result && report_error_(builder, sqlQuery(builder, level + 1));
    result = pinned && consumeToken(builder, RPAREN) && result;
    exit_section_(builder, level, marker, result, pinned, null);
    return result || pinned;
  }

  /* ********************************************************** */
  // subquery alias
  public static boolean subqueryTable(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "subqueryTable")) return false;
    if (!nextTokenIs(builder, LPAREN)) return false;
    boolean result, pinned;
    Marker marker = enter_section_(builder, level, _NONE_, SUBQUERY_TABLE, null);
    result = subquery(builder, level + 1);
    pinned = result; // pin = 1
    result = result && alias(builder, level + 1);
    exit_section_(builder, level, marker, result, pinned, null);
    return result || pinned;
  }

  /* ********************************************************** */
  // identifier alias?
  public static boolean tableDeclaration(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "tableDeclaration")) return false;
    if (!nextTokenIs(builder, ID_TOKEN)) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = identifier(builder, level + 1);
    result = result && tableDeclaration_1(builder, level + 1);
    exit_section_(builder, marker, TABLE_DECLARATION, result);
    return result;
  }

  // alias?
  private static boolean tableDeclaration_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "tableDeclaration_1")) return false;
    alias(builder, level + 1);
    return true;
  }

  /* ********************************************************** */
  // [topKeyword numberLiteral]
  public static boolean topOpt(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "topOpt")) return false;
    Marker marker = enter_section_(builder, level, _NONE_, TOP_OPT, "<top opt>");
    topOpt_0(builder, level + 1);
    exit_section_(builder, level, marker, true, false, null);
    return true;
  }

  // topKeyword numberLiteral
  private static boolean topOpt_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "topOpt_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, TOPKEYWORD);
    result = result && numberLiteral(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // unionKeyword allKeyword? select_statement
  public static boolean unionClause(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "unionClause")) return false;
    if (!nextTokenIs(builder, UNIONKEYWORD)) return false;
    boolean result, pinned;
    Marker marker = enter_section_(builder, level, _NONE_, UNION_CLAUSE, null);
    result = consumeToken(builder, UNIONKEYWORD);
    pinned = result; // pin = 1
    result = result && report_error_(builder, unionClause_1(builder, level + 1));
    result = pinned && select_statement(builder, level + 1) && result;
    exit_section_(builder, level, marker, result, pinned, null);
    return result || pinned;
  }

  // allKeyword?
  private static boolean unionClause_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "unionClause_1")) return false;
    consumeToken(builder, ALLKEYWORD);
    return true;
  }

  /* ********************************************************** */
  // select_statement (unionClause)*
  static boolean unionList(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "unionList")) return false;
    if (!nextTokenIs(builder, SELECTKEYWORD)) return false;
    boolean result, pinned;
    Marker marker = enter_section_(builder, level, _NONE_);
    result = select_statement(builder, level + 1);
    pinned = result; // pin = 1
    result = result && unionList_1(builder, level + 1);
    exit_section_(builder, level, marker, result, pinned, null);
    return result || pinned;
  }

  // (unionClause)*
  private static boolean unionList_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "unionList_1")) return false;
    int pos = current_position_(builder);
    while (true) {
      if (!unionList_1_0(builder, level + 1)) break;
      if (!empty_element_parsed_guard_(builder, "unionList_1", pos)) break;
      pos = current_position_(builder);
    }
    return true;
  }

  // (unionClause)
  private static boolean unionList_1_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "unionList_1_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = unionClause(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // whereKeyword expression
  static boolean where_expression(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "where_expression")) return false;
    if (!nextTokenIs(builder, WHEREKEYWORD)) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, WHEREKEYWORD);
    result = result && expression(builder, level + 1, -1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // Expression root: expression
  // Operator priority table:
  // 0: BINARY(andExpr) BINARY(orExpr)
  // 1: PREFIX(unaryNotExpr)
  // 2: BINARY(eqExpr) BINARY(neqExpr) BINARY(ltExpr) BINARY(lteExpr)
  //    BINARY(gtExpr) BINARY(gteExpr) BINARY(likeExpr) BINARY(notLikeExpr)
  // 3: POSTFIX(isNullExpr)
  // 4: BINARY(addExpr) BINARY(subtractExpr)
  // 5: BINARY(multExpr) BINARY(divExpr)
  // 6: PREFIX(unaryNegExpr)
  // 7: ATOM(aggregateExpression) POSTFIX(queryFunctionExpression) ATOM(caseExpression) POSTFIX(inExpression)
  //    ATOM(parExpr) ATOM(identifier) ATOM(numberLiteral) ATOM(boolLiteral)
  //    ATOM(nullLiteral) ATOM(stringLiteral)
  public static boolean expression(PsiBuilder builder, int level, int priority) {
    if (!recursion_guard_(builder, level, "expression")) return false;
    addVariant(builder, "<expression>");
    boolean result, pinned;
    Marker marker = enter_section_(builder, level, _NONE_, "<expression>");
    result = unaryNotExpr(builder, level + 1);
    if (!result) result = unaryNegExpr(builder, level + 1);
    if (!result) result = aggregateExpression(builder, level + 1);
    if (!result) result = caseExpression(builder, level + 1);
    if (!result) result = parExpr(builder, level + 1);
    if (!result) result = identifier(builder, level + 1);
    if (!result) result = numberLiteral(builder, level + 1);
    if (!result) result = boolLiteral(builder, level + 1);
    if (!result) result = nullLiteral(builder, level + 1);
    if (!result) result = stringLiteral(builder, level + 1);
    pinned = result;
    result = result && expression_0(builder, level + 1, priority);
    exit_section_(builder, level, marker, null, result, pinned, null);
    return result || pinned;
  }

  public static boolean expression_0(PsiBuilder builder, int level, int priority) {
    if (!recursion_guard_(builder, level, "expression_0")) return false;
    boolean result = true;
    while (true) {
      Marker marker = enter_section_(builder, level, _LEFT_, null);
      if (priority < 0 && consumeTokenSmart(builder, OP_AND)) {
        result = expression(builder, level, 0);
        exit_section_(builder, level, marker, BINARY_EXPRESSION, result, true, null);
      }
      else if (priority < 0 && consumeTokenSmart(builder, OP_OR)) {
        result = expression(builder, level, 0);
        exit_section_(builder, level, marker, BINARY_EXPRESSION, result, true, null);
      }
      else if (priority < 2 && consumeTokenSmart(builder, OP_EQ)) {
        result = expression(builder, level, 2);
        exit_section_(builder, level, marker, BINARY_EXPRESSION, result, true, null);
      }
      else if (priority < 2 && consumeTokenSmart(builder, OP_NEQ)) {
        result = expression(builder, level, 2);
        exit_section_(builder, level, marker, BINARY_EXPRESSION, result, true, null);
      }
      else if (priority < 2 && consumeTokenSmart(builder, OP_LT)) {
        result = expression(builder, level, 2);
        exit_section_(builder, level, marker, BINARY_EXPRESSION, result, true, null);
      }
      else if (priority < 2 && consumeTokenSmart(builder, OP_LTE)) {
        result = expression(builder, level, 2);
        exit_section_(builder, level, marker, BINARY_EXPRESSION, result, true, null);
      }
      else if (priority < 2 && consumeTokenSmart(builder, OP_GT)) {
        result = expression(builder, level, 2);
        exit_section_(builder, level, marker, BINARY_EXPRESSION, result, true, null);
      }
      else if (priority < 2 && consumeTokenSmart(builder, OP_GTE)) {
        result = expression(builder, level, 2);
        exit_section_(builder, level, marker, BINARY_EXPRESSION, result, true, null);
      }
      else if (priority < 2 && consumeTokenSmart(builder, OP_LIKE)) {
        result = expression(builder, level, 2);
        exit_section_(builder, level, marker, BINARY_EXPRESSION, result, true, null);
      }
      else if (priority < 2 && parseTokensSmart(builder, 0, OP_NOT, OP_LIKE)) {
        result = expression(builder, level, 2);
        exit_section_(builder, level, marker, BINARY_EXPRESSION, result, true, null);
      }
      else if (priority < 3 && isNullExpr_0(builder, level + 1)) {
        result = true;
        exit_section_(builder, level, marker, UNARY_EXPRESSION, result, true, null);
      }
      else if (priority < 4 && consumeTokenSmart(builder, OP_PLUS)) {
        result = expression(builder, level, 4);
        exit_section_(builder, level, marker, BINARY_EXPRESSION, result, true, null);
      }
      else if (priority < 4 && consumeTokenSmart(builder, OP_MINUS)) {
        result = expression(builder, level, 4);
        exit_section_(builder, level, marker, BINARY_EXPRESSION, result, true, null);
      }
      else if (priority < 5 && consumeTokenSmart(builder, ASTERISK)) {
        result = expression(builder, level, 5);
        exit_section_(builder, level, marker, BINARY_EXPRESSION, result, true, null);
      }
      else if (priority < 5 && consumeTokenSmart(builder, OP_DIV)) {
        result = expression(builder, level, 5);
        exit_section_(builder, level, marker, BINARY_EXPRESSION, result, true, null);
      }
      else if (priority < 7 && leftMarkerIs(builder, IDENTIFIER) && queryFunctionExpression_0(builder, level + 1)) {
        result = true;
        exit_section_(builder, level, marker, QUERY_FUNCTION_EXPRESSION, result, true, null);
      }
      else if (priority < 7 && inExpression_0(builder, level + 1)) {
        result = true;
        exit_section_(builder, level, marker, IN_EXPRESSION, result, true, null);
      }
      else {
        exit_section_(builder, level, marker, null, false, false, null);
        break;
      }
    }
    return result;
  }

  public static boolean unaryNotExpr(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "unaryNotExpr")) return false;
    if (!nextTokenIsSmart(builder, OP_NOT)) return false;
    boolean result, pinned;
    Marker marker = enter_section_(builder, level, _NONE_, null);
    result = consumeTokenSmart(builder, OP_NOT);
    pinned = result;
    result = pinned && expression(builder, level, 1);
    exit_section_(builder, level, marker, UNARY_EXPRESSION, result, pinned, null);
    return result || pinned;
  }

  // OP_IS OP_NOT? null
  private static boolean isNullExpr_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "isNullExpr_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeTokenSmart(builder, OP_IS);
    result = result && isNullExpr_0_1(builder, level + 1);
    result = result && consumeToken(builder, NULL);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // OP_NOT?
  private static boolean isNullExpr_0_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "isNullExpr_0_1")) return false;
    consumeTokenSmart(builder, OP_NOT);
    return true;
  }

  public static boolean unaryNegExpr(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "unaryNegExpr")) return false;
    if (!nextTokenIsSmart(builder, OP_MINUS)) return false;
    boolean result, pinned;
    Marker marker = enter_section_(builder, level, _NONE_, null);
    result = consumeTokenSmart(builder, OP_MINUS);
    pinned = result;
    result = pinned && expression(builder, level, 6);
    exit_section_(builder, level, marker, UNARY_EXPRESSION, result, pinned, null);
    return result || pinned;
  }

  // aggregationFunction LPAREN (ASTERISK | distinctOpt expression) RPAREN
  public static boolean aggregateExpression(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "aggregateExpression")) return false;
    if (!nextTokenIsSmart(builder, AGGREGATIONFUNCTION)) return false;
    boolean result, pinned;
    Marker marker = enter_section_(builder, level, _NONE_, AGGREGATE_EXPRESSION, null);
    result = consumeTokens(builder, 2, AGGREGATIONFUNCTION, LPAREN);
    pinned = result; // pin = 2
    result = result && report_error_(builder, aggregateExpression_2(builder, level + 1));
    result = pinned && consumeToken(builder, RPAREN) && result;
    exit_section_(builder, level, marker, result, pinned, null);
    return result || pinned;
  }

  // ASTERISK | distinctOpt expression
  private static boolean aggregateExpression_2(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "aggregateExpression_2")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeTokenSmart(builder, ASTERISK);
    if (!result) result = aggregateExpression_2_1(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // distinctOpt expression
  private static boolean aggregateExpression_2_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "aggregateExpression_2_1")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = distinctOpt(builder, level + 1);
    result = result && expression(builder, level + 1, -1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // LPAREN expressionList RPAREN
  private static boolean queryFunctionExpression_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "queryFunctionExpression_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeTokenSmart(builder, LPAREN);
    result = result && expressionList(builder, level + 1);
    result = result && consumeToken(builder, RPAREN);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // caseKeyword caseElementList [elseKeyword expression] endKeyword
  public static boolean caseExpression(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "caseExpression")) return false;
    if (!nextTokenIsSmart(builder, CASEKEYWORD)) return false;
    boolean result, pinned;
    Marker marker = enter_section_(builder, level, _NONE_, CASE_EXPRESSION, null);
    result = consumeTokenSmart(builder, CASEKEYWORD);
    pinned = result; // pin = 1
    result = result && report_error_(builder, caseElementList(builder, level + 1));
    result = pinned && report_error_(builder, caseExpression_2(builder, level + 1)) && result;
    result = pinned && consumeToken(builder, ENDKEYWORD) && result;
    exit_section_(builder, level, marker, result, pinned, null);
    return result || pinned;
  }

  // [elseKeyword expression]
  private static boolean caseExpression_2(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "caseExpression_2")) return false;
    caseExpression_2_0(builder, level + 1);
    return true;
  }

  // elseKeyword expression
  private static boolean caseExpression_2_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "caseExpression_2_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeTokenSmart(builder, ELSEKEYWORD);
    result = result && expression(builder, level + 1, -1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // OP_IN (LPAREN expressionList RPAREN | subquery)
  private static boolean inExpression_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "inExpression_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeTokenSmart(builder, OP_IN);
    result = result && inExpression_0_1(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // LPAREN expressionList RPAREN | subquery
  private static boolean inExpression_0_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "inExpression_0_1")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = inExpression_0_1_0(builder, level + 1);
    if (!result) result = subquery(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // LPAREN expressionList RPAREN
  private static boolean inExpression_0_1_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "inExpression_0_1_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeTokenSmart(builder, LPAREN);
    result = result && expressionList(builder, level + 1);
    result = result && consumeToken(builder, RPAREN);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // '(' expression ')'
  public static boolean parExpr(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "parExpr")) return false;
    if (!nextTokenIsSmart(builder, LPAREN)) return false;
    boolean result, pinned;
    Marker marker = enter_section_(builder, level, _NONE_, EXPRESSION, null);
    result = consumeTokenSmart(builder, LPAREN);
    pinned = result; // pin = 1
    result = result && report_error_(builder, expression(builder, level + 1, -1));
    result = pinned && consumeToken(builder, RPAREN) && result;
    exit_section_(builder, level, marker, result, pinned, null);
    return result || pinned;
  }

  // ID_TOKEN
  public static boolean identifier(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "identifier")) return false;
    if (!nextTokenIsSmart(builder, ID_TOKEN)) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeTokenSmart(builder, ID_TOKEN);
    exit_section_(builder, marker, IDENTIFIER, result);
    return result;
  }

  // number
  public static boolean numberLiteral(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "numberLiteral")) return false;
    if (!nextTokenIsSmart(builder, NUMBER)) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeTokenSmart(builder, NUMBER);
    exit_section_(builder, marker, NUMBER_LITERAL, result);
    return result;
  }

  // bool
  public static boolean boolLiteral(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "boolLiteral")) return false;
    if (!nextTokenIsSmart(builder, BOOL)) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeTokenSmart(builder, BOOL);
    exit_section_(builder, marker, BOOL_LITERAL, result);
    return result;
  }

  // null
  public static boolean nullLiteral(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "nullLiteral")) return false;
    if (!nextTokenIsSmart(builder, NULL)) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeTokenSmart(builder, NULL);
    exit_section_(builder, marker, NULL_LITERAL, result);
    return result;
  }

  // string
  public static boolean stringLiteral(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "stringLiteral")) return false;
    if (!nextTokenIsSmart(builder, STRING)) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeTokenSmart(builder, STRING);
    exit_section_(builder, marker, STRING_LITERAL, result);
    return result;
  }

}
