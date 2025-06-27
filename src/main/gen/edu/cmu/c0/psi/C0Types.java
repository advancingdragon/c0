// This is a generated file. Not intended for manual editing.
package edu.cmu.c0.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import edu.cmu.c0.psi.impl.*;

public interface C0Types {

  IElementType ASSIGN_OP = new C0ElementType("ASSIGN_OP");
  IElementType DEF_INCLUDE = new C0ElementType("DEF_INCLUDE");
  IElementType DEF_METHOD = new C0ElementType("DEF_METHOD");
  IElementType DEF_PREDICATE = new C0ElementType("DEF_PREDICATE");
  IElementType DEF_PREDICATES = new C0ElementType("DEF_PREDICATES");
  IElementType DEF_STRUCT = new C0ElementType("DEF_STRUCT");
  IElementType DEF_TYPE = new C0ElementType("DEF_TYPE");
  IElementType EXPR = new C0ElementType("EXPR");
  IElementType EXPRS = new C0ElementType("EXPRS");
  IElementType EXPR_0 = new C0ElementType("EXPR_0");
  IElementType EXPR_1 = new C0ElementType("EXPR_1");
  IElementType EXPR_2 = new C0ElementType("EXPR_2");
  IElementType EXPR_3 = new C0ElementType("EXPR_3");
  IElementType EXPR_4 = new C0ElementType("EXPR_4");
  IElementType EXPR_5 = new C0ElementType("EXPR_5");
  IElementType EXPR_ACC = new C0ElementType("EXPR_ACC");
  IElementType EXPR_ADD = new C0ElementType("EXPR_ADD");
  IElementType EXPR_ALLOC = new C0ElementType("EXPR_ALLOC");
  IElementType EXPR_ARROW = new C0ElementType("EXPR_ARROW");
  IElementType EXPR_BITWISE_NOT = new C0ElementType("EXPR_BITWISE_NOT");
  IElementType EXPR_BOOL = new C0ElementType("EXPR_BOOL");
  IElementType EXPR_DEC = new C0ElementType("EXPR_DEC");
  IElementType EXPR_DIV = new C0ElementType("EXPR_DIV");
  IElementType EXPR_DOT = new C0ElementType("EXPR_DOT");
  IElementType EXPR_EQ = new C0ElementType("EXPR_EQ");
  IElementType EXPR_GE = new C0ElementType("EXPR_GE");
  IElementType EXPR_GT = new C0ElementType("EXPR_GT");
  IElementType EXPR_ID = new C0ElementType("EXPR_ID");
  IElementType EXPR_IMPRECISION = new C0ElementType("EXPR_IMPRECISION");
  IElementType EXPR_INC = new C0ElementType("EXPR_INC");
  IElementType EXPR_INVOKE = new C0ElementType("EXPR_INVOKE");
  IElementType EXPR_LE = new C0ElementType("EXPR_LE");
  IElementType EXPR_LOGICAL_NOT = new C0ElementType("EXPR_LOGICAL_NOT");
  IElementType EXPR_LT = new C0ElementType("EXPR_LT");
  IElementType EXPR_MOD = new C0ElementType("EXPR_MOD");
  IElementType EXPR_MUL = new C0ElementType("EXPR_MUL");
  IElementType EXPR_NE = new C0ElementType("EXPR_NE");
  IElementType EXPR_NEG = new C0ElementType("EXPR_NEG");
  IElementType EXPR_NULL = new C0ElementType("EXPR_NULL");
  IElementType EXPR_NUMBER_10 = new C0ElementType("EXPR_NUMBER_10");
  IElementType EXPR_NUMBER_16 = new C0ElementType("EXPR_NUMBER_16");
  IElementType EXPR_PAREN = new C0ElementType("EXPR_PAREN");
  IElementType EXPR_REF = new C0ElementType("EXPR_REF");
  IElementType EXPR_RESULT = new C0ElementType("EXPR_RESULT");
  IElementType EXPR_SHL = new C0ElementType("EXPR_SHL");
  IElementType EXPR_SHR = new C0ElementType("EXPR_SHR");
  IElementType EXPR_SUB = new C0ElementType("EXPR_SUB");
  IElementType EXPR_UNFOLDING = new C0ElementType("EXPR_UNFOLDING");
  IElementType FILENAME = new C0ElementType("FILENAME");
  IElementType GHOSTS = new C0ElementType("GHOSTS");
  IElementType GHOST_ASSERT = new C0ElementType("GHOST_ASSERT");
  IElementType GHOST_FOLD = new C0ElementType("GHOST_FOLD");
  IElementType GHOST_UNFOLD = new C0ElementType("GHOST_UNFOLD");
  IElementType INVARIANT = new C0ElementType("INVARIANT");
  IElementType INVARIANTS = new C0ElementType("INVARIANTS");
  IElementType METHOD_BODY = new C0ElementType("METHOD_BODY");
  IElementType PARAMETER = new C0ElementType("PARAMETER");
  IElementType PARAMETERS = new C0ElementType("PARAMETERS");
  IElementType SPECS = new C0ElementType("SPECS");
  IElementType SPEC_ENSURES = new C0ElementType("SPEC_ENSURES");
  IElementType SPEC_REQUIRES = new C0ElementType("SPEC_REQUIRES");
  IElementType STMT_ASSERT = new C0ElementType("STMT_ASSERT");
  IElementType STMT_BLOCK = new C0ElementType("STMT_BLOCK");
  IElementType STMT_EXPR = new C0ElementType("STMT_EXPR");
  IElementType STMT_FOR = new C0ElementType("STMT_FOR");
  IElementType STMT_IF = new C0ElementType("STMT_IF");
  IElementType STMT_RETURN = new C0ElementType("STMT_RETURN");
  IElementType STMT_VAR = new C0ElementType("STMT_VAR");
  IElementType STMT_WHILE = new C0ElementType("STMT_WHILE");
  IElementType STRUCT_FIELD = new C0ElementType("STRUCT_FIELD");
  IElementType STRUCT_FIELDS = new C0ElementType("STRUCT_FIELDS");
  IElementType STRUCT_REFERENCE = new C0ElementType("STRUCT_REFERENCE");
  IElementType TYPE_MODIFIER = new C0ElementType("TYPE_MODIFIER");
  IElementType TYPE_REFERENCE = new C0ElementType("TYPE_REFERENCE");

