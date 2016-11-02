package com.simple1c.ui.Actions

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.psi.util.PsiTreeUtil
import com.simple1c.boilerplate._1cFile
import com.simple1c.execution.QueryExecutor
import generated.SqlQuery

class ExecuteQueryAction(val queryExecutor: QueryExecutor) : AnAction("1C:Execute Query", "1C:Execute Query", AllIcons.General.Run) {
    override fun actionPerformed(e: AnActionEvent?) {
        if (e == null)
            return
        val _1cFile = e.getData(CommonDataKeys.PSI_FILE) as _1cFile
        val editor = e.getData(CommonDataKeys.EDITOR)
        val query = findSqlQueryAtOffset(_1cFile, editor!!.selectionModel.selectionStart)
        if (query == null)
            throw Exception("Assertion failed. Expected selection to be ${SqlQuery::class.simpleName} but was ${editor.selectionModel.selectedText}")
        queryExecutor.executeQuery(query.text)
    }

    override fun update(e: AnActionEvent?) {
        if (e == null)
            return
        val psiFile = e.getData(CommonDataKeys.PSI_FILE)
        val editor = e.getData(CommonDataKeys.EDITOR)
        val _1cFile = psiFile as? _1cFile
        e.presentation.isVisible = _1cFile != null
        val isEnabled = if (_1cFile != null) {
            val startQuery = findSqlQueryAtOffset(_1cFile, editor!!.selectionModel.selectionStart)
            val endQuery = findSqlQueryAtOffset(_1cFile, editor.selectionModel.selectionEnd)
            startQuery != null && endQuery != null && startQuery == endQuery
        } else false

        e.presentation.isEnabled = isEnabled
    }

    fun findSqlQueryAtOffset(file: _1cFile, offset: Int): SqlQuery? {
        fun find(offset: Int): SqlQuery? =
                PsiTreeUtil.getParentOfType(file.findElementAt(offset), SqlQuery::class.java)

        val element = find(offset)
        if (element == null && offset > 0)
            return find(offset - 1)
        return element
    }

}