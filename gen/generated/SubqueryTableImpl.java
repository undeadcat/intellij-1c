// This is a generated file. Not intended for manual editing.
package generated;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static generated.GeneratedTypes.*;

public class SubqueryTableImpl extends ColumnSourceImpl implements SubqueryTable {

  public SubqueryTableImpl(ASTNode node) {
    super(node);
  }

  public <R> R accept(@NotNull Visitor<R> visitor) {
    return visitor.visitSubqueryTable(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof Visitor) accept((Visitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public Subquery getSubquery() {
    return findNotNullChildByClass(Subquery.class);
  }

  @Override
  @NotNull
  public Identifier getAlias() {
    Alias p1 = findNotNullChildByClass(Alias.class);
    return p1.getIdentifier();
  }

}