  IElementType ACC = new C0TokenType("acc");
  IElementType ALLOC = new C0TokenType("alloc");
  IElementType ARROW = new C0TokenType("->");
  IElementType ASSERT = new C0TokenType("assert");
  IElementType ASSIGN = new C0TokenType("=");
  IElementType ASSIGN_ADD = new C0TokenType("+=");
  IElementType ASSIGN_AND = new C0TokenType("&=");
  IElementType ASSIGN_DIV = new C0TokenType("/=");
  IElementType ASSIGN_MOD = new C0TokenType("%=");
  IElementType ASSIGN_MUL = new C0TokenType("*=");
  IElementType ASSIGN_OR = new C0TokenType("|=");
  IElementType ASSIGN_SHL = new C0TokenType("<<=");
  IElementType ASSIGN_SHR = new C0TokenType(">>=");
  IElementType ASSIGN_SUB = new C0TokenType("-=");
  IElementType ASSIGN_XOR = new C0TokenType("^=");
  IElementType ASTERISK = new C0TokenType("*");
  IElementType BACKSLASH = new C0TokenType("\\\\");
  IElementType BITWISE_AND = new C0TokenType("&");
  IElementType BITWISE_NOT = new C0TokenType("~");
  IElementType BITWISE_OR = new C0TokenType("|");
  IElementType BITWISE_XOR = new C0TokenType("^");
  IElementType BOOL = new C0TokenType("bool");
  IElementType COLON = new C0TokenType(":");
  IElementType COMMA = new C0TokenType(",");
  IElementType DEC = new C0TokenType("--");
  IElementType DOT = new C0TokenType(".");
  IElementType DOUBLEQUOTE = new C0TokenType("\"");
  IElementType ELSE = new C0TokenType("else");
  IElementType ENSURES = new C0TokenType("ensures");
  IElementType EQ = new C0TokenType("==");
  IElementType FALSE = new C0TokenType("false");
  IElementType FOLD = new C0TokenType("fold");
  IElementType FOR = new C0TokenType("for");
  IElementType GE = new C0TokenType(">=");
  IElementType GT = new C0TokenType(">");
  IElementType HASH = new C0TokenType("#");
  IElementType IDENTIFIER = new C0TokenType("IDENTIFIER");
  IElementType IF = new C0TokenType("if");
  IElementType IN = new C0TokenType("in");
  IElementType INC = new C0TokenType("++");
  IElementType INT = new C0TokenType("int");
  IElementType LBRACE = new C0TokenType("{");
  IElementType LE = new C0TokenType("<=");
  IElementType LI = new C0TokenType("loop_invariant");
  IElementType LOGICAL_AND = new C0TokenType("&&");
  IElementType LOGICAL_NOT = new C0TokenType("!");
  IElementType LOGICAL_OR = new C0TokenType("||");
  IElementType LPAREN = new C0TokenType("(");
  IElementType LT = new C0TokenType("<");
  IElementType MINUS = new C0TokenType("-");
  IElementType NE = new C0TokenType("!=");
  IElementType NULL = new C0TokenType("NULL");
  IElementType NUMBER_10 = new C0TokenType("NUMBER_10");
  IElementType NUMBER_16 = new C0TokenType("NUMBER_16");
  IElementType PERCENT = new C0TokenType("%");
  IElementType PLUS = new C0TokenType("+");
  IElementType PREDICATE = new C0TokenType("predicate");
  IElementType QUESTION = new C0TokenType("?");
  IElementType RBRACE = new C0TokenType("}");
  IElementType REQUIRES = new C0TokenType("requires");
  IElementType RESULT = new C0TokenType("result");
  IElementType RETURN = new C0TokenType("return");
  IElementType RPAREN = new C0TokenType(")");
  IElementType SEMICOLON = new C0TokenType(";");
  IElementType SHL = new C0TokenType("<<");
  IElementType SHR = new C0TokenType(">>");
  IElementType SLASH = new C0TokenType("/");
  IElementType SPEC_END = new C0TokenType("SPEC_END");
  IElementType SPEC_START = new C0TokenType("SPEC_START");
  IElementType STRUCT = new C0TokenType("struct");
  IElementType TRUE = new C0TokenType("true");
  IElementType TYPEDEF = new C0TokenType("typedef");
  IElementType UNFOLD = new C0TokenType("unfold");
  IElementType UNFOLDING = new C0TokenType("unfolding");
  IElementType USE = new C0TokenType("use");
  IElementType VOID = new C0TokenType("void");
  IElementType WHILE = new C0TokenType("while");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == ASSIGN_OP) {
        return new C0AssignOpImpl(node);
      }
      else if (type == DEF_INCLUDE) {
        return new C0DefIncludeImpl(node);
      }
      else if (type == DEF_METHOD) {
        return new C0DefMethodImpl(node);
      }
      else if (type == DEF_PREDICATE) {
        return new C0DefPredicateImpl(node);
      }
      else if (type == DEF_PREDICATES) {
        return new C0DefPredicatesImpl(node);
      }
      else if (type == DEF_STRUCT) {
        return new C0DefStructImpl(node);
      }
      else if (type == DEF_TYPE) {
        return new C0DefTypeImpl(node);
      }
      else if (type == EXPRS) {
        return new C0ExprsImpl(node);
      }
      else if (type == EXPR_0) {
        return new C0Expr0Impl(node);
      }
      else if (type == EXPR_1) {
        return new C0Expr1Impl(node);
      }
      else if (type == EXPR_2) {
        return new C0Expr2Impl(node);
      }
      else if (type == EXPR_3) {
        return new C0Expr3Impl(node);
      }
      else if (type == EXPR_4) {
        return new C0Expr4Impl(node);
      }
      else if (type == EXPR_5) {
        return new C0Expr5Impl(node);
      }
      else if (type == EXPR_ACC) {
        return new C0ExprAccImpl(node);
      }
      else if (type == EXPR_ADD) {
        return new C0ExprAddImpl(node);
      }
      else if (type == EXPR_ALLOC) {
        return new C0ExprAllocImpl(node);
      }
      else if (type == EXPR_ARROW) {
        return new C0ExprArrowImpl(node);
      }
      else if (type == EXPR_BITWISE_NOT) {
        return new C0ExprBitwiseNotImpl(node);
      }
      else if (type == EXPR_BOOL) {
        return new C0ExprBoolImpl(node);
      }
      else if (type == EXPR_DEC) {
        return new C0ExprDecImpl(node);
      }
      else if (type == EXPR_DIV) {
        return new C0ExprDivImpl(node);
      }
      else if (type == EXPR_DOT) {
        return new C0ExprDotImpl(node);
      }
      else if (type == EXPR_EQ) {
        return new C0ExprEqImpl(node);
      }
      else if (type == EXPR_GE) {
        return new C0ExprGeImpl(node);
      }
      else if (type == EXPR_GT) {
        return new C0ExprGtImpl(node);
      }
      else if (type == EXPR_ID) {
        return new C0ExprIdImpl(node);
      }
      else if (type == EXPR_IMPRECISION) {
        return new C0ExprImprecisionImpl(node);
      }
      else if (type == EXPR_INC) {
        return new C0ExprIncImpl(node);
      }
      else if (type == EXPR_INVOKE) {
        return new C0ExprInvokeImpl(node);
      }
      else if (type == EXPR_LE) {
        return new C0ExprLeImpl(node);
      }
      else if (type == EXPR_LOGICAL_NOT) {
        return new C0ExprLogicalNotImpl(node);
      }
      else if (type == EXPR_LT) {
        return new C0ExprLtImpl(node);
      }
      else if (type == EXPR_MOD) {
        return new C0ExprModImpl(node);
      }
      else if (type == EXPR_MUL) {
        return new C0ExprMulImpl(node);
      }
      else if (type == EXPR_NE) {
        return new C0ExprNeImpl(node);
      }
      else if (type == EXPR_NEG) {
        return new C0ExprNegImpl(node);
      }
      else if (type == EXPR_NULL) {
        return new C0ExprNullImpl(node);
      }
      else if (type == EXPR_NUMBER_10) {
        return new C0ExprNumber10Impl(node);
      }
      else if (type == EXPR_NUMBER_16) {
        return new C0ExprNumber16Impl(node);
      }
      else if (type == EXPR_PAREN) {
        return new C0ExprParenImpl(node);
      }
      else if (type == EXPR_REF) {
        return new C0ExprRefImpl(node);
      }
      else if (type == EXPR_RESULT) {
        return new C0ExprResultImpl(node);
      }
      else if (type == EXPR_SHL) {
        return new C0ExprShlImpl(node);
      }
      else if (type == EXPR_SHR) {
        return new C0ExprShrImpl(node);
      }
      else if (type == EXPR_SUB) {
        return new C0ExprSubImpl(node);
      }
      else if (type == EXPR_UNFOLDING) {
        return new C0ExprUnfoldingImpl(node);
      }
      else if (type == FILENAME) {
        return new C0FilenameImpl(node);
      }
      else if (type == GHOSTS) {
        return new C0GhostsImpl(node);
      }
      else if (type == GHOST_ASSERT) {
        return new C0GhostAssertImpl(node);
      }
      else if (type == GHOST_FOLD) {
        return new C0GhostFoldImpl(node);
      }
      else if (type == GHOST_UNFOLD) {
        return new C0GhostUnfoldImpl(node);
      }
      else if (type == INVARIANT) {
        return new C0InvariantImpl(node);
      }
      else if (type == INVARIANTS) {
        return new C0InvariantsImpl(node);
      }
      else if (type == METHOD_BODY) {
        return new C0MethodBodyImpl(node);
      }
      else if (type == PARAMETER) {
        return new C0ParameterImpl(node);
      }
      else if (type == PARAMETERS) {
        return new C0ParametersImpl(node);
      }
      else if (type == SPECS) {
        return new C0SpecsImpl(node);
      }
      else if (type == SPEC_ENSURES) {
        return new C0SpecEnsuresImpl(node);
      }
      else if (type == SPEC_REQUIRES) {
        return new C0SpecRequiresImpl(node);
      }
      else if (type == STMT_ASSERT) {
        return new C0StmtAssertImpl(node);
      }
      else if (type == STMT_BLOCK) {
        return new C0StmtBlockImpl(node);
      }
      else if (type == STMT_EXPR) {
        return new C0StmtExprImpl(node);
      }
      else if (type == STMT_FOR) {
        return new C0StmtForImpl(node);
      }
      else if (type == STMT_IF) {
        return new C0StmtIfImpl(node);
      }
      else if (type == STMT_RETURN) {
        return new C0StmtReturnImpl(node);
      }
      else if (type == STMT_VAR) {
        return new C0StmtVarImpl(node);
      }
      else if (type == STMT_WHILE) {
        return new C0StmtWhileImpl(node);
      }
      else if (type == STRUCT_FIELD) {
        return new C0StructFieldImpl(node);
      }
      else if (type == STRUCT_FIELDS) {
        return new C0StructFieldsImpl(node);
      }
      else if (type == STRUCT_REFERENCE) {
        return new C0StructReferenceImpl(node);
      }
      else if (type == TYPE_MODIFIER) {
        return new C0TypeModifierImpl(node);
      }
      else if (type == TYPE_REFERENCE) {
        return new C0TypeReferenceImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
