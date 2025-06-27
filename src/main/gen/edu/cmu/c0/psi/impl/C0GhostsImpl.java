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

public class C0GhostsImpl extends ASTWrapperPsiElement implements C0Ghosts {

  public C0GhostsImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull C0Visitor visitor) {
    visitor.visitGhosts(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof C0Visitor) accept((C0Visitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<C0GhostAssert> getGhostAssertList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, C0GhostAssert.class);
  }

  @Override
  @NotNull
  public List<C0GhostFold> getGhostFoldList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, C0GhostFold.class);
  }

  @Override
  @NotNull
  public List<C0GhostUnfold> getGhostUnfoldList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, C0GhostUnfold.class);
  }

}
