package edu.cmu.c0;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import edu.cmu.c0.psi.C0TokenTypesSets;
import edu.cmu.c0.psi.C0Types;

%%

%class C0Lexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

DIGIT_10=[0-9]
DIGIT_16=[0-9A-Fa-f]
NUMBER_10={DIGIT_10} {DIGIT_10}*
NUMBER_16="0" [Xx] {DIGIT_16} {DIGIT_16}*
IDENTIFIER=[A-Z_a-z] [0-9A-Z_a-z]*
COMMENT_1LINE="//" [^@] [^\n]*
COMMENT_BLOCK="/*" [^@] !([^]* "*/" [^]*) "*/"

%state IN_1LINE, IN_BLOCK

%%

<YYINITIAL> "//@" { yybegin(IN_1LINE); return C0Types.SPEC_START; }
<YYINITIAL> "/*@" { yybegin(IN_BLOCK); return C0Types.SPEC_START; }

<YYINITIAL> {COMMENT_1LINE} { return C0TokenTypesSets.COMMENT_1LINE; }
<YYINITIAL> {COMMENT_BLOCK} { return C0TokenTypesSets.COMMENT_BLOCK; }

<YYINITIAL, IN_BLOCK> [ \n\t\f]+ { return C0TokenTypesSets.WHITE_SPACE; }

<IN_BLOCK> "@*/" { yybegin(YYINITIAL); return C0Types.SPEC_END; }

<IN_1LINE> [ \t]+ { return C0TokenTypesSets.WHITE_SPACE; }
<IN_1LINE> [\n\f] { yybegin(YYINITIAL); return C0Types.SPEC_END; }

<YYINITIAL, IN_1LINE, IN_BLOCK> "NULL"           { return C0Types.NULL; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "acc"            { return C0Types.ACC; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "alloc"          { return C0Types.ALLOC; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "assert"         { return C0Types.ASSERT; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "bool"           { return C0Types.BOOL; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "else"           { return C0Types.ELSE; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "ensures"        { return C0Types.ENSURES; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "false"          { return C0Types.FALSE; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "fold"           { return C0Types.FOLD; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "for"            { return C0Types.FOR; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "if"             { return C0Types.IF; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "in"             { return C0Types.IN; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "int"            { return C0Types.INT; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "loop_invariant" { return C0Types.LI; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "predicate"      { return C0Types.PREDICATE; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "requires"       { return C0Types.REQUIRES; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "result"         { return C0Types.RESULT; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "return"         { return C0Types.RETURN; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "struct"         { return C0Types.STRUCT; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "true"           { return C0Types.TRUE; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "typedef"        { return C0Types.TYPEDEF; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "unfold"         { return C0Types.UNFOLD; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "unfolding"      { return C0Types.UNFOLDING; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "use"            { return C0Types.USE; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "void"           { return C0Types.VOID; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "while"          { return C0Types.WHILE; }

<YYINITIAL, IN_1LINE, IN_BLOCK> {NUMBER_10}  { return C0Types.NUMBER_10; }
<YYINITIAL, IN_1LINE, IN_BLOCK> {NUMBER_16}  { return C0Types.NUMBER_16; }
<YYINITIAL, IN_1LINE, IN_BLOCK> {IDENTIFIER} { return C0Types.IDENTIFIER; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "#"          { return C0Types.HASH; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "\""         { return C0Types.DOUBLEQUOTE; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "{"          { return C0Types.LBRACE; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "}"          { return C0Types.RBRACE; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "("          { return C0Types.LPAREN; }
<YYINITIAL, IN_1LINE, IN_BLOCK> ")"          { return C0Types.RPAREN; }
<YYINITIAL, IN_1LINE, IN_BLOCK> ","          { return C0Types.COMMA; }
<YYINITIAL, IN_1LINE, IN_BLOCK> ";"          { return C0Types.SEMICOLON; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "="          { return C0Types.ASSIGN; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "+="         { return C0Types.ASSIGN_ADD; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "-="         { return C0Types.ASSIGN_SUB; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "*="         { return C0Types.ASSIGN_MUL; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "/="         { return C0Types.ASSIGN_DIV; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "%="         { return C0Types.ASSIGN_MOD; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "<<="        { return C0Types.ASSIGN_SHL; }
<YYINITIAL, IN_1LINE, IN_BLOCK> ">>="        { return C0Types.ASSIGN_SHR; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "&="         { return C0Types.ASSIGN_AND; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "^="         { return C0Types.ASSIGN_XOR; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "|="         { return C0Types.ASSIGN_OR; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "?"          { return C0Types.QUESTION; }
<YYINITIAL, IN_1LINE, IN_BLOCK> ":"          { return C0Types.COLON; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "||"         { return C0Types.LOGICAL_OR; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "&&"         { return C0Types.LOGICAL_AND; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "|"          { return C0Types.BITWISE_OR; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "^"          { return C0Types.BITWISE_XOR; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "&"          { return C0Types.BITWISE_AND; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "=="         { return C0Types.EQ; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "!="         { return C0Types.NE; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "<="         { return C0Types.LE; }
<YYINITIAL, IN_1LINE, IN_BLOCK> ">="         { return C0Types.GE; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "<"          { return C0Types.LT; }
<YYINITIAL, IN_1LINE, IN_BLOCK> ">"          { return C0Types.GT; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "<<"         { return C0Types.SHL; }
<YYINITIAL, IN_1LINE, IN_BLOCK> ">>"         { return C0Types.SHR; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "+"          { return C0Types.PLUS; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "-"          { return C0Types.MINUS; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "*"          { return C0Types.ASTERISK; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "/"          { return C0Types.SLASH; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "%"          { return C0Types.PERCENT; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "!"          { return C0Types.LOGICAL_NOT; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "~"          { return C0Types.BITWISE_NOT; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "."          { return C0Types.DOT; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "->"         { return C0Types.ARROW; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "++"         { return C0Types.INC; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "--"         { return C0Types.DEC; }
<YYINITIAL, IN_1LINE, IN_BLOCK> "\\"         { return C0Types.BACKSLASH; }

<YYINITIAL, IN_1LINE, IN_BLOCK> [^] { return C0TokenTypesSets.BAD_CHARACTER; }
