// This is a generated file. Not intended for manual editing.
package edu.cmu.c0.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface C0StmtWhile extends PsiElement {

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

  @Nullable
  C0StmtExpr getStmtExpr();

  @Nullable
  C0StmtFor getStmtFor();

  @Nullable
  C0StmtIf getStmtIf();

  @Nullable
  C0StmtReturn getStmtReturn();

  @Nullable
  C0StmtVar getStmtVar();

  @Nullable
  C0StmtWhile getStmtWhile();

}
