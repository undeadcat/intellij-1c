// This is a generated file. Not intended for manual editing.
package generated;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface JoinItem extends PsiElement {

  @Nullable
  ColumnSource getColumnSource();

  @Nullable
  Expression getExpression();

  @Nullable
  JoinKind getJoinKind();

  @NotNull
  PsiElement getJoinKeyword();

}
