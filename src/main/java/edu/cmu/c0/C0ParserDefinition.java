package edu.cmu.c0;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import edu.cmu.c0.parser.C0Parser;
import edu.cmu.c0.psi.C0TokenTypesSets;
import edu.cmu.c0.psi.C0Types;
import org.jetbrains.annotations.NotNull;

public class C0ParserDefinition implements ParserDefinition {
    public static final IFileElementType FILE =
            new IFileElementType(C0Language.INSTANCE);

    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        return new C0LexerAdapter();
    }

    @NotNull
    @Override
    public TokenSet getCommentTokens() {
        return C0TokenTypesSets.COMMENTS;
    }

    @NotNull
    @Override
    public TokenSet getStringLiteralElements() {
        return TokenSet.EMPTY;
    }

    @NotNull
    @Override
    public PsiParser createParser(final Project project) {
        return new C0Parser();
    }

    @NotNull
    @Override
    public IFileElementType getFileNodeType() {
        return FILE;
    }

    @NotNull
    @Override
    public PsiFile createFile(@NotNull FileViewProvider viewProvider) {
        return new C0File(viewProvider);
    }

    @NotNull
    @Override
    public PsiElement createElement(ASTNode node) {
        return C0Types.Factory.createElement(node);
    }
}
