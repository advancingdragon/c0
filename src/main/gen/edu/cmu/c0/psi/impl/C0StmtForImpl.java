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

public class C0StmtForImpl extends ASTWrapperPsiElement implements C0StmtFor {

  public C0StmtForImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull C0Visitor visitor) {
    visitor.visitStmtFor(this);
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
  @Nullable
  public C0Ghosts getGhosts() {
    return findChildByClass(C0Ghosts.class);
  }

  @Override
  @NotNull
  public List<C0Invariants> getInvariantsList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, C0Invariants.class);
  }

  @Override
  @Nullable
  public C0StmtAssert getStmtAssert() {
    return findChildByClass(C0StmtAssert.class);
  }

  @Override
  @Nullable
  public C0StmtBlock getStmtBlock() {
    return findChildByClass(C0StmtBlock.class);
  }

  @Override
  @NotNull
  public List<C0StmtExpr> getStmtExprList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, C0StmtExpr.class);
  }

  @Override
  @Nullable
  public C0StmtFor getStmtFor() {
    return findChildByClass(C0StmtFor.class);
  }

  @Override
  @Nullable
  public C0StmtIf getStmtIf() {
    return findChildByClass(C0StmtIf.class);
  }

  @Override
  @Nullable
  public C0StmtReturn getStmtReturn() {
    return findChildByClass(C0StmtReturn.class);
  }

  @Override
  @NotNull
  public List<C0StmtVar> getStmtVarList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, C0StmtVar.class);
  }

  @Override
  @Nullable
  public C0StmtWhile getStmtWhile() {
    return findChildByClass(C0StmtWhile.class);
  }

}
