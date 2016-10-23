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
    create_token_set_(BINARY_EXPRESSION, BOOL_LITERAL, EXPRESSION, IDENTIFIER,
      NUMBER_LITERAL, STRING_LITERAL, UNARY_EXPRESSION),
  };

  /* ********************************************************** */
  // asKeyword? identifier
  public static boolean alias(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "alias")) return false;
    if (!nextTokenIs(builder, "<alias>", ASKEYWORD, IDSIMPLE)) return false;
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
  // tableDeclaration | subqueryTable
  public static boolean columnSource(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "columnSource")) return false;
    if (!nextTokenIs(builder, "<column source>", LPAREN, IDSIMPLE)) return false;
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
  // (querySeparator? sqlQuery)*
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

  // querySeparator? sqlQuery
  private static boolean grammar_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "grammar_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = grammar_0_0(builder, level + 1);
    result = result && sqlQuery(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // querySeparator?
  private static boolean grammar_0_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "grammar_0_0")) return false;
    querySeparator(builder, level + 1);
    return true;
  }

  /* ********************************************************** */
  // joinKind? joinKeyword columnSource (onKeyword | поKeyword) expression
  public static boolean joinItem(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "joinItem")) return false;
    boolean result;
    Marker marker = enter_section_(builder, level, _NONE_, JOIN_ITEM, "<join item>");
    result = joinItem_0(builder, level + 1);
    result = result && consumeToken(builder, JOINKEYWORD);
    result = result && columnSource(builder, level + 1);
    result = result && joinItem_3(builder, level + 1);
    result = result && expression(builder, level + 1, -1);
    exit_section_(builder, level, marker, result, false, null);
    return result;
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
  // !fromKeyword
  static boolean notFromKeyword(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "notFromKeyword")) return false;
    boolean result;
    Marker marker = enter_section_(builder, level, _NOT_);
    result = !consumeToken(builder, FROMKEYWORD);
    exit_section_(builder, level, marker, result, false, null);
    return result;
  }

  /* ********************************************************** */
  // !(querySeparator)
  static boolean notSeparator(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "notSeparator")) return false;
    boolean result;
    Marker marker = enter_section_(builder, level, _NOT_);
    result = !notSeparator_0(builder, level + 1);
    exit_section_(builder, level, marker, result, false, null);
    return result;
  }

  // (querySeparator)
  private static boolean notSeparator_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "notSeparator_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = querySeparator(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
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
  // SEMICOLON
  static boolean querySeparator(PsiBuilder builder, int level) {
    return consumeToken(builder, SEMICOLON);
  }

  /* ********************************************************** */
  // ASTERISK | selectionList
  static boolean selectList(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "selectList")) return false;
    boolean result;
    Marker marker = enter_section_(builder, level, _NONE_);
    result = consumeToken(builder, ASTERISK);
    if (!result) result = selectionList(builder, level + 1);
    exit_section_(builder, level, marker, result, false, notFromKeyword_parser_);
    return result;
  }

  /* ********************************************************** */
  // !<<eof>> selectKeyword topOpt distinctOpt selectList
  // fromKeyword columnSource
  // joinItem*
  // [whereKeyword expression]
  // [groupKeyword (byKeyword| поKeyword) expressionList [havingKeyword expression]]
  public static boolean select_statement(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "select_statement")) return false;
    boolean result, pinned;
    Marker marker = enter_section_(builder, level, _NONE_, SELECT_STATEMENT, "<select statement>");
    result = select_statement_0(builder, level + 1);
    pinned = result; // pin = 1
    result = result && report_error_(builder, consumeToken(builder, SELECTKEYWORD));
    result = pinned && report_error_(builder, topOpt(builder, level + 1)) && result;
    result = pinned && report_error_(builder, distinctOpt(builder, level + 1)) && result;
    result = pinned && report_error_(builder, selectList(builder, level + 1)) && result;
    result = pinned && report_error_(builder, consumeToken(builder, FROMKEYWORD)) && result;
    result = pinned && report_error_(builder, columnSource(builder, level + 1)) && result;
    result = pinned && report_error_(builder, select_statement_7(builder, level + 1)) && result;
    result = pinned && report_error_(builder, select_statement_8(builder, level + 1)) && result;
    result = pinned && select_statement_9(builder, level + 1) && result;
    exit_section_(builder, level, marker, result, pinned, notSeparator_parser_);
    return result || pinned;
  }

  // !<<eof>>
  private static boolean select_statement_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "select_statement_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder, level, _NOT_);
    result = !eof(builder, level + 1);
    exit_section_(builder, level, marker, result, false, null);
    return result;
  }

  // joinItem*
  private static boolean select_statement_7(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "select_statement_7")) return false;
    int pos = current_position_(builder);
    while (true) {
      if (!joinItem(builder, level + 1)) break;
      if (!empty_element_parsed_guard_(builder, "select_statement_7", pos)) break;
      pos = current_position_(builder);
    }
    return true;
  }

  // [whereKeyword expression]
  private static boolean select_statement_8(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "select_statement_8")) return false;
    select_statement_8_0(builder, level + 1);
    return true;
  }

  // whereKeyword expression
  private static boolean select_statement_8_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "select_statement_8_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, WHEREKEYWORD);
    result = result && expression(builder, level + 1, -1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // [groupKeyword (byKeyword| поKeyword) expressionList [havingKeyword expression]]
  private static boolean select_statement_9(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "select_statement_9")) return false;
    select_statement_9_0(builder, level + 1);
    return true;
  }

  // groupKeyword (byKeyword| поKeyword) expressionList [havingKeyword expression]
  private static boolean select_statement_9_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "select_statement_9_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, GROUPKEYWORD);
    result = result && select_statement_9_0_1(builder, level + 1);
    result = result && expressionList(builder, level + 1);
    result = result && select_statement_9_0_3(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // byKeyword| поKeyword
  private static boolean select_statement_9_0_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "select_statement_9_0_1")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, BYKEYWORD);
    if (!result) result = consumeToken(builder, ПОKEYWORD);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // [havingKeyword expression]
  private static boolean select_statement_9_0_3(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "select_statement_9_0_3")) return false;
    select_statement_9_0_3_0(builder, level + 1);
    return true;
  }

  // havingKeyword expression
  private static boolean select_statement_9_0_3_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "select_statement_9_0_3_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, HAVINGKEYWORD);
    result = result && expression(builder, level + 1, -1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // expression alias?
  public static boolean selectionItem(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "selectionItem")) return false;
    boolean result;
    Marker marker = enter_section_(builder, level, _NONE_, SELECTION_ITEM, "<selection item>");
    result = expression(builder, level + 1, -1);
    result = result && selectionItem_1(builder, level + 1);
    exit_section_(builder, level, marker, result, false, null);
    return result;
  }

  // alias?
  private static boolean selectionItem_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "selectionItem_1")) return false;
    alias(builder, level + 1);
    return true;
  }

  /* ********************************************************** */
  // selectionItem (COMMA selectionItem)*{
  //     //TODO. isSelectAll
  // }
  public static boolean selectionList(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "selectionList")) return false;
    boolean result;
    Marker marker = enter_section_(builder, level, _NONE_, SELECTION_LIST, "<selection list>");
    result = selectionItem(builder, level + 1);
    result = result && selectionList_1(builder, level + 1);
    result = result && selectionList_2(builder, level + 1);
    exit_section_(builder, level, marker, result, false, null);
    return result;
  }

  // (COMMA selectionItem)*
  private static boolean selectionList_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "selectionList_1")) return false;
    int pos = current_position_(builder);
    while (true) {
      if (!selectionList_1_0(builder, level + 1)) break;
      if (!empty_element_parsed_guard_(builder, "selectionList_1", pos)) break;
      pos = current_position_(builder);
    }
    return true;
  }

  // COMMA selectionItem
  private static boolean selectionList_1_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "selectionList_1_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, COMMA);
    result = result && selectionItem(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // {
  //     //TODO. isSelectAll
  // }
  private static boolean selectionList_2(PsiBuilder builder, int level) {
    return true;
  }

  /* ********************************************************** */
  // unionList [orderKeyword (byKeyword| поKeyword) orderByExpressionList]
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

  // [orderKeyword (byKeyword| поKeyword) orderByExpressionList]
  private static boolean sqlQuery_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "sqlQuery_1")) return false;
    sqlQuery_1_0(builder, level + 1);
    return true;
  }

  // orderKeyword (byKeyword| поKeyword) orderByExpressionList
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

  // byKeyword| поKeyword
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
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, LPAREN);
    result = result && sqlQuery(builder, level + 1);
    result = result && consumeToken(builder, RPAREN);
    exit_section_(builder, marker, SUBQUERY, result);
    return result;
  }

  /* ********************************************************** */
  // subquery alias
  public static boolean subqueryTable(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "subqueryTable")) return false;
    if (!nextTokenIs(builder, LPAREN)) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = subquery(builder, level + 1);
    result = result && alias(builder, level + 1);
    exit_section_(builder, marker, SUBQUERY_TABLE, result);
    return result;
  }

  /* ********************************************************** */
  // identifier alias?
  public static boolean tableDeclaration(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "tableDeclaration")) return false;
    if (!nextTokenIs(builder, IDSIMPLE)) return false;
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
  // OP_NOT expression
  public static boolean unaryNotExpr(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "unaryNotExpr")) return false;
    if (!nextTokenIs(builder, OP_NOT)) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, OP_NOT);
    result = result && expression(builder, level + 1, -1);
    exit_section_(builder, marker, UNARY_EXPRESSION, result);
    return result;
  }

  /* ********************************************************** */
  // select_statement (unionKeyword allKeyword? select_statement)*
  static boolean unionList(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "unionList")) return false;
    if (!nextTokenIs(builder, SELECTKEYWORD)) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = select_statement(builder, level + 1);
    result = result && unionList_1(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // (unionKeyword allKeyword? select_statement)*
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

  // unionKeyword allKeyword? select_statement
  private static boolean unionList_1_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "unionList_1_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, UNIONKEYWORD);
    result = result && unionList_1_0_1(builder, level + 1);
    result = result && select_statement(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // allKeyword?
  private static boolean unionList_1_0_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "unionList_1_0_1")) return false;
    consumeToken(builder, ALLKEYWORD);
    return true;
  }

  /* ********************************************************** */
  // Expression root: expression
  // Operator priority table:
  // 0: BINARY(andExpr) BINARY(orExpr)
  // 1:
  // 2: BINARY(eqExpr) BINARY(neqExpr) BINARY(ltExpr) BINARY(lteExpr)
  //    BINARY(gtExpr) BINARY(gteExpr) BINARY(likeExpr)
  // 3: BINARY(addExpr) BINARY(subtractExpr)
  // 4: BINARY(multExpr) BINARY(divExpr) BINARY(remExpr)
  // 5: ATOM(identifier) ATOM(numberLiteral) ATOM(boolLiteral) ATOM(stringLiteral)
  //    PREFIX(parExpr)
  public static boolean expression(PsiBuilder builder, int level, int priority) {
    if (!recursion_guard_(builder, level, "expression")) return false;
    addVariant(builder, "<expression>");
    boolean result, pinned;
    Marker marker = enter_section_(builder, level, _NONE_, "<expression>");
    result = identifier(builder, level + 1);
    if (!result) result = numberLiteral(builder, level + 1);
    if (!result) result = boolLiteral(builder, level + 1);
    if (!result) result = stringLiteral(builder, level + 1);
    if (!result) result = parExpr(builder, level + 1);
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
      else if (priority < 3 && consumeTokenSmart(builder, OP_PLUS)) {
        result = expression(builder, level, 3);
        exit_section_(builder, level, marker, BINARY_EXPRESSION, result, true, null);
      }
      else if (priority < 3 && consumeTokenSmart(builder, OP_MINUS)) {
        result = expression(builder, level, 3);
        exit_section_(builder, level, marker, BINARY_EXPRESSION, result, true, null);
      }
      else if (priority < 4 && consumeTokenSmart(builder, ASTERISK)) {
        result = expression(builder, level, 4);
        exit_section_(builder, level, marker, BINARY_EXPRESSION, result, true, null);
      }
      else if (priority < 4 && consumeTokenSmart(builder, OP_DIV)) {
        result = expression(builder, level, 4);
        exit_section_(builder, level, marker, BINARY_EXPRESSION, result, true, null);
      }
      else if (priority < 4 && consumeTokenSmart(builder, OP_REM)) {
        result = expression(builder, level, 4);
        exit_section_(builder, level, marker, BINARY_EXPRESSION, result, true, null);
      }
      else {
        exit_section_(builder, level, marker, null, false, false, null);
        break;
      }
    }
    return result;
  }

  // idSimple (DOT idSimple)*
  public static boolean identifier(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "identifier")) return false;
    if (!nextTokenIsSmart(builder, IDSIMPLE)) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeTokenSmart(builder, IDSIMPLE);
    result = result && identifier_1(builder, level + 1);
    exit_section_(builder, marker, IDENTIFIER, result);
    return result;
  }

  // (DOT idSimple)*
  private static boolean identifier_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "identifier_1")) return false;
    int pos = current_position_(builder);
    while (true) {
      if (!identifier_1_0(builder, level + 1)) break;
      if (!empty_element_parsed_guard_(builder, "identifier_1", pos)) break;
      pos = current_position_(builder);
    }
    return true;
  }

  // DOT idSimple
  private static boolean identifier_1_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "identifier_1_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeTokens(builder, 0, DOT, IDSIMPLE);
    exit_section_(builder, marker, null, result);
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

  public static boolean parExpr(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "parExpr")) return false;
    if (!nextTokenIsSmart(builder, LPAREN)) return false;
    boolean result, pinned;
    Marker marker = enter_section_(builder, level, _NONE_, null);
    result = consumeTokenSmart(builder, LPAREN);
    pinned = result;
    result = pinned && expression(builder, level, -1);
    result = pinned && report_error_(builder, consumeToken(builder, RPAREN)) && result;
    exit_section_(builder, level, marker, EXPRESSION, result, pinned, null);
    return result || pinned;
  }

  final static Parser notFromKeyword_parser_ = new Parser() {
    public boolean parse(PsiBuilder builder, int level) {
      return notFromKeyword(builder, level + 1);
    }
  };
  final static Parser notSeparator_parser_ = new Parser() {
    public boolean parse(PsiBuilder builder, int level) {
      return notSeparator(builder, level + 1);
    }
  };
}
