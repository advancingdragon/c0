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

public class C0TypeReferenceImpl extends ASTWrapperPsiElement implements C0TypeReference {

  public C0TypeReferenceImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull C0Visitor visitor) {
    visitor.visitTypeReference(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof C0Visitor) accept((C0Visitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public C0StructReference getStructReference() {
    return findNotNullChildByClass(C0StructReference.class);
  }

  @Override
  @NotNull
  public List<C0TypeModifier> getTypeModifierList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, C0TypeModifier.class);
  }

}
