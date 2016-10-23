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

public class JoinKindImpl extends ASTWrapperPsiElement implements JoinKind {

  public JoinKindImpl(ASTNode node) {
    super(node);
  }

  public <R> R accept(@NotNull Visitor<R> visitor) {
    return visitor.visitJoinKind(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof Visitor) accept((Visitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PsiElement getFullKeyword() {
    return findChildByType(FULLKEYWORD);
  }

  @Override
  @Nullable
  public PsiElement getInnerKeyword() {
    return findChildByType(INNERKEYWORD);
  }

  @Override
  @Nullable
  public PsiElement getLeftKeyword() {
    return findChildByType(LEFTKEYWORD);
  }

  @Override
  @Nullable
  public PsiElement getOuterKeyword() {
    return findChildByType(OUTERKEYWORD);
  }

  @Override
  @Nullable
  public PsiElement getRightKeyword() {
    return findChildByType(RIGHTKEYWORD);
  }

}
