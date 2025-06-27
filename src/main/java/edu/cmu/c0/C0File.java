package edu.cmu.c0;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

public class C0File extends PsiFileBase {
    public C0File(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, C0Language.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return C0FileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "C0 File";
    }
}
