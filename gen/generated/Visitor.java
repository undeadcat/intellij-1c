// This is a generated file. Not intended for manual editing.
package generated;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;

public class Visitor extends PsiElementVisitor {

  public void visitAggregateExpression(@NotNull AggregateExpression o) {
    visitExpression(o);
  }

  public void visitAlias(@NotNull Alias o) {
    visit_1cElement(o);
  }

  public void visitBinaryExpression(@NotNull BinaryExpression o) {
    visitExpression(o);
  }

  public void visitBoolLiteral(@NotNull BoolLiteral o) {
    visitExpression(o);
  }

  public void visitCaseElement(@NotNull CaseElement o) {
    visit_1cElement(o);
  }

  public void visitCaseExpression(@NotNull CaseExpression o) {
    visitExpression(o);
  }

  public void visitColumnSource(@NotNull ColumnSource o) {
    visit_1cElement(o);
  }

  public void visitExpression(@NotNull Expression o) {
    visit_1cElement(o);
  }

  public void visitExpressionList(@NotNull ExpressionList o) {
    visit_1cElement(o);
  }

  public void visitIdentifier(@NotNull Identifier o) {
    visitExpression(o);
  }

  public void visitInExpression(@NotNull InExpression o) {
    visitExpression(o);
  }

  public void visitJoinItem(@NotNull JoinItem o) {
    visit_1cElement(o);
  }

  public void visitJoinKind(@NotNull JoinKind o) {
    visit_1cElement(o);
  }

  public void visitNullLiteral(@NotNull NullLiteral o) {
    visitExpression(o);
  }

  public void visitNumberLiteral(@NotNull NumberLiteral o) {
    visitExpression(o);
  }

  public void visitOrderItem(@NotNull OrderItem o) {
    visit_1cElement(o);
  }

  public void visitQueryFunctionExpression(@NotNull QueryFunctionExpression o) {
    visitExpression(o);
  }

  public void visitRootQuery(@NotNull RootQuery o) {
    visit_1cElement(o);
  }

  public void visitSelectStatement(@NotNull SelectStatement o) {
    visit_1cElement(o);
  }

  public void visitSelectionItem(@NotNull SelectionItem o) {
    visit_1cElement(o);
  }

  public void visitSelectionList(@NotNull SelectionList o) {
    visit_1cElement(o);
  }

  public void visitSqlQuery(@NotNull SqlQuery o) {
    visit_1cElement(o);
  }

  public void visitStringLiteral(@NotNull StringLiteral o) {
    visitExpression(o);
  }

  public void visitSubquery(@NotNull Subquery o) {
    visit_1cElement(o);
  }

  public void visitSubqueryTable(@NotNull SubqueryTable o) {
    visitColumnSource(o);
  }

  public void visitTableDeclaration(@NotNull TableDeclaration o) {
    visitColumnSource(o);
  }

  public void visitTopOpt(@NotNull TopOpt o) {
    visit_1cElement(o);
  }

  public void visitUnaryExpression(@NotNull UnaryExpression o) {
    visitExpression(o);
  }

  public void visitUnionClause(@NotNull UnionClause o) {
    visit_1cElement(o);
  }

  public void visit_1cElement(@NotNull _1cElement o) {
    visitElement(o);
  }

}
