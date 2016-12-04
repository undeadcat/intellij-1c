package com.simple1c.ui.Actions

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.ShortcutSet
import com.intellij.openapi.project.DumbAware
import com.intellij.psi.PsiElement
import com.intellij.psi.TokenType
import com.intellij.psi.util.PsiTreeUtil
import com.simple1c.boilerplate._1cFile
import com.simple1c.dataSources.DataSourceStorage
import com.simple1c.execution.QueryExecutor
import generated.GeneratedTypes
import generated.SqlQuery

class ExecuteQueryAction(val queryExecutor: QueryExecutor)
: AnAction("1C:Execute Query", "1C:Execute Query", AllIcons.General.Run), DumbAware {
    override fun setShortcutSet(shortcutSet: ShortcutSet?) {
        super.setShortcutSet(shortcutSet)
    }

    override fun actionPerformed(e: AnActionEvent?) {
        if (e == null)
            return
        val _1cFile = e.getData(CommonDataKeys.PSI_FILE) as _1cFile
        val editor = e.getData(CommonDataKeys.EDITOR)
        val query = findQueryAfter(_1cFile, editor!!.selectionModel.selectionStart)
        if (query == null)
            throw Exception("Assertion failed. Expected selection to be ${SqlQuery::class.simpleName} but was ${editor.selectionModel.selectedText}")
        val dataSourceStorage = DataSourceStorage.instance(e.project!!)
        queryExecutor.executeQuery(e.project!!, query.text, dataSourceStorage.getAll().first())
    }

    override fun update(e: AnActionEvent?) {
        if (e == null)
            return
        val psiFile = e.getData(CommonDataKeys.PSI_FILE)
        val editor = e.getData(CommonDataKeys.EDITOR)
        val _1cFile = psiFile as? _1cFile
        e.presentation.isVisible = _1cFile != null
        val isEnabled = if (_1cFile != null) {
            val startQuery = findQueryAfter(_1cFile, editor!!.selectionModel.selectionStart)
            val endQuery = findQueryBefore(_1cFile, editor.selectionModel.selectionEnd)
            startQuery != null
                    && endQuery != null
                    && startQuery == endQuery
                    && !queryExecutor.hasQueryInProgress()
        } else false

        e.presentation.isEnabled = isEnabled
    }

    private fun findQueryBefore(_1cFile: _1cFile, offset: Int): SqlQuery? {
        val theOffset = if (offset >= _1cFile.textLength - 1 && offset > 0) offset - 1 else offset
        return findSqlQuery(_1cFile, theOffset, { it.prevSibling })
    }

    private fun findQueryAfter(_1cFile: _1cFile, offset: Int) =
            findSqlQuery(_1cFile, offset, { it.nextSibling })

    fun findSqlQuery(file: _1cFile, offset: Int, getSibling: (PsiElement) -> PsiElement?): SqlQuery? {
        var element = file.findElementAt(offset)
        while (element != null && (element.node.elementType == GeneratedTypes.LINE_COMMENT
                || element.node.elementType == TokenType.WHITE_SPACE)) {
            element = getSibling(element)
        }
        return PsiTreeUtil.getParentOfType(element, SqlQuery::class.java, false)
    }

}