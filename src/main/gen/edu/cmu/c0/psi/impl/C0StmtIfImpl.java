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

public class C0StmtIfImpl extends ASTWrapperPsiElement implements C0StmtIf {

  public C0StmtIfImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull C0Visitor visitor) {
    visitor.visitStmtIf(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof C0Visitor) accept((C0Visitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public C0Expr getExpr() {
    return findNotNullChildByClass(C0Expr.class);
  }

  @Override
  @NotNull
  public List<C0Ghosts> getGhostsList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, C0Ghosts.class);
  }

  @Override
  @NotNull
  public List<C0StmtAssert> getStmtAssertList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, C0StmtAssert.class);
  }

  @Override
  @NotNull
  public List<C0StmtBlock> getStmtBlockList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, C0StmtBlock.class);
  }

  @Override
  @NotNull
  public List<C0StmtExpr> getStmtExprList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, C0StmtExpr.class);
  }

  @Override
  @NotNull
  public List<C0StmtFor> getStmtForList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, C0StmtFor.class);
  }

  @Override
  @NotNull
  public List<C0StmtIf> getStmtIfList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, C0StmtIf.class);
  }

  @Override
  @NotNull
  public List<C0StmtReturn> getStmtReturnList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, C0StmtReturn.class);
  }

  @Override
  @NotNull
  public List<C0StmtVar> getStmtVarList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, C0StmtVar.class);
  }

  @Override
  @NotNull
  public List<C0StmtWhile> getStmtWhileList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, C0StmtWhile.class);
  }

}
