// This is a generated file. Not intended for manual editing.
package edu.cmu.c0.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static edu.cmu.c0.psi.C0Types.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class C0Parser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, EXTENDS_SETS_);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    boolean r;
    if (t == EXPR) {
      r = expr(b, l + 1, -1);
    }
    else {
      r = c0File(b, l + 1);
    }
    return r;
  }

  public static final TokenSet[] EXTENDS_SETS_ = new TokenSet[] {
    create_token_set_(EXPR, EXPRS, EXPR_0, EXPR_1,
      EXPR_2, EXPR_3, EXPR_4, EXPR_5,
      EXPR_ACC, EXPR_ADD, EXPR_ALLOC, EXPR_ARROW,
      EXPR_BITWISE_NOT, EXPR_BOOL, EXPR_DEC, EXPR_DIV,
      EXPR_DOT, EXPR_EQ, EXPR_GE, EXPR_GT,
      EXPR_ID, EXPR_IMPRECISION, EXPR_INC, EXPR_INVOKE,
      EXPR_LE, EXPR_LOGICAL_NOT, EXPR_LT, EXPR_MOD,
      EXPR_MUL, EXPR_NE, EXPR_NEG, EXPR_NULL,
      EXPR_NUMBER_10, EXPR_NUMBER_16, EXPR_PAREN, EXPR_REF,
      EXPR_RESULT, EXPR_SHL, EXPR_SHR, EXPR_SUB,
      EXPR_UNFOLDING),
  };

  /* ********************************************************** */
  // '='
  //   | '+=' | '-=' | '*=' | '/=' | '%=' | '<<=' | '>>=' | '&=' | '^=' | '|='
  public static boolean assignOp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "assignOp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ASSIGN_OP, "<assign op>");
    r = consumeToken(b, ASSIGN);
    if (!r) r = consumeToken(b, ASSIGN_ADD);
    if (!r) r = consumeToken(b, ASSIGN_SUB);
    if (!r) r = consumeToken(b, ASSIGN_MUL);
    if (!r) r = consumeToken(b, ASSIGN_DIV);
    if (!r) r = consumeToken(b, ASSIGN_MOD);
    if (!r) r = consumeToken(b, ASSIGN_SHL);
    if (!r) r = consumeToken(b, ASSIGN_SHR);
    if (!r) r = consumeToken(b, ASSIGN_AND);
    if (!r) r = consumeToken(b, ASSIGN_XOR);
    if (!r) r = consumeToken(b, ASSIGN_OR);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // def*
  static boolean c0File(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "c0File")) return false;
    while (true) {
      int c = current_position_(b);
      if (!def(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "c0File", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // defInclude
  //   | defPredicates
  //   | defMethod
  //   | defStruct
  //   | defType
  static boolean def(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "def")) return false;
    boolean r;
    r = defInclude(b, l + 1);
    if (!r) r = defPredicates(b, l + 1);
    if (!r) r = defMethod(b, l + 1);
    if (!r) r = defStruct(b, l + 1);
    if (!r) r = defType(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // '#' 'use' filename
  public static boolean defInclude(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "defInclude")) return false;
    if (!nextTokenIs(b, HASH)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, HASH, USE);
    r = r && filename(b, l + 1);
    exit_section_(b, m, DEF_INCLUDE, r);
    return r;
  }

  /* ********************************************************** */
  // typeReference IDENTIFIER '(' parameters? ')' specs* methodBody
  public static boolean defMethod(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "defMethod")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, DEF_METHOD, "<def method>");
    r = typeReference(b, l + 1);
    r = r && consumeTokens(b, 0, IDENTIFIER, LPAREN);
    r = r && defMethod_3(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    r = r && defMethod_5(b, l + 1);
    r = r && methodBody(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // parameters?
  private static boolean defMethod_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "defMethod_3")) return false;
    parameters(b, l + 1);
    return true;
  }

  // specs*
  private static boolean defMethod_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "defMethod_5")) return false;
    while (true) {
      int c = current_position_(b);
      if (!specs(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "defMethod_5", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // 'predicate' IDENTIFIER '(' parameters ')' '=' expr ';'
  public static boolean defPredicate(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "defPredicate")) return false;
    if (!nextTokenIs(b, PREDICATE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, PREDICATE, IDENTIFIER, LPAREN);
    r = r && parameters(b, l + 1);
    r = r && consumeTokens(b, 0, RPAREN, ASSIGN);
    r = r && expr(b, l + 1, -1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, m, DEF_PREDICATE, r);
    return r;
  }

  /* ********************************************************** */
  // SPEC_START defPredicate* SPEC_END
  public static boolean defPredicates(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "defPredicates")) return false;
    if (!nextTokenIs(b, SPEC_START)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SPEC_START);
    r = r && defPredicates_1(b, l + 1);
    r = r && consumeToken(b, SPEC_END);
    exit_section_(b, m, DEF_PREDICATES, r);
    return r;
  }

  // defPredicate*
  private static boolean defPredicates_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "defPredicates_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!defPredicate(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "defPredicates_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // 'struct' IDENTIFIER structFields? ';'
  public static boolean defStruct(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "defStruct")) return false;
    if (!nextTokenIs(b, STRUCT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, STRUCT, IDENTIFIER);
    r = r && defStruct_2(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, m, DEF_STRUCT, r);
    return r;
  }

  // structFields?
  private static boolean defStruct_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "defStruct_2")) return false;
    structFields(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // 'typedef' typeReference IDENTIFIER ';'
  public static boolean defType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "defType")) return false;
    if (!nextTokenIs(b, TYPEDEF)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TYPEDEF);
    r = r && typeReference(b, l + 1);
    r = r && consumeTokens(b, 0, IDENTIFIER, SEMICOLON);
    exit_section_(b, m, DEF_TYPE, r);
    return r;
  }

  /* ********************************************************** */
  // expr ( ',' expr )*
  public static boolean exprs(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exprs")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, EXPRS, "<exprs>");
    r = expr(b, l + 1, -1);
    r = r && exprs_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ( ',' expr )*
  private static boolean exprs_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exprs_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!exprs_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "exprs_1", c)) break;
    }
    return true;
  }

  // ',' expr
  private static boolean exprs_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exprs_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && expr(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // '<' IDENTIFIER '>'
  //   | '"' IDENTIFIER '"'
  public static boolean filename(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "filename")) return false;
    if (!nextTokenIs(b, "<filename>", DOUBLEQUOTE, LT)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FILENAME, "<filename>");
    r = parseTokens(b, 0, LT, IDENTIFIER, GT);
    if (!r) r = parseTokens(b, 0, DOUBLEQUOTE, IDENTIFIER, DOUBLEQUOTE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ghostAssert
  //   | ghostFold
  //   | ghostUnfold
  static boolean ghost(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ghost")) return false;
    boolean r;
    r = ghostAssert(b, l + 1);
    if (!r) r = ghostFold(b, l + 1);
    if (!r) r = ghostUnfold(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // 'assert' expr ';'
  public static boolean ghostAssert(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ghostAssert")) return false;
    if (!nextTokenIs(b, ASSERT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ASSERT);
    r = r && expr(b, l + 1, -1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, m, GHOST_ASSERT, r);
    return r;
  }

  /* ********************************************************** */
  // 'fold' IDENTIFIER '(' exprs ')' ';'
  public static boolean ghostFold(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ghostFold")) return false;
    if (!nextTokenIs(b, FOLD)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, FOLD, IDENTIFIER, LPAREN);
    r = r && exprs(b, l + 1);
    r = r && consumeTokens(b, 0, RPAREN, SEMICOLON);
    exit_section_(b, m, GHOST_FOLD, r);
    return r;
  }

  /* ********************************************************** */
  // 'unfold' IDENTIFIER '(' exprs ')' ';'
  public static boolean ghostUnfold(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ghostUnfold")) return false;
    if (!nextTokenIs(b, UNFOLD)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, UNFOLD, IDENTIFIER, LPAREN);
    r = r && exprs(b, l + 1);
    r = r && consumeTokens(b, 0, RPAREN, SEMICOLON);
    exit_section_(b, m, GHOST_UNFOLD, r);
    return r;
  }

  /* ********************************************************** */
  // SPEC_START ghost* SPEC_END
  public static boolean ghosts(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ghosts")) return false;
    if (!nextTokenIs(b, SPEC_START)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SPEC_START);
    r = r && ghosts_1(b, l + 1);
    r = r && consumeToken(b, SPEC_END);
    exit_section_(b, m, GHOSTS, r);
    return r;
  }

  // ghost*
  private static boolean ghosts_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ghosts_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!ghost(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ghosts_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // 'loop_invariant' expr ';'
  public static boolean invariant(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "invariant")) return false;
    if (!nextTokenIs(b, LI)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LI);
    r = r && expr(b, l + 1, -1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, m, INVARIANT, r);
    return r;
  }

  /* ********************************************************** */
  // SPEC_START invariant* SPEC_END
  public static boolean invariants(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "invariants")) return false;
    if (!nextTokenIs(b, SPEC_START)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SPEC_START);
    r = r && invariants_1(b, l + 1);
    r = r && consumeToken(b, SPEC_END);
    exit_section_(b, m, INVARIANTS, r);
    return r;
  }

  // invariant*
  private static boolean invariants_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "invariants_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!invariant(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "invariants_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // ';'
  //   | '{' stmt* '}'
  public static boolean methodBody(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "methodBody")) return false;
    if (!nextTokenIs(b, "<method body>", LBRACE, SEMICOLON)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, METHOD_BODY, "<method body>");
    r = consumeToken(b, SEMICOLON);
    if (!r) r = methodBody_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // '{' stmt* '}'
  private static boolean methodBody_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "methodBody_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LBRACE);
    r = r && methodBody_1_1(b, l + 1);
    r = r && consumeToken(b, RBRACE);
    exit_section_(b, m, null, r);
    return r;
  }

  // stmt*
  private static boolean methodBody_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "methodBody_1_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!stmt(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "methodBody_1_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // typeReference IDENTIFIER
  public static boolean parameter(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameter")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PARAMETER, "<parameter>");
    r = typeReference(b, l + 1);
    r = r && consumeToken(b, IDENTIFIER);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // parameter ( ',' parameter )*
  public static boolean parameters(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameters")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PARAMETERS, "<parameters>");
    r = parameter(b, l + 1);
    r = r && parameters_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ( ',' parameter )*
  private static boolean parameters_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameters_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!parameters_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "parameters_1", c)) break;
    }
    return true;
  }

  // ',' parameter
  private static boolean parameters_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameters_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && parameter(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // specRequires | specEnsures
  static boolean spec(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "spec")) return false;
    if (!nextTokenIs(b, "", ENSURES, REQUIRES)) return false;
    boolean r;
    r = specRequires(b, l + 1);
    if (!r) r = specEnsures(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // 'ensures' expr ';'
  public static boolean specEnsures(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "specEnsures")) return false;
    if (!nextTokenIs(b, ENSURES)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ENSURES);
    r = r && expr(b, l + 1, -1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, m, SPEC_ENSURES, r);
    return r;
  }

  /* ********************************************************** */
  // 'requires' expr ';'
  public static boolean specRequires(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "specRequires")) return false;
    if (!nextTokenIs(b, REQUIRES)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, REQUIRES);
    r = r && expr(b, l + 1, -1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, m, SPEC_REQUIRES, r);
    return r;
  }

  /* ********************************************************** */
  // SPEC_START spec* SPEC_END
  public static boolean specs(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "specs")) return false;
    if (!nextTokenIs(b, SPEC_START)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SPEC_START);
    r = r && specs_1(b, l + 1);
    r = r && consumeToken(b, SPEC_END);
    exit_section_(b, m, SPECS, r);
    return r;
  }

  // spec*
  private static boolean specs_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "specs_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!spec(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "specs_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // ghosts
  //   | stmtBlock
  //   | stmtIf
  //   | stmtWhile
  //   | stmtFor
  //   | stmtReturn
  //   | stmtAssert
  //   | stmtSimple ';'
  static boolean stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "stmt")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ghosts(b, l + 1);
    if (!r) r = stmtBlock(b, l + 1);
    if (!r) r = stmtIf(b, l + 1);
    if (!r) r = stmtWhile(b, l + 1);
    if (!r) r = stmtFor(b, l + 1);
    if (!r) r = stmtReturn(b, l + 1);
    if (!r) r = stmtAssert(b, l + 1);
    if (!r) r = stmt_7(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // stmtSimple ';'
  private static boolean stmt_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "stmt_7")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = stmtSimple(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'assert' '(' expr ')' ';'
  public static boolean stmtAssert(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "stmtAssert")) return false;
    if (!nextTokenIs(b, ASSERT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, ASSERT, LPAREN);
    r = r && expr(b, l + 1, -1);
    r = r && consumeTokens(b, 0, RPAREN, SEMICOLON);
    exit_section_(b, m, STMT_ASSERT, r);
    return r;
  }

  /* ********************************************************** */
  // '{' stmt* '}'
  public static boolean stmtBlock(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "stmtBlock")) return false;
    if (!nextTokenIs(b, LBRACE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LBRACE);
    r = r && stmtBlock_1(b, l + 1);
    r = r && consumeToken(b, RBRACE);
    exit_section_(b, m, STMT_BLOCK, r);
    return r;
  }

  // stmt*
  private static boolean stmtBlock_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "stmtBlock_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!stmt(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "stmtBlock_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // expr ( assignOp expr )?
  public static boolean stmtExpr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "stmtExpr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, STMT_EXPR, "<stmt expr>");
    r = expr(b, l + 1, -1);
    r = r && stmtExpr_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ( assignOp expr )?
  private static boolean stmtExpr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "stmtExpr_1")) return false;
    stmtExpr_1_0(b, l + 1);
    return true;
  }

  // assignOp expr
  private static boolean stmtExpr_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "stmtExpr_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = assignOp(b, l + 1);
    r = r && expr(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'for' '(' stmtSimple ';' expr ';' stmtExpr ')' invariants* stmt
  public static boolean stmtFor(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "stmtFor")) return false;
    if (!nextTokenIs(b, FOR)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, FOR, LPAREN);
    r = r && stmtSimple(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    r = r && expr(b, l + 1, -1);
    r = r && consumeToken(b, SEMICOLON);
    r = r && stmtExpr(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    r = r && stmtFor_8(b, l + 1);
    r = r && stmt(b, l + 1);
    exit_section_(b, m, STMT_FOR, r);
    return r;
  }

  // invariants*
  private static boolean stmtFor_8(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "stmtFor_8")) return false;
    while (true) {
      int c = current_position_(b);
      if (!invariants(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "stmtFor_8", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // 'if' '(' expr ')' stmt ( 'else' stmt )?
  public static boolean stmtIf(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "stmtIf")) return false;
    if (!nextTokenIs(b, IF)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, IF, LPAREN);
    r = r && expr(b, l + 1, -1);
    r = r && consumeToken(b, RPAREN);
    r = r && stmt(b, l + 1);
    r = r && stmtIf_5(b, l + 1);
    exit_section_(b, m, STMT_IF, r);
    return r;
  }

  // ( 'else' stmt )?
  private static boolean stmtIf_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "stmtIf_5")) return false;
    stmtIf_5_0(b, l + 1);
    return true;
  }

  // 'else' stmt
  private static boolean stmtIf_5_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "stmtIf_5_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ELSE);
    r = r && stmt(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'return' expr? ';'
  public static boolean stmtReturn(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "stmtReturn")) return false;
    if (!nextTokenIs(b, RETURN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, RETURN);
    r = r && stmtReturn_1(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, m, STMT_RETURN, r);
    return r;
  }

  // expr?
  private static boolean stmtReturn_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "stmtReturn_1")) return false;
    expr(b, l + 1, -1);
    return true;
  }

  /* ********************************************************** */
  // stmtVar | stmtExpr
  static boolean stmtSimple(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "stmtSimple")) return false;
    boolean r;
    r = stmtVar(b, l + 1);
    if (!r) r = stmtExpr(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // typeReference IDENTIFIER ( '=' expr )?
  public static boolean stmtVar(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "stmtVar")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, STMT_VAR, "<stmt var>");
    r = typeReference(b, l + 1);
    r = r && consumeToken(b, IDENTIFIER);
    r = r && stmtVar_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ( '=' expr )?
  private static boolean stmtVar_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "stmtVar_2")) return false;
    stmtVar_2_0(b, l + 1);
    return true;
  }

  // '=' expr
  private static boolean stmtVar_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "stmtVar_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ASSIGN);
    r = r && expr(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'while' '(' expr ')' invariants* stmt
  public static boolean stmtWhile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "stmtWhile")) return false;
    if (!nextTokenIs(b, WHILE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, WHILE, LPAREN);
    r = r && expr(b, l + 1, -1);
    r = r && consumeToken(b, RPAREN);
    r = r && stmtWhile_4(b, l + 1);
    r = r && stmt(b, l + 1);
    exit_section_(b, m, STMT_WHILE, r);
    return r;
  }

  // invariants*
  private static boolean stmtWhile_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "stmtWhile_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!invariants(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "stmtWhile_4", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // typeReference IDENTIFIER ';'
  public static boolean structField(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "structField")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, STRUCT_FIELD, "<struct field>");
    r = typeReference(b, l + 1);
    r = r && consumeTokens(b, 0, IDENTIFIER, SEMICOLON);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '{' structField* '}'
  public static boolean structFields(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "structFields")) return false;
    if (!nextTokenIs(b, LBRACE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LBRACE);
    r = r && structFields_1(b, l + 1);
    r = r && consumeToken(b, RBRACE);
    exit_section_(b, m, STRUCT_FIELDS, r);
    return r;
  }

  // structField*
  private static boolean structFields_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "structFields_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!structField(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "structFields_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // 'struct' IDENTIFIER
  //   | 'bool'
  //   | 'int'
  //   | 'void'
  //   | IDENTIFIER
  public static boolean structReference(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "structReference")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, STRUCT_REFERENCE, "<struct reference>");
    r = parseTokens(b, 0, STRUCT, IDENTIFIER);
    if (!r) r = consumeToken(b, BOOL);
    if (!r) r = consumeToken(b, INT);
    if (!r) r = consumeToken(b, VOID);
    if (!r) r = consumeToken(b, IDENTIFIER);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '*'
  public static boolean typeModifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "typeModifier")) return false;
    if (!nextTokenIs(b, ASTERISK)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ASTERISK);
    exit_section_(b, m, TYPE_MODIFIER, r);
    return r;
  }

  /* ********************************************************** */
  // structReference typeModifier*
  public static boolean typeReference(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "typeReference")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TYPE_REFERENCE, "<type reference>");
    r = structReference(b, l + 1);
    r = r && typeReference_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // typeModifier*
  private static boolean typeReference_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "typeReference_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!typeModifier(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "typeReference_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // Expression root: expr
  // Operator priority table:
  // 0: BINARY(expr0)
  // 1: BINARY(expr1)
  // 2: BINARY(expr2)
  // 3: BINARY(expr3)
  // 4: BINARY(expr4)
  // 5: BINARY(expr5)
  // 6: BINARY(exprEq) BINARY(exprNe)
  // 7: BINARY(exprLe) BINARY(exprGe) BINARY(exprLt) BINARY(exprGt)
  // 8: BINARY(exprShl) BINARY(exprShr)
  // 9: BINARY(exprAdd) BINARY(exprSub)
  // 10: BINARY(exprMul) BINARY(exprDiv) BINARY(exprMod)
  // 11: PREFIX(exprRef) PREFIX(exprNeg) PREFIX(exprLogicalNot) PREFIX(exprBitwiseNot)
  // 12: POSTFIX(exprDot) POSTFIX(exprArrow) POSTFIX(exprInc) POSTFIX(exprDec)
  // 13: PREFIX(exprParen) ATOM(exprNull) ATOM(exprBool) ATOM(exprNumber10)
  //    ATOM(exprNumber16) ATOM(exprResult) ATOM(exprImprecision) PREFIX(exprAcc)
  //    ATOM(exprAlloc) ATOM(exprUnfolding) ATOM(exprInvoke) ATOM(exprId)
  public static boolean expr(PsiBuilder b, int l, int g) {
    if (!recursion_guard_(b, l, "expr")) return false;
    addVariant(b, "<expr>");
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, "<expr>");
    r = exprImprecision(b, l + 1);
    if (!r) r = exprNeg(b, l + 1);
    if (!r) r = exprRef(b, l + 1);
    if (!r) r = exprLogicalNot(b, l + 1);
    if (!r) r = exprBitwiseNot(b, l + 1);
    if (!r) r = exprParen(b, l + 1);
    if (!r) r = exprNull(b, l + 1);
    if (!r) r = exprBool(b, l + 1);
    if (!r) r = exprNumber10(b, l + 1);
    if (!r) r = exprNumber16(b, l + 1);
    if (!r) r = exprResult(b, l + 1);
    if (!r) r = exprAcc(b, l + 1);
    if (!r) r = exprAlloc(b, l + 1);
    if (!r) r = exprUnfolding(b, l + 1);
    if (!r) r = exprInvoke(b, l + 1);
    if (!r) r = exprId(b, l + 1);
    p = r;
    r = r && expr_0(b, l + 1, g);
    exit_section_(b, l, m, null, r, p, null);
    return r || p;
  }

  public static boolean expr_0(PsiBuilder b, int l, int g) {
    if (!recursion_guard_(b, l, "expr_0")) return false;
    boolean r = true;
    while (true) {
      Marker m = enter_section_(b, l, _LEFT_, null);
      if (g < 0 && consumeTokenSmart(b, QUESTION)) {
        r = report_error_(b, expr(b, l, -1));
        r = expr0_1(b, l + 1) && r;
        exit_section_(b, l, m, EXPR_0, r, true, null);
      }
      else if (g < 1 && consumeTokenSmart(b, LOGICAL_OR)) {
        r = expr(b, l, 1);
        exit_section_(b, l, m, EXPR_1, r, true, null);
      }
      else if (g < 2 && consumeTokenSmart(b, LOGICAL_AND)) {
        r = expr(b, l, 2);
        exit_section_(b, l, m, EXPR_2, r, true, null);
      }
      else if (g < 3 && consumeTokenSmart(b, BITWISE_OR)) {
        r = expr(b, l, 3);
        exit_section_(b, l, m, EXPR_3, r, true, null);
      }
      else if (g < 4 && consumeTokenSmart(b, BITWISE_XOR)) {
        r = expr(b, l, 4);
        exit_section_(b, l, m, EXPR_4, r, true, null);
      }
      else if (g < 5 && consumeTokenSmart(b, BITWISE_AND)) {
        r = expr(b, l, 5);
        exit_section_(b, l, m, EXPR_5, r, true, null);
      }
      else if (g < 6 && consumeTokenSmart(b, EQ)) {
        r = expr(b, l, 6);
        exit_section_(b, l, m, EXPR_EQ, r, true, null);
      }
      else if (g < 6 && consumeTokenSmart(b, NE)) {
        r = expr(b, l, 6);
        exit_section_(b, l, m, EXPR_NE, r, true, null);
      }
      else if (g < 7 && consumeTokenSmart(b, LE)) {
        r = expr(b, l, 7);
        exit_section_(b, l, m, EXPR_LE, r, true, null);
      }
      else if (g < 7 && consumeTokenSmart(b, GE)) {
        r = expr(b, l, 7);
        exit_section_(b, l, m, EXPR_GE, r, true, null);
      }
      else if (g < 7 && consumeTokenSmart(b, LT)) {
        r = expr(b, l, 7);
        exit_section_(b, l, m, EXPR_LT, r, true, null);
      }
      else if (g < 7 && consumeTokenSmart(b, GT)) {
        r = expr(b, l, 7);
        exit_section_(b, l, m, EXPR_GT, r, true, null);
      }
      else if (g < 8 && consumeTokenSmart(b, SHL)) {
        r = expr(b, l, 8);
        exit_section_(b, l, m, EXPR_SHL, r, true, null);
      }
      else if (g < 8 && consumeTokenSmart(b, SHR)) {
        r = expr(b, l, 8);
        exit_section_(b, l, m, EXPR_SHR, r, true, null);
      }
      else if (g < 9 && consumeTokenSmart(b, PLUS)) {
        r = expr(b, l, 9);
        exit_section_(b, l, m, EXPR_ADD, r, true, null);
      }
      else if (g < 9 && consumeTokenSmart(b, MINUS)) {
        r = expr(b, l, 9);
        exit_section_(b, l, m, EXPR_SUB, r, true, null);
      }
      else if (g < 10 && consumeTokenSmart(b, ASTERISK)) {
        r = expr(b, l, 10);
        exit_section_(b, l, m, EXPR_MUL, r, true, null);
      }
      else if (g < 10 && consumeTokenSmart(b, SLASH)) {
        r = expr(b, l, 10);
        exit_section_(b, l, m, EXPR_DIV, r, true, null);
      }
      else if (g < 10 && consumeTokenSmart(b, PERCENT)) {
        r = expr(b, l, 10);
        exit_section_(b, l, m, EXPR_MOD, r, true, null);
      }
      else if (g < 12 && parseTokensSmart(b, 0, DOT, IDENTIFIER)) {
        r = true;
        exit_section_(b, l, m, EXPR_DOT, r, true, null);
      }
      else if (g < 12 && parseTokensSmart(b, 0, ARROW, IDENTIFIER)) {
        r = true;
        exit_section_(b, l, m, EXPR_ARROW, r, true, null);
      }
      else if (g < 12 && consumeTokenSmart(b, INC)) {
        r = true;
        exit_section_(b, l, m, EXPR_INC, r, true, null);
      }
      else if (g < 12 && consumeTokenSmart(b, DEC)) {
        r = true;
        exit_section_(b, l, m, EXPR_DEC, r, true, null);
      }
      else {
        exit_section_(b, l, m, null, false, false, null);
        break;
      }
    }
    return r;
  }

  // ':' expr
  private static boolean expr0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expr0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COLON);
    r = r && expr(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '?'
  public static boolean exprImprecision(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exprImprecision")) return false;
    if (!nextTokenIsSmart(b, QUESTION)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, QUESTION);
    exit_section_(b, m, EXPR_IMPRECISION, r);
    return r;
  }

  public static boolean exprNeg(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exprNeg")) return false;
    if (!nextTokenIsSmart(b, MINUS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeTokenSmart(b, MINUS);
    p = r;
    r = p && expr(b, l, 11);
    exit_section_(b, l, m, EXPR_NEG, r, p, null);
    return r || p;
  }

  public static boolean exprRef(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exprRef")) return false;
    if (!nextTokenIsSmart(b, ASTERISK)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeTokenSmart(b, ASTERISK);
    p = r;
    r = p && expr(b, l, 11);
    exit_section_(b, l, m, EXPR_REF, r, p, null);
    return r || p;
  }

  public static boolean exprLogicalNot(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exprLogicalNot")) return false;
    if (!nextTokenIsSmart(b, LOGICAL_NOT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeTokenSmart(b, LOGICAL_NOT);
    p = r;
    r = p && expr(b, l, 11);
    exit_section_(b, l, m, EXPR_LOGICAL_NOT, r, p, null);
    return r || p;
  }

  public static boolean exprBitwiseNot(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exprBitwiseNot")) return false;
    if (!nextTokenIsSmart(b, BITWISE_NOT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeTokenSmart(b, BITWISE_NOT);
    p = r;
    r = p && expr(b, l, 11);
    exit_section_(b, l, m, EXPR_BITWISE_NOT, r, p, null);
    return r || p;
  }

  public static boolean exprParen(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exprParen")) return false;
    if (!nextTokenIsSmart(b, LPAREN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeTokenSmart(b, LPAREN);
    p = r;
    r = p && expr(b, l, -1);
    r = p && report_error_(b, consumeToken(b, RPAREN)) && r;
    exit_section_(b, l, m, EXPR_PAREN, r, p, null);
    return r || p;
  }

  // 'NULL'
  public static boolean exprNull(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exprNull")) return false;
    if (!nextTokenIsSmart(b, NULL)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, NULL);
    exit_section_(b, m, EXPR_NULL, r);
    return r;
  }

  // 'true' | 'false'
  public static boolean exprBool(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exprBool")) return false;
    if (!nextTokenIsSmart(b, FALSE, TRUE)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, EXPR_BOOL, "<expr bool>");
    r = consumeTokenSmart(b, TRUE);
    if (!r) r = consumeTokenSmart(b, FALSE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // NUMBER_10
  public static boolean exprNumber10(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exprNumber10")) return false;
    if (!nextTokenIsSmart(b, NUMBER_10)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, NUMBER_10);
    exit_section_(b, m, EXPR_NUMBER_10, r);
    return r;
  }

  // NUMBER_16
  public static boolean exprNumber16(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exprNumber16")) return false;
    if (!nextTokenIsSmart(b, NUMBER_16)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, NUMBER_16);
    exit_section_(b, m, EXPR_NUMBER_16, r);
    return r;
  }

  // '\\' 'result'
  public static boolean exprResult(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exprResult")) return false;
    if (!nextTokenIsSmart(b, BACKSLASH)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokensSmart(b, 0, BACKSLASH, RESULT);
    exit_section_(b, m, EXPR_RESULT, r);
    return r;
  }

  public static boolean exprAcc(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exprAcc")) return false;
    if (!nextTokenIsSmart(b, ACC)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = parseTokensSmart(b, 0, ACC, LPAREN);
    p = r;
    r = p && expr(b, l, -1);
    r = p && report_error_(b, consumeToken(b, RPAREN)) && r;
    exit_section_(b, l, m, EXPR_ACC, r, p, null);
    return r || p;
  }

  // 'alloc' '(' typeReference ')'
  public static boolean exprAlloc(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exprAlloc")) return false;
    if (!nextTokenIsSmart(b, ALLOC)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokensSmart(b, 0, ALLOC, LPAREN);
    r = r && typeReference(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, EXPR_ALLOC, r);
    return r;
  }

  // 'unfolding' IDENTIFIER '(' exprs ')' 'in' '(' expr ')'
  public static boolean exprUnfolding(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exprUnfolding")) return false;
    if (!nextTokenIsSmart(b, UNFOLDING)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokensSmart(b, 0, UNFOLDING, IDENTIFIER, LPAREN);
    r = r && exprs(b, l + 1);
    r = r && consumeTokensSmart(b, 0, RPAREN, IN, LPAREN);
    r = r && expr(b, l + 1, -1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, EXPR_UNFOLDING, r);
    return r;
  }

  // IDENTIFIER '(' exprs* ')'
  public static boolean exprInvoke(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exprInvoke")) return false;
    if (!nextTokenIsSmart(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokensSmart(b, 0, IDENTIFIER, LPAREN);
    r = r && exprInvoke_2(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, EXPR_INVOKE, r);
    return r;
  }

  // exprs*
  private static boolean exprInvoke_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exprInvoke_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!exprs(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "exprInvoke_2", c)) break;
    }
    return true;
  }

  // IDENTIFIER
  public static boolean exprId(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exprId")) return false;
    if (!nextTokenIsSmart(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, IDENTIFIER);
    exit_section_(b, m, EXPR_ID, r);
    return r;
  }

}
