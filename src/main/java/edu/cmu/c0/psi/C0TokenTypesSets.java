package edu.cmu.c0.psi;

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

public interface C0TokenTypesSets {
    IElementType BAD_CHARACTER = TokenType.BAD_CHARACTER;
    IElementType WHITE_SPACE = TokenType.WHITE_SPACE;
    IElementType COMMENT_1LINE = new C0TokenType("COMMENT_1LINE");
    IElementType COMMENT_BLOCK = new C0TokenType("COMMENT_BLOCK");
    TokenSet COMMENTS = TokenSet.create(COMMENT_1LINE, COMMENT_BLOCK);
    TokenSet KEYWORDS = TokenSet.create(C0Types.ACC, C0Types.ALLOC,
            C0Types.ASSERT, C0Types.BOOL, C0Types.ELSE, C0Types.ENSURES,
            C0Types.FOLD, C0Types.FOR, C0Types.IF, C0Types.IN, C0Types.INT,
            C0Types.LI, C0Types.PREDICATE, C0Types.REQUIRES, C0Types.RETURN,
            C0Types.STRUCT, C0Types.TYPEDEF, C0Types.UNFOLD,
            C0Types.UNFOLDING, C0Types.VOID, C0Types.WHILE);
    TokenSet LITERALS = TokenSet.create(C0Types.NULL, C0Types.TRUE,
            C0Types.FALSE, C0Types.NUMBER_10, C0Types.NUMBER_16);
}
