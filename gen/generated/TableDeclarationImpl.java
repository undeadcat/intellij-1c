// This is a generated file. Not intended for manual editing.
package generated;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static generated.GeneratedTypes.*;

public class TableDeclarationImpl extends ColumnSourceImpl implements TableDeclaration {

  public TableDeclarationImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull Visitor visitor) {
    visitor.visitTableDeclaration(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof Visitor) accept((Visitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public Identifier getAlias() {
    Alias p1 = findChildByClass(Alias.class);
    if (p1 == null) return null;
    return p1.getIdentifier();
  }

  @Override
  @NotNull
  public Identifier getTableName() {
    return findNotNullChildByClass(Identifier.class);
  }

}
