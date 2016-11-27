// This is a generated file. Not intended for manual editing.
package generated;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface SqlQuery extends _1cElement {

  @NotNull
  List<OrderItem> getOrderItemList();

  @NotNull
  SelectStatement getSelectStatement();

  @NotNull
  List<UnionClause> getUnionClauseList();

  @Nullable
  PsiElement getOrderKeyword();

}
