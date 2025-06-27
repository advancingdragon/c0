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

public class C0ExprRefImpl extends C0ExprImpl implements C0ExprRef {

  public C0ExprRefImpl(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public void accept(@NotNull C0Visitor visitor) {
    visitor.visitExprRef(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof C0Visitor) accept((C0Visitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public C0Expr getExpr() {
    return findChildByClass(C0Expr.class);
  }

}
