// This is a generated file. Not intended for manual editing.
package generated;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.simple1c.boilerplate._1cElement;

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
  NumberLiteral getTopCount();

  @Nullable
  ExpressionList getGrouping();

}
