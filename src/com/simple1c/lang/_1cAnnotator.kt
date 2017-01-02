package com.simple1c.lang

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.psi.PsiElement
import com.intellij.psi.util.*
import com.simple1c.remote.RemoteException
import generated.*

//todo. need to log exceptions to console.
class _1cAnnotator(private val pathEvaluator: PathEvaluator,
                   private val schemaStore: ISchemaStore) : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        try {
            element.accept(AnnotatingVisitor(holder, pathEvaluator, schemaStore))
        } catch (e: RemoteException) {

        }
    }

    class AnnotatingVisitor(private val annotationHolder: AnnotationHolder,
                            private val pathEvaluator: PathEvaluator,
                            private val schemaStore: ISchemaStore) : generated.Visitor() {
        override fun visitIdentifier(o: Identifier) {
            if (PsiTreeUtil.getParentOfType(o, Alias::class.java) != null)
                return
            val functionExpression = PsiTreeUtil.getParentOfType(o, QueryFunctionExpression::class.java)
            if (functionExpression != null && functionExpression.functionName == o)
                return
            val context = getContextOrNull(o)
            if (context == null)
                return
            val text = o.text
            if (PsiTreeUtil.getParentOfType(o, TableDeclaration::class.java) != null) {
                if (context.resolve(text) == null)
                    annotationHolder.createWarningAnnotation(o, "Could not resolve table by name '$text'")
            } else {
                val pathSegments = text.split(".").map(String::trim)
                if (pathEvaluator.eval(o.containingFile, context, pathSegments).isEmpty())
                    annotationHolder.createWarningAnnotation(o, "Could not resolve identifier by name $text")
            }
        }

        private fun getContextOrNull(identifier: Identifier): QueryContext? {
            val query = PsiTreeUtil.getParentOfType(identifier, SqlQuery::class.java)
            if (query == null)
                return null
            return CachedValuesManager.getCachedValue(query, {
                val context = QueryContext.createForQuery(query, { schemaStore.getSchemaOrNull(query.containingFile, it) })
                CachedValueProvider.Result(context, PsiModificationTracker.MODIFICATION_COUNT)
            })
        }

    }

}