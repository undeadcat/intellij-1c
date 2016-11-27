// This is a generated file. Not intended for manual editing.
package generated;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static generated.GeneratedTypes.*;

public class InExpressionImpl extends ExpressionImpl implements InExpression {

  public InExpressionImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull Visitor visitor) {
    visitor.visitInExpression(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof Visitor) accept((Visitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public Expression getExpression() {
    return findNotNullChildByClass(Expression.class);
  }

  @Override
  @Nullable
  public ExpressionList getExpressionList() {
    return findChildByClass(ExpressionList.class);
  }

  @Override
  @Nullable
  public Subquery getSubquery() {
    return findChildByClass(Subquery.class);
  }

}
