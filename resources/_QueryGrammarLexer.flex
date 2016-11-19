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

%caseless
%public
%class _QueryGrammarLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

OP_NEQ=<>|\!\=
OP_LIKE=like|подобно
OP_IS=is|есть
OP_IN=in|в
OP_NOT=not|не
OP_AND=and|и
OP_OR=or|или
AGGREGATIONFUNCTION=sum|сумма|count|количество|min|минимум|max|максимум|avg|среднее
SELECTKEYWORD=select|выбрать
WHEREKEYWORD=where|где
FROMKEYWORD=from|из
TOPKEYWORD=top|первые
DISTINCTKEYWORD=distinct|различные
ASKEYWORD=as|как
GROUPKEYWORD=group|сгруппировать
ORDERKEYWORD=order|упорядочить
ASCKEYWORD=asc|возр
DESCKEYWORD=desc|убыв
HAVINGKEYWORD=having|имеющие
UNIONKEYWORD=union|объединить
ALLKEYWORD=all|все
JOINKEYWORD=join|соединение
OUTERKEYWORD=outer|внешнее
FULLKEYWORD=full|полное
LEFTKEYWORD=left|левое
RIGHTKEYWORD=right|правое
INNERKEYWORD=inner|внутреннее
CASEKEYWORD=case|выбор
WHENKEYWORD=when|когда
THENKEYWORD=then|тогда
ELSEKEYWORD=else|иначе
ENDKEYWORD=end|конец
WHITE_SPACE=[\s]+
NUMBER=[0-9]+
BOOL=true|false|истина|ложь
ID_TOKEN=([a-zA-Zа-яА-Я][a-zA-Zа-яА-Я0123456789]*)(\.([a-zA-Zа-яА-Я][a-zA-Zа-яА-Я0123456789]*))*
STRING=(\"([^\"\\]|\\\"|\\)*\")
LINE_COMMENT="//".*

%%
<YYINITIAL> {
  {WHITE_SPACE}              { return com.intellij.psi.TokenType.WHITE_SPACE; }

  "*"                        { return ASTERISK; }
  "."                        { return DOT; }
  ","                        { return COMMA; }
  "("                        { return LPAREN; }
  ")"                        { return RPAREN; }
  ";"                        { return SEMICOLON; }
  "="                        { return OP_EQ; }
  "/"                        { return OP_DIV; }
  "+"                        { return OP_PLUS; }
  "-"                        { return OP_MINUS; }
  "<"                        { return OP_LT; }
  "<="                       { return OP_LTE; }
  ">"                        { return OP_GT; }
  ">="                       { return OP_GTE; }
  "by"                       { return BYKEYWORD; }
  "on"                       { return ONKEYWORD; }
  "по"                       { return ПОKEYWORD; }
  "null"                     { return NULL; }

  {OP_NEQ}                   { return OP_NEQ; }
  {OP_LIKE}                  { return OP_LIKE; }
  {OP_IS}                    { return OP_IS; }
  {OP_IN}                    { return OP_IN; }
  {OP_NOT}                   { return OP_NOT; }
  {OP_AND}                   { return OP_AND; }
  {OP_OR}                    { return OP_OR; }
  {AGGREGATIONFUNCTION} /\s*\(     { return AGGREGATIONFUNCTION; }
  {SELECTKEYWORD}            { return SELECTKEYWORD; }
  {WHEREKEYWORD}             { return WHEREKEYWORD; }
  {FROMKEYWORD}              { return FROMKEYWORD; }
  {TOPKEYWORD}               { return TOPKEYWORD; }
  {DISTINCTKEYWORD}          { return DISTINCTKEYWORD; }
  {ASKEYWORD}                { return ASKEYWORD; }
  {GROUPKEYWORD}             { return GROUPKEYWORD; }
  {ORDERKEYWORD}             { return ORDERKEYWORD; }
  {ASCKEYWORD}               { return ASCKEYWORD; }
  {DESCKEYWORD}              { return DESCKEYWORD; }
  {HAVINGKEYWORD}            { return HAVINGKEYWORD; }
  {UNIONKEYWORD}             { return UNIONKEYWORD; }
  {ALLKEYWORD}               { return ALLKEYWORD; }
  {JOINKEYWORD}              { return JOINKEYWORD; }
  {OUTERKEYWORD}             { return OUTERKEYWORD; }
  {FULLKEYWORD}              { return FULLKEYWORD; }
  {LEFTKEYWORD}              { return LEFTKEYWORD; }
  {RIGHTKEYWORD}             { return RIGHTKEYWORD; }
  {INNERKEYWORD}             { return INNERKEYWORD; }
  {CASEKEYWORD}              { return CASEKEYWORD; }
  {WHENKEYWORD}              { return WHENKEYWORD; }
  {THENKEYWORD}              { return THENKEYWORD; }
  {ELSEKEYWORD}              { return ELSEKEYWORD; }
  {ENDKEYWORD}               { return ENDKEYWORD; }
  {NUMBER}                   { return NUMBER; }
  {BOOL}                     { return BOOL; }
  {ID_TOKEN}                 { return ID_TOKEN; }
  {STRING}                   { return STRING; }
  {LINE_COMMENT}             { return LINE_COMMENT; }

}

[^] { return com.intellij.psi.TokenType.BAD_CHARACTER; }
