// This is a generated file. Not intended for manual editing.
package generated;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiElement;

public class Visitor<R> extends PsiElementVisitor {

  public R visitAlias(@NotNull Alias o) {
    return visitPsiElement(o);
  }

  public R visitBoolLiteral(@NotNull BoolLiteral o) {
    return visitExpression(o);
  }

  public R visitColumnSource(@NotNull ColumnSource o) {
    return visitPsiElement(o);
  }

  public R visitExpression(@NotNull Expression o) {
    return visitPsiElement(o);
  }

  public R visitExpressionList(@NotNull ExpressionList o) {
    return visitPsiElement(o);
  }

  public R visitIdentifier(@NotNull Identifier o) {
    return visitExpression(o);
  }

  public R visitJoinItem(@NotNull JoinItem o) {
    return visitPsiElement(o);
  }

  public R visitJoinKind(@NotNull JoinKind o) {
    return visitPsiElement(o);
  }

  public R visitNumberLiteral(@NotNull NumberLiteral o) {
    return visitExpression(o);
  }

  public R visitOrderItem(@NotNull OrderItem o) {
    return visitPsiElement(o);
  }

  public R visitSelectStatement(@NotNull SelectStatement o) {
    return visitPsiElement(o);
  }

  public R visitSelectionItem(@NotNull SelectionItem o) {
    return visitPsiElement(o);
  }

  public R visitSelectionList(@NotNull SelectionList o) {
    return visitPsiElement(o);
  }

  public R visitSqlQuery(@NotNull SqlQuery o) {
    return visitPsiElement(o);
  }

  public R visitStringLiteral(@NotNull StringLiteral o) {
    return visitExpression(o);
  }

  public R visitSubquery(@NotNull Subquery o) {
    return visitPsiElement(o);
  }

  public R visitSubqueryTable(@NotNull SubqueryTable o) {
    return visitPsiElement(o);
  }

  public R visitTableDeclaration(@NotNull TableDeclaration o) {
    return visitPsiElement(o);
  }

  public R visitTopOpt(@NotNull TopOpt o) {
    return visitPsiElement(o);
  }

  public R visitPsiElement(@NotNull PsiElement o) {
    visitElement(o);
    return null;
  }

}
