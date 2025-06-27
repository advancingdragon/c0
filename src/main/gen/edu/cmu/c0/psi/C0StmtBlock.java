// This is a generated file. Not intended for manual editing.
package edu.cmu.c0.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface C0StmtBlock extends PsiElement {

  @NotNull
  List<C0Ghosts> getGhostsList();

  @NotNull
  List<C0StmtAssert> getStmtAssertList();

  @NotNull
  List<C0StmtBlock> getStmtBlockList();

  @NotNull
  List<C0StmtExpr> getStmtExprList();

  @NotNull
  List<C0StmtFor> getStmtForList();

  @NotNull
  List<C0StmtIf> getStmtIfList();

  @NotNull
  List<C0StmtReturn> getStmtReturnList();

  @NotNull
  List<C0StmtVar> getStmtVarList();

  @NotNull
  List<C0StmtWhile> getStmtWhileList();

}
