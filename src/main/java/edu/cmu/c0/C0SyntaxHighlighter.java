package edu.cmu.c0;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import edu.cmu.c0.psi.C0TokenTypesSets;
import edu.cmu.c0.psi.C0Types;
import org.jetbrains.annotations.NotNull;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class C0SyntaxHighlighter extends SyntaxHighlighterBase {
    public static final TextAttributesKey BAD_CHARACTER =
            createTextAttributesKey("C0_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);
    public static final TextAttributesKey COMMENT_1LINE =
            createTextAttributesKey("C0_COMMENT_1LINE", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey COMMENT_BLOCK =
            createTextAttributesKey("C0_COMMENT_BLOCK", DefaultLanguageHighlighterColors.BLOCK_COMMENT);
    public static final TextAttributesKey KEYWORD =
            createTextAttributesKey("C0_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey LITERAL =
            createTextAttributesKey("C0_LITERAL", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey SPEC =
            createTextAttributesKey("C0_SPEC_START", DefaultLanguageHighlighterColors.LABEL);

    private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{BAD_CHARACTER};
    private static final TextAttributesKey[] COMMENT_1LINE_KEYS = new TextAttributesKey[]{COMMENT_1LINE};
    private static final TextAttributesKey[] COMMENT_BLOCK_KEYS = new TextAttributesKey[]{COMMENT_BLOCK};
    private static final TextAttributesKey[] KEYWORD_KEYS = new TextAttributesKey[]{KEYWORD};
    private static final TextAttributesKey[] LITERAL_KEYS = new TextAttributesKey[]{LITERAL};
    private static final TextAttributesKey[] SPEC_KEYS = new TextAttributesKey[]{SPEC};
    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new C0LexerAdapter();
    }

    @Override
    public TextAttributesKey @NotNull [] getTokenHighlights(IElementType tokenType) {
        if (tokenType.equals(C0TokenTypesSets.COMMENT_1LINE)) {
            return COMMENT_1LINE_KEYS;
        }
        if (tokenType.equals(C0TokenTypesSets.COMMENT_BLOCK)) {
            return COMMENT_BLOCK_KEYS;
        }
        if (tokenType.equals(C0Types.SPEC_START) || tokenType.equals(C0Types.SPEC_END)) {
            return SPEC_KEYS;
        }
        if (C0TokenTypesSets.KEYWORDS.contains(tokenType)) {
            return KEYWORD_KEYS;
        }
        if (C0TokenTypesSets.LITERALS.contains(tokenType)) {
            return LITERAL_KEYS;
        }
        if (tokenType.equals(C0TokenTypesSets.BAD_CHARACTER)) {
            return BAD_CHAR_KEYS;
        }
        return EMPTY_KEYS;
    }
}
