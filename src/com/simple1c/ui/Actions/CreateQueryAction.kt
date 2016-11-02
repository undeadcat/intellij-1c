package com.simple1c.ui.Actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class CreateQueryAction() : AnAction("1C: Create query") {
    override fun actionPerformed(e: AnActionEvent?) {
        print(e!!.dataContext)
    }

    override fun update(e: AnActionEvent?) {
        if (e == null)
            return
        e.presentation.isVisible = e.place == MyActionConstants.Places.DataSources
    }
}