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
    else if (type == BOOL_LITERAL) {
      result = boolLiteral(builder, 0);
    }
    else if (type == COLUMN_SOURCE) {
      result = columnSource(builder, 0);
    }
    else if (type == EXPRESSION) {
      result = expression(builder, 0);
    }
    else if (type == EXPRESSION_LIST) {
      result = expressionList(builder, 0);
    }
    else if (type == IDENTIFIER) {
      result = identifier(builder, 0);
    }
    else if (type == JOIN_ITEM) {
      result = joinItem(builder, 0);
    }
    else if (type == JOIN_KIND) {
      result = joinKind(builder, 0);
    }
    else if (type == MUL_GROUP) {
      result = mul_group(builder, 0);
    }
    else if (type == NUMBER_LITERAL) {
      result = numberLiteral(builder, 0);
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
    else if (type == STRING_LITERAL) {
      result = stringLiteral(builder, 0);
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
    create_token_set_(BOOL_LITERAL, EXPRESSION, IDENTIFIER, NUMBER_LITERAL,
      STRING_LITERAL),
  };

  /* ********************************************************** */
  // expression '+' expression | expression '-' expression
  static boolean add_group(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "add_group")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = add_group_0(builder, level + 1);
    if (!result) result = add_group_1(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // expression '+' expression
  private static boolean add_group_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "add_group_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = expression(builder, level + 1);
    result = result && consumeToken(builder, "+");
    result = result && expression(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // expression '-' expression
  private static boolean add_group_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "add_group_1")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = expression(builder, level + 1);
    result = result && consumeToken(builder, "-");
    result = result && expression(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // as? identifier
  public static boolean alias(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "alias")) return false;
    if (!nextTokenIs(builder, "<alias>", AS, IDSIMPLE)) return false;
    boolean result;
    Marker marker = enter_section_(builder, level, _NONE_, ALIAS, "<alias>");
    result = alias_0(builder, level + 1);
    result = result && identifier(builder, level + 1);
    exit_section_(builder, level, marker, result, false, null);
    return result;
  }

  // as?
  private static boolean alias_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "alias_0")) return false;
    consumeToken(builder, AS);
    return true;
  }

  /* ********************************************************** */
  // [all | все]
  static boolean allModifierOpt(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "allModifierOpt")) return false;
    allModifierOpt_0(builder, level + 1);
    return true;
  }

  // all | все
  private static boolean allModifierOpt_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "allModifierOpt_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, ALL);
    if (!result) result = consumeToken(builder, ВСЕ);
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // asc | возр
  static boolean ascKeyword(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "ascKeyword")) return false;
    if (!nextTokenIs(builder, "", ASC, ВОЗР)) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, ASC);
    if (!result) result = consumeToken(builder, ВОЗР);
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // true | false | истина | ложь
  public static boolean boolLiteral(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "boolLiteral")) return false;
    boolean result;
    Marker marker = enter_section_(builder, level, _NONE_, BOOL_LITERAL, "<bool literal>");
    result = consumeToken(builder, TRUE);
    if (!result) result = consumeToken(builder, FALSE);
    if (!result) result = consumeToken(builder, ИСТИНА);
    if (!result) result = consumeToken(builder, ЛОЖЬ);
    exit_section_(builder, level, marker, result, false, null);
    return result;
  }

  /* ********************************************************** */
  // tableDeclaration | subqueryTable
  public static boolean columnSource(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "columnSource")) return false;
    if (!nextTokenIs(builder, "<column source>", LPAREN, IDSIMPLE)) return false;
    boolean result;
    Marker marker = enter_section_(builder, level, _NONE_, COLUMN_SOURCE, "<column source>");
    result = tableDeclaration(builder, level + 1);
    if (!result) result = subqueryTable(builder, level + 1);
    exit_section_(builder, level, marker, result, false, null);
    return result;
  }

  /* ********************************************************** */
  // expression EQ expression
  //     | expression ('<>' | '!=') expression
  //     | expression ('<') expression
  //     | expression ('<=') expression
  //     | expression ('>') expression
  //     | expression ('>=') expression
  //     | expression ('like' | 'подобно') expression
  static boolean comparison_group(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "comparison_group")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = comparison_group_0(builder, level + 1);
    if (!result) result = comparison_group_1(builder, level + 1);
    if (!result) result = comparison_group_2(builder, level + 1);
    if (!result) result = comparison_group_3(builder, level + 1);
    if (!result) result = comparison_group_4(builder, level + 1);
    if (!result) result = comparison_group_5(builder, level + 1);
    if (!result) result = comparison_group_6(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // expression EQ expression
  private static boolean comparison_group_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "comparison_group_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = expression(builder, level + 1);
    result = result && consumeToken(builder, EQ);
    result = result && expression(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // expression ('<>' | '!=') expression
  private static boolean comparison_group_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "comparison_group_1")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = expression(builder, level + 1);
    result = result && comparison_group_1_1(builder, level + 1);
    result = result && expression(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // '<>' | '!='
  private static boolean comparison_group_1_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "comparison_group_1_1")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, "<>");
    if (!result) result = consumeToken(builder, "!=");
    exit_section_(builder, marker, null, result);
    return result;
  }

  // expression ('<') expression
  private static boolean comparison_group_2(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "comparison_group_2")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = expression(builder, level + 1);
    result = result && comparison_group_2_1(builder, level + 1);
    result = result && expression(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // ('<')
  private static boolean comparison_group_2_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "comparison_group_2_1")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, "<");
    exit_section_(builder, marker, null, result);
    return result;
  }

  // expression ('<=') expression
  private static boolean comparison_group_3(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "comparison_group_3")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = expression(builder, level + 1);
    result = result && comparison_group_3_1(builder, level + 1);
    result = result && expression(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // ('<=')
  private static boolean comparison_group_3_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "comparison_group_3_1")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, "<=");
    exit_section_(builder, marker, null, result);
    return result;
  }

  // expression ('>') expression
  private static boolean comparison_group_4(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "comparison_group_4")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = expression(builder, level + 1);
    result = result && comparison_group_4_1(builder, level + 1);
    result = result && expression(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // ('>')
  private static boolean comparison_group_4_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "comparison_group_4_1")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, ">");
    exit_section_(builder, marker, null, result);
    return result;
  }

  // expression ('>=') expression
  private static boolean comparison_group_5(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "comparison_group_5")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = expression(builder, level + 1);
    result = result && comparison_group_5_1(builder, level + 1);
    result = result && expression(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // ('>=')
  private static boolean comparison_group_5_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "comparison_group_5_1")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, ">=");
    exit_section_(builder, marker, null, result);
    return result;
  }

  // expression ('like' | 'подобно') expression
  private static boolean comparison_group_6(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "comparison_group_6")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = expression(builder, level + 1);
    result = result && comparison_group_6_1(builder, level + 1);
    result = result && expression(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // 'like' | 'подобно'
  private static boolean comparison_group_6_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "comparison_group_6_1")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, "like");
    if (!result) result = consumeToken(builder, "подобно");
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // desc | убыв
  static boolean descKeyword(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "descKeyword")) return false;
    if (!nextTokenIs(builder, "", DESC, УБЫВ)) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, DESC);
    if (!result) result = consumeToken(builder, УБЫВ);
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // distinct?
  static boolean distinctOpt(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "distinctOpt")) return false;
    consumeToken(builder, DISTINCT);
    return true;
  }

  /* ********************************************************** */
  // expression 'or' expression
  //   | expression 'and' expression
  //   | 'not' expression
  //   | comparison_group
  //   | add_group
  //   | mul_group
  //   | primary_group
  public static boolean expression(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "expression")) return false;
    boolean result;
    Marker marker = enter_section_(builder, level, _COLLAPSE_, EXPRESSION, "<expression>");
    result = expression_0(builder, level + 1);
    if (!result) result = expression_1(builder, level + 1);
    if (!result) result = expression_2(builder, level + 1);
    if (!result) result = comparison_group(builder, level + 1);
    if (!result) result = add_group(builder, level + 1);
    if (!result) result = mul_group(builder, level + 1);
    if (!result) result = primary_group(builder, level + 1);
    exit_section_(builder, level, marker, result, false, null);
    return result;
  }

  // expression 'or' expression
  private static boolean expression_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "expression_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = expression(builder, level + 1);
    result = result && consumeToken(builder, "or");
    result = result && expression(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // expression 'and' expression
  private static boolean expression_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "expression_1")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = expression(builder, level + 1);
    result = result && consumeToken(builder, "and");
    result = result && expression(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // 'not' expression
  private static boolean expression_2(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "expression_2")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, "not");
    result = result && expression(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // expression (COMMA expression)*
  public static boolean expressionList(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "expressionList")) return false;
    boolean result;
    Marker marker = enter_section_(builder, level, _NONE_, EXPRESSION_LIST, "<expression list>");
    result = expression(builder, level + 1);
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
    result = result && expression(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // from | из
  static boolean fromKeyword(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "fromKeyword")) return false;
    if (!nextTokenIs(builder, "", FROM, ИЗ)) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, FROM);
    if (!result) result = consumeToken(builder, ИЗ);
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // sqlQuery ((SEMICOLON) sqlQuery) ?
  static boolean grammar(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "grammar")) return false;
    if (!nextTokenIs(builder, SELECT)) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = sqlQuery(builder, level + 1);
    result = result && grammar_1(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // ((SEMICOLON) sqlQuery) ?
  private static boolean grammar_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "grammar_1")) return false;
    grammar_1_0(builder, level + 1);
    return true;
  }

  // (SEMICOLON) sqlQuery
  private static boolean grammar_1_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "grammar_1_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, SEMICOLON);
    result = result && sqlQuery(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // (group by) | (сгруппировать по)
  static boolean groupByKeyword(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "groupByKeyword")) return false;
    if (!nextTokenIs(builder, "", GROUP, СГРУППИРОВАТЬ)) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = groupByKeyword_0(builder, level + 1);
    if (!result) result = groupByKeyword_1(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // group by
  private static boolean groupByKeyword_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "groupByKeyword_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeTokens(builder, 0, GROUP, BY);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // сгруппировать по
  private static boolean groupByKeyword_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "groupByKeyword_1")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeTokens(builder, 0, СГРУППИРОВАТЬ, ПО);
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // having | имеющие
  static boolean havingKeyword(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "havingKeyword")) return false;
    if (!nextTokenIs(builder, "", HAVING, ИМЕЮЩИЕ)) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, HAVING);
    if (!result) result = consumeToken(builder, ИМЕЮЩИЕ);
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // idSimple (DOT idSimple)*
  public static boolean identifier(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "identifier")) return false;
    if (!nextTokenIs(builder, IDSIMPLE)) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, IDSIMPLE);
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

  /* ********************************************************** */
  // joinKind? joinKeyword columnSource (on | по) expression
  public static boolean joinItem(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "joinItem")) return false;
    boolean result;
    Marker marker = enter_section_(builder, level, _NONE_, JOIN_ITEM, "<join item>");
    result = joinItem_0(builder, level + 1);
    result = result && joinKeyword(builder, level + 1);
    result = result && columnSource(builder, level + 1);
    result = result && joinItem_3(builder, level + 1);
    result = result && expression(builder, level + 1);
    exit_section_(builder, level, marker, result, false, null);
    return result;
  }

  // joinKind?
  private static boolean joinItem_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "joinItem_0")) return false;
    joinKind(builder, level + 1);
    return true;
  }

  // on | по
  private static boolean joinItem_3(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "joinItem_3")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, ON);
    if (!result) result = consumeToken(builder, ПО);
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // join | соединение
  static boolean joinKeyword(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "joinKeyword")) return false;
    if (!nextTokenIs(builder, "", JOIN, СОЕДИНЕНИЕ)) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, JOIN);
    if (!result) result = consumeToken(builder, СОЕДИНЕНИЕ);
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // (inner | внутреннее) | (outerJoinKind outerKeywordOpt)
  public static boolean joinKind(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "joinKind")) return false;
    boolean result;
    Marker marker = enter_section_(builder, level, _NONE_, JOIN_KIND, "<join kind>");
    result = joinKind_0(builder, level + 1);
    if (!result) result = joinKind_1(builder, level + 1);
    exit_section_(builder, level, marker, result, false, null);
    return result;
  }

  // inner | внутреннее
  private static boolean joinKind_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "joinKind_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, INNER);
    if (!result) result = consumeToken(builder, ВНУТРЕННЕЕ);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // outerJoinKind outerKeywordOpt
  private static boolean joinKind_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "joinKind_1")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = outerJoinKind(builder, level + 1);
    result = result && outerKeywordOpt(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // expression '*' expression | expression '/' expression | expression '%' expression
  public static boolean mul_group(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "mul_group")) return false;
    boolean result;
    Marker marker = enter_section_(builder, level, _NONE_, MUL_GROUP, "<mul group>");
    result = mul_group_0(builder, level + 1);
    if (!result) result = mul_group_1(builder, level + 1);
    if (!result) result = mul_group_2(builder, level + 1);
    exit_section_(builder, level, marker, result, false, null);
    return result;
  }

  // expression '*' expression
  private static boolean mul_group_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "mul_group_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = expression(builder, level + 1);
    result = result && consumeToken(builder, WILDCARD);
    result = result && expression(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // expression '/' expression
  private static boolean mul_group_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "mul_group_1")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = expression(builder, level + 1);
    result = result && consumeToken(builder, "/");
    result = result && expression(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // expression '%' expression
  private static boolean mul_group_2(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "mul_group_2")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = expression(builder, level + 1);
    result = result && consumeToken(builder, "%");
    result = result && expression(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // !fromKeyword
  static boolean notFromKeyword(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "notFromKeyword")) return false;
    boolean result;
    Marker marker = enter_section_(builder, level, _NOT_);
    result = !fromKeyword(builder, level + 1);
    exit_section_(builder, level, marker, result, false, null);
    return result;
  }

  /* ********************************************************** */
  // number
  public static boolean numberLiteral(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "numberLiteral")) return false;
    if (!nextTokenIs(builder, NUMBER)) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, NUMBER);
    exit_section_(builder, marker, NUMBER_LITERAL, result);
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
  // (order by) | (упорядочить по)
  static boolean orderByKeyword(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "orderByKeyword")) return false;
    if (!nextTokenIs(builder, "", ORDER, УПОРЯДОЧИТЬ)) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = orderByKeyword_0(builder, level + 1);
    if (!result) result = orderByKeyword_1(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // order by
  private static boolean orderByKeyword_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "orderByKeyword_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeTokens(builder, 0, ORDER, BY);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // упорядочить по
  private static boolean orderByKeyword_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "orderByKeyword_1")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeTokens(builder, 0, УПОРЯДОЧИТЬ, ПО);
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // [ascKeyword | descKeyword]
  static boolean orderDirectionOpt(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "orderDirectionOpt")) return false;
    orderDirectionOpt_0(builder, level + 1);
    return true;
  }

  // ascKeyword | descKeyword
  private static boolean orderDirectionOpt_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "orderDirectionOpt_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = ascKeyword(builder, level + 1);
    if (!result) result = descKeyword(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // expression orderDirectionOpt
  public static boolean orderItem(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "orderItem")) return false;
    boolean result;
    Marker marker = enter_section_(builder, level, _NONE_, ORDER_ITEM, "<order item>");
    result = expression(builder, level + 1);
    result = result && orderDirectionOpt(builder, level + 1);
    exit_section_(builder, level, marker, result, false, null);
    return result;
  }

  /* ********************************************************** */
  // full | left | right | полное | левое | правое
  static boolean outerJoinKind(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "outerJoinKind")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, FULL);
    if (!result) result = consumeToken(builder, LEFT);
    if (!result) result = consumeToken(builder, RIGHT);
    if (!result) result = consumeToken(builder, ПОЛНОЕ);
    if (!result) result = consumeToken(builder, ЛЕВОЕ);
    if (!result) result = consumeToken(builder, ПРАВОЕ);
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // (outer | внешнее)?
  static boolean outerKeywordOpt(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "outerKeywordOpt")) return false;
    outerKeywordOpt_0(builder, level + 1);
    return true;
  }

  // outer | внешнее
  private static boolean outerKeywordOpt_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "outerKeywordOpt_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, OUTER);
    if (!result) result = consumeToken(builder, ВНЕШНЕЕ);
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // identifier
  //     | numberLiteral
  //     | boolLiteral
  //     | stringLiteral
  //     | '(' expression ')'
  static boolean primary_group(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "primary_group")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = identifier(builder, level + 1);
    if (!result) result = numberLiteral(builder, level + 1);
    if (!result) result = boolLiteral(builder, level + 1);
    if (!result) result = stringLiteral(builder, level + 1);
    if (!result) result = primary_group_4(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // '(' expression ')'
  private static boolean primary_group_4(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "primary_group_4")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, LPAREN);
    result = result && expression(builder, level + 1);
    result = result && consumeToken(builder, RPAREN);
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // WILDCARD | selectionList
  static boolean selectList(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "selectList")) return false;
    boolean result;
    Marker marker = enter_section_(builder, level, _NONE_);
    result = consumeToken(builder, WILDCARD);
    if (!result) result = selectionList(builder, level + 1);
    exit_section_(builder, level, marker, result, false, notFromKeyword_parser_);
    return result;
  }

  /* ********************************************************** */
  // !<<eof>> select topOpt distinctOpt selectList
  // fromKeyword tableDeclaration
  // joinItem*
  // [whereKeyword expression]
  // [groupByKeyword expressionList [havingKeyword expression]]
  public static boolean select_statement(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "select_statement")) return false;
    if (!nextTokenIs(builder, SELECT)) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = select_statement_0(builder, level + 1);
    result = result && consumeToken(builder, SELECT);
    result = result && topOpt(builder, level + 1);
    result = result && distinctOpt(builder, level + 1);
    result = result && selectList(builder, level + 1);
    result = result && fromKeyword(builder, level + 1);
    result = result && tableDeclaration(builder, level + 1);
    result = result && select_statement_7(builder, level + 1);
    result = result && select_statement_8(builder, level + 1);
    result = result && select_statement_9(builder, level + 1);
    exit_section_(builder, marker, SELECT_STATEMENT, result);
    return result;
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
    result = whereKeyword(builder, level + 1);
    result = result && expression(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // [groupByKeyword expressionList [havingKeyword expression]]
  private static boolean select_statement_9(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "select_statement_9")) return false;
    select_statement_9_0(builder, level + 1);
    return true;
  }

  // groupByKeyword expressionList [havingKeyword expression]
  private static boolean select_statement_9_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "select_statement_9_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = groupByKeyword(builder, level + 1);
    result = result && expressionList(builder, level + 1);
    result = result && select_statement_9_0_2(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // [havingKeyword expression]
  private static boolean select_statement_9_0_2(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "select_statement_9_0_2")) return false;
    select_statement_9_0_2_0(builder, level + 1);
    return true;
  }

  // havingKeyword expression
  private static boolean select_statement_9_0_2_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "select_statement_9_0_2_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = havingKeyword(builder, level + 1);
    result = result && expression(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // expression alias?
  public static boolean selectionItem(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "selectionItem")) return false;
    boolean result;
    Marker marker = enter_section_(builder, level, _NONE_, SELECTION_ITEM, "<selection item>");
    result = expression(builder, level + 1);
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
  // unionList [orderByKeyword orderByExpressionList]
  public static boolean sqlQuery(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "sqlQuery")) return false;
    if (!nextTokenIs(builder, SELECT)) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = unionList(builder, level + 1);
    result = result && sqlQuery_1(builder, level + 1);
    exit_section_(builder, marker, SQL_QUERY, result);
    return result;
  }

  // [orderByKeyword orderByExpressionList]
  private static boolean sqlQuery_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "sqlQuery_1")) return false;
    sqlQuery_1_0(builder, level + 1);
    return true;
  }

  // orderByKeyword orderByExpressionList
  private static boolean sqlQuery_1_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "sqlQuery_1_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = orderByKeyword(builder, level + 1);
    result = result && orderByExpressionList(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // string
  public static boolean stringLiteral(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "stringLiteral")) return false;
    if (!nextTokenIs(builder, STRING)) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, STRING);
    exit_section_(builder, marker, STRING_LITERAL, result);
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
  // [top numberLiteral]
  public static boolean topOpt(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "topOpt")) return false;
    Marker marker = enter_section_(builder, level, _NONE_, TOP_OPT, "<top opt>");
    topOpt_0(builder, level + 1);
    exit_section_(builder, level, marker, true, false, null);
    return true;
  }

  // top numberLiteral
  private static boolean topOpt_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "topOpt_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, TOP);
    result = result && numberLiteral(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // union| объединить
  static boolean unionKeyword(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "unionKeyword")) return false;
    if (!nextTokenIs(builder, "", UNION, ОБЪЕДИНИТЬ)) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, UNION);
    if (!result) result = consumeToken(builder, ОБЪЕДИНИТЬ);
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // select_statement (unionKeyword allModifierOpt select_statement)*
  static boolean unionList(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "unionList")) return false;
    if (!nextTokenIs(builder, SELECT)) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = select_statement(builder, level + 1);
    result = result && unionList_1(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // (unionKeyword allModifierOpt select_statement)*
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

  // unionKeyword allModifierOpt select_statement
  private static boolean unionList_1_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "unionList_1_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = unionKeyword(builder, level + 1);
    result = result && allModifierOpt(builder, level + 1);
    result = result && select_statement(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // where | где
  static boolean whereKeyword(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "whereKeyword")) return false;
    if (!nextTokenIs(builder, "", WHERE, ГДЕ)) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, WHERE);
    if (!result) result = consumeToken(builder, ГДЕ);
    exit_section_(builder, marker, null, result);
    return result;
  }

  final static Parser notFromKeyword_parser_ = new Parser() {
    public boolean parse(PsiBuilder builder, int level) {
      return notFromKeyword(builder, level + 1);
    }
  };
}
