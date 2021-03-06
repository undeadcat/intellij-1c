// This is a generated file. Not intended for manual editing.
package generated;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static generated.GeneratedTypes.*;

public class AggregateExpressionImpl extends ExpressionImpl implements AggregateExpression {

  public AggregateExpressionImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull Visitor visitor) {
    visitor.visitAggregateExpression(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof Visitor) accept((Visitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public Expression getExpression() {
    return findChildByClass(Expression.class);
  }

  @Override
  @NotNull
  public PsiElement getAggregationFunction() {
    return findNotNullChildByType(AGGREGATIONFUNCTION);
  }

  @Override
  @Nullable
  public PsiElement getDistinctKeyword() {
    return findChildByType(DISTINCTKEYWORD);
  }

}
