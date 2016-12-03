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

  public void accept(@NotNull Visitor visitor) {
    visitor.visitSelectStatement(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof Visitor) accept((Visitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public ColumnSource getColumnSource() {
    return findChildByClass(ColumnSource.class);
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
  @Nullable
  public NumberLiteral getTopCount() {
    TopOpt p1 = findChildByClass(TopOpt.class);
    if (p1 == null) return null;
    return p1.getNumberLiteral();
  }

  @Override
  @Nullable
  public ExpressionList getGrouping() {
    return findChildByClass(ExpressionList.class);
  }

}
