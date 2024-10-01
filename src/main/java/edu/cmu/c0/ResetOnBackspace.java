package edu.cmu.c0;

import com.intellij.codeInsight.editorActions.BackspaceHandlerDelegate;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class ResetOnBackspace extends BackspaceHandlerDelegate {
    public void beforeCharDeleted(char c, @NotNull PsiFile file, @NotNull Editor editor) {
        U.reset(editor);
    }

    public boolean charDeleted(char c, @NotNull PsiFile file, @NotNull Editor editor) {
        return false;
    }
}
