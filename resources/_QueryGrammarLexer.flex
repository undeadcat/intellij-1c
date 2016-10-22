package generated;

import com.intellij.lexer.*;
import com.intellij.psi.tree.IElementType;
import static generated.GeneratedTypes.*;

%%

%{
  public _QueryGrammarLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _QueryGrammarLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s

SPACE=[ \t\n\x0B\f\r]+
NUMBER=[0-9]+
IDSIMPLE=[a-zA-Zа-яА-Я][a-zA-Zа-яА-Я0123456789]*
STRING=('([^'\\]|\\.)*'|\"([^\"\\]|\\\"|\\'|\\)*\")
LINE_COMMENT="//".*

%%
<YYINITIAL> {
  {WHITE_SPACE}       { return com.intellij.psi.TokenType.WHITE_SPACE; }

  "*"                 { return WILDCARD; }
  "."                 { return DOT; }
  ","                 { return COMMA; }
  "("                 { return LPAREN; }
  ")"                 { return RPAREN; }
  "select"            { return SELECT; }
  "from"              { return FROM; }
  "из"                { return ИЗ; }
  "top"               { return TOP; }
  "distinct"          { return DISTINCT; }
  "as"                { return AS; }
  "where"             { return WHERE; }
  "где"               { return ГДЕ; }
  "group"             { return GROUP; }
  "by"                { return BY; }
  "сгруппировать"     { return СГРУППИРОВАТЬ; }
  "по"                { return ПО; }
  "having"            { return HAVING; }
  "имеющие"           { return ИМЕЮЩИЕ; }
  "order"             { return ORDER; }
  "упорядочить"       { return УПОРЯДОЧИТЬ; }
  "asc"               { return ASC; }
  "возр"              { return ВОЗР; }
  "desc"              { return DESC; }
  "убыв"              { return УБЫВ; }
  "union"             { return UNION; }
  "объединить"        { return ОБЪЕДИНИТЬ; }
  "all"               { return ALL; }
  "все"               { return ВСЕ; }
  "join"              { return JOIN; }
  "соединение"        { return СОЕДИНЕНИЕ; }
  "outer"             { return OUTER; }
  "внешнее"           { return ВНЕШНЕЕ; }
  "full"              { return FULL; }
  "left"              { return LEFT; }
  "right"             { return RIGHT; }
  "полное"            { return ПОЛНОЕ; }
  "левое"             { return ЛЕВОЕ; }
  "правое"            { return ПРАВОЕ; }
  "inner"             { return INNER; }
  "внутреннее"        { return ВНУТРЕННЕЕ; }
  "on"                { return ON; }
  "true"              { return TRUE; }
  "false"             { return FALSE; }
  "истина"            { return ИСТИНА; }
  "ложь"              { return ЛОЖЬ; }

  {SPACE}             { return com.intellij.psi.TokenType.WHITE_SPACE; }
  {NUMBER}            { return NUMBER; }
  {IDSIMPLE}          { return IDSIMPLE; }
  {STRING}            { return STRING; }
  {LINE_COMMENT}      { return LINE_COMMENT; }

}

[^] { return com.intellij.psi.TokenType.BAD_CHARACTER; }
