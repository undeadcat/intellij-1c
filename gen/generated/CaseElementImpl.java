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

public class CaseElementImpl extends ASTWrapperPsiElement implements CaseElement {

  public CaseElementImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull Visitor visitor) {
    visitor.visitCaseElement(this);
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
  @Nullable
  public Expression getCondition() {
    List<Expression> p1 = getExpressionList();
    return p1.size() < 1 ? null : p1.get(0);
  }

  @Override
  @Nullable
  public Expression getResult() {
    List<Expression> p1 = getExpressionList();
    return p1.size() < 2 ? null : p1.get(1);
  }

}
