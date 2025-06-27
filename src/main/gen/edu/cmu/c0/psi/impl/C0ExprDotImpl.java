// This is a generated file. Not intended for manual editing.
package edu.cmu.c0.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static edu.cmu.c0.psi.C0Types.*;
import edu.cmu.c0.psi.*;

public class C0ExprDotImpl extends C0ExprImpl implements C0ExprDot {

  public C0ExprDotImpl(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public void accept(@NotNull C0Visitor visitor) {
    visitor.visitExprDot(this);
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

}
