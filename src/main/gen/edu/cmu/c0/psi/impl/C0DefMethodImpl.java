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

public class C0DefMethodImpl extends ASTWrapperPsiElement implements C0DefMethod {

  public C0DefMethodImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull C0Visitor visitor) {
    visitor.visitDefMethod(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof C0Visitor) accept((C0Visitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public C0MethodBody getMethodBody() {
    return findNotNullChildByClass(C0MethodBody.class);
  }

  @Override
  @Nullable
  public C0Parameters getParameters() {
    return findChildByClass(C0Parameters.class);
  }

  @Override
  @NotNull
  public List<C0Specs> getSpecsList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, C0Specs.class);
  }

  @Override
  @NotNull
  public C0TypeReference getTypeReference() {
    return findNotNullChildByClass(C0TypeReference.class);
  }

}
