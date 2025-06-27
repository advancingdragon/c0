// This is a generated file. Not intended for manual editing.
package edu.cmu.c0.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static edu.cmu.c0.psi.C0Types.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import edu.cmu.c0.psi.*;

public class C0DefStructImpl extends ASTWrapperPsiElement implements C0DefStruct {

  public C0DefStructImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull C0Visitor visitor) {
    visitor.visitDefStruct(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof C0Visitor) accept((C0Visitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public C0StructFields getStructFields() {
    return findChildByClass(C0StructFields.class);
  }

}
