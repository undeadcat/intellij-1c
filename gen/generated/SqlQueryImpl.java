// This is a generated file. Not intended for manual editing.
package generated;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static generated.GeneratedTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;

public class SqlQueryImpl extends ASTWrapperPsiElement implements SqlQuery {

  public SqlQueryImpl(ASTNode node) {
    super(node);
  }

  public <R> R accept(@NotNull Visitor<R> visitor) {
    return visitor.visitSqlQuery(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof Visitor) accept((Visitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<OrderItem> getOrderItemList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, OrderItem.class);
  }

  @Override
  @NotNull
  public List<SelectStatement> getSelectStatementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, SelectStatement.class);
  }

}
