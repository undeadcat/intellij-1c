// This is a generated file. Not intended for manual editing.
package generated;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static generated.GeneratedTypes.*;

public class BinaryExpressionImpl extends ExpressionImpl implements BinaryExpression {

  public BinaryExpressionImpl(ASTNode node) {
    super(node);
  }

  public <R> R accept(@NotNull Visitor<R> visitor) {
    return visitor.visitBinaryExpression(this);
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
  public Expression getLeft() {
    List<Expression> p1 = getExpressionList();
    return p1.get(0);
  }

  @Override
  @Nullable
  public Expression getRight() {
    List<Expression> p1 = getExpressionList();
    return p1.size() < 2 ? null : p1.get(1);
  }

}
