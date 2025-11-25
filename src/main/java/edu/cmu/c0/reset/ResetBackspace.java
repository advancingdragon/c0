package edu.cmu.c0.reset;

import com.intellij.codeInsight.editorActions.BackspaceHandlerDelegate;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiFile;
import edu.cmu.c0.U;
import org.jetbrains.annotations.NotNull;

public class ResetBackspace extends BackspaceHandlerDelegate {
    @Override
    public void beforeCharDeleted(char c, @NotNull PsiFile file, @NotNull Editor editor) {
        if (c == '\n') {
            U.reset(editor);
        }
    }

    @Override
    public boolean charDeleted(char c, @NotNull PsiFile file, @NotNull Editor editor) {
        return false;
    }
}
