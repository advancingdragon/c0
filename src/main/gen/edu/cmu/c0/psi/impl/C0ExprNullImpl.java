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

public class C0ExprNullImpl extends C0ExprImpl implements C0ExprNull {

  public C0ExprNullImpl(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public void accept(@NotNull C0Visitor visitor) {
    visitor.visitExprNull(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof C0Visitor) accept((C0Visitor)visitor);
    else super.accept(visitor);
  }

}
