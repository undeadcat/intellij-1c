package com.simple1c.lang

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.util.*
import com.simple1c.remote.RemoteException
import generated.*

class _1cAnnotator(private val schemaStore: ISchemaStore) : Annotator {
    private val pathEvaluator = PathEvaluator.exact(schemaStore)
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        try {
            element.accept(AnnotatingVisitor(holder, pathEvaluator, schemaStore))
        } catch (e: RemoteException) {
            //todo. need to log exceptions to console.
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
                val prefixes = pathSegments.withIndex()
                        .map { pathSegments.take(it.index + 1) }
                val failed = prefixes
                        .asSequence()
                        .firstOrNull { pathEvaluator.eval(o.containingFile, context, it).isEmpty() }
                if (failed != null) {
                    val validPrefix = failed.take(failed.size - 1).joinToString(".")
                    val unresolved = pathSegments.takeLast(pathSegments.size - (failed.size - 1)).joinToString(".")
                    val startOffset = o.textOffset + validPrefix.length + (if (validPrefix.isNotEmpty()) 1 else 0)
                    val highlightRange = TextRange(startOffset, startOffset + unresolved.length)
                    annotationHolder.createWarningAnnotation(highlightRange, "Could not resolve identifier '$unresolved'")
                }
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