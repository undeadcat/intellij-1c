// This is a generated file. Not intended for manual editing.
package generated;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface SelectStatement extends _1cElement {

  @Nullable
  ColumnSource getColumnSource();

  @NotNull
  List<Expression> getExpressionList();

  @NotNull
  List<JoinItem> getJoinItemList();

  @Nullable
  SelectionList getSelectionList();

  @Nullable
  PsiElement getDistinctKeyword();

  @Nullable
  PsiElement getFromKeyword();

  @Nullable
  PsiElement getGroupKeyword();

  @Nullable
  PsiElement getHavingKeyword();

  @NotNull
  PsiElement getSelectKeyword();

  @Nullable
  PsiElement getWhereKeyword();

  @Nullable
  NumberLiteral getTopCount();

  @Nullable
  Expression getWhereExpression();

  @Nullable
  Expression getHavingExpression();

}
