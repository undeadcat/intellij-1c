package com.simple1c.ui.Actions

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.wm.ToolWindowManager
import com.simple1c.dataSources.DataSourceStorage
import com.simple1c.ui.EditDataSourceDialog
import com.simple1c.ui.MyToolWindowIds

class NewDataSourceAction()
: AnAction("1C: New Data Source", "1c: New Data Source", AllIcons.General.Add) {
    override fun actionPerformed(event: AnActionEvent?) {
        if (event == null)
            return
        val dataSourceStorage = DataSourceStorage.instance(event.project!!)
        val dialog = EditDataSourceDialog(event.project, dataSourceStorage.newTemplate())
        if (dialog.go()) {
            dataSourceStorage.addOrUpdate(dialog.dataSource)
            val toolWindow = ToolWindowManager.getInstance(event.project!!).getToolWindow(MyToolWindowIds.dataSources)
            toolWindow.show(null)
            toolWindow.activate(null)
        }
    }
}

