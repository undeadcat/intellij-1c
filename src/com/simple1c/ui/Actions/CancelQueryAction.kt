package com.simple1c.ui.Actions

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.simple1c.execution.QueryExecutor

class CancelQueryAction(val queryExecutor: QueryExecutor)
: AnAction("1C:Cancel query",
        "Cancel the currently executing query", AllIcons.Process.Stop) {
    override fun actionPerformed(e: AnActionEvent?) {
        queryExecutor.cancelQuery()
    }

    override fun update(e: AnActionEvent?) {
        if (e == null)
            return
        e.presentation.isEnabled = queryExecutor.hasQueryInProgress()

    }
}