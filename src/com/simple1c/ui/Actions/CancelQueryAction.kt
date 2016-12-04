package com.simple1c.ui.Actions

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAware
import com.simple1c.execution.QueryExecutor

class CancelQueryAction(val queryExecutor: QueryExecutor)
: AnAction("1C:Cancel query",
        "Cancel the currently executing query", AllIcons.Process.Stop), DumbAware {
    override fun actionPerformed(e: AnActionEvent?) {
        queryExecutor.cancelQuery()
    }

    override fun update(e: AnActionEvent?) {
        if (e == null)
            return
        e.presentation.isEnabled = queryExecutor.isAvailable()
                && queryExecutor.hasQueryInProgress()

    }
}