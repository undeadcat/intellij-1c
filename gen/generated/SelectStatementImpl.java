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

public class SelectStatementImpl extends ASTWrapperPsiElement implements SelectStatement {

  public SelectStatementImpl(ASTNode node) {
    super(node);
  }

  public <R> R accept(@NotNull Visitor<R> visitor) {
    return visitor.visitSelectStatement(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof Visitor) accept((Visitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<Expression> getExpressionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, Expression.class);
  }

  @Override
  @NotNull
  public List<JoinItem> getJoinItemList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, JoinItem.class);
  }

  @Override
  @Nullable
  public SelectionList getSelectionList() {
    return findChildByClass(SelectionList.class);
  }

  @Override
  @NotNull
  public TableDeclaration getTableDeclaration() {
    return findNotNullChildByClass(TableDeclaration.class);
  }

  @Override
  @Nullable
  public NumberLiteral getTopCount() {
    TopOpt p1 = findNotNullChildByClass(TopOpt.class);
    return p1.getNumberLiteral();
  }

  @Override
  @Nullable
  public Expression getWhereExpression() {
    List<Expression> p1 = getExpressionList();
    return p1.size() < 1 ? null : p1.get(0);
  }

  @Override
  @Nullable
  public Expression getHavingExpression() {
    List<Expression> p1 = getExpressionList();
    return p1.size() < 2 ? null : p1.get(1);
  }

}
