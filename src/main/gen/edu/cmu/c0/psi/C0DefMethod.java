// This is a generated file. Not intended for manual editing.
package edu.cmu.c0.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface C0DefMethod extends PsiElement {

  @NotNull
  C0MethodBody getMethodBody();

  @Nullable
  C0Parameters getParameters();

  @NotNull
  List<C0Specs> getSpecsList();

  @NotNull
  C0TypeReference getTypeReference();

}
