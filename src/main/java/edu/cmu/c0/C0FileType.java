package edu.cmu.c0;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import javax.swing.Icon;

public class C0FileType extends LanguageFileType {
    public static final C0FileType INSTANCE = new C0FileType();

    private C0FileType() {
        super(C0Language.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "C0 File";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "C0 language file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "c0";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return C0Icons.FILE;
    }
}
