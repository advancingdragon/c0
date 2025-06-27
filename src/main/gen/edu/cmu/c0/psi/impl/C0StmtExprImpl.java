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

public class C0StmtExprImpl extends ASTWrapperPsiElement implements C0StmtExpr {

  public C0StmtExprImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull C0Visitor visitor) {
    visitor.visitStmtExpr(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof C0Visitor) accept((C0Visitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public C0AssignOp getAssignOp() {
    return findChildByClass(C0AssignOp.class);
  }

  @Override
  @NotNull
  public List<C0Expr> getExprList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, C0Expr.class);
  }

}
