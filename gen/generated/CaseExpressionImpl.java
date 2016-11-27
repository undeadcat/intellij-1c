// This is a generated file. Not intended for manual editing.
package generated;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static generated.GeneratedTypes.*;

public class CaseExpressionImpl extends ExpressionImpl implements CaseExpression {

  public CaseExpressionImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull Visitor visitor) {
    visitor.visitCaseExpression(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof Visitor) accept((Visitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<CaseElement> getCaseElementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, CaseElement.class);
  }

  @Override
  @Nullable
  public Expression getDefault() {
    return findChildByClass(Expression.class);
  }

}
