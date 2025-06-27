package edu.cmu.c0.psi;

import com.intellij.psi.tree.IElementType;
import edu.cmu.c0.C0Language;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class C0ElementType extends IElementType {
    public C0ElementType(@NotNull @NonNls String debugName) {
        super(debugName, C0Language.INSTANCE);
    }
}
