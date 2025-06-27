// This is a generated file. Not intended for manual editing.
package edu.cmu.c0.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface C0StmtFor extends PsiElement {

  @NotNull
  C0Expr getExpr();

  @Nullable
  C0Ghosts getGhosts();

  @NotNull
  List<C0Invariants> getInvariantsList();

  @Nullable
  C0StmtAssert getStmtAssert();

  @Nullable
  C0StmtBlock getStmtBlock();

  @NotNull
  List<C0StmtExpr> getStmtExprList();

  @Nullable
  C0StmtFor getStmtFor();

  @Nullable
  C0StmtIf getStmtIf();

  @Nullable
  C0StmtReturn getStmtReturn();

  @NotNull
  List<C0StmtVar> getStmtVarList();

  @Nullable
  C0StmtWhile getStmtWhile();

}
