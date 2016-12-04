package com.simple1c.ui.Actions

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindowManager
import com.simple1c.dataSources.DataSourceStorage
import com.simple1c.remote.AnalysisHostProcess
import com.simple1c.ui.EditDataSourceDialog
import com.simple1c.ui.MyToolWindowIds

class NewDataSourceAction(private val analysisHostProcess: AnalysisHostProcess)
: AnAction("1C: New Data Source", "1c: New Data Source", AllIcons.General.Add), DumbAware {
    override fun actionPerformed(event: AnActionEvent?) {
        if (event == null)
            return
        run(event.project!!)
    }

    fun run(project: Project) {
        val dataSourceStorage = DataSourceStorage.instance(project)
        val dialog = EditDataSourceDialog(project, dataSourceStorage.newTemplate(), analysisHostProcess)
        if (dialog.go()) {
            dataSourceStorage.addOrUpdate(dialog.dataSource)
            val toolWindow = ToolWindowManager.getInstance(project).getToolWindow(MyToolWindowIds.dataSources)
            toolWindow.show(null)
            toolWindow.activate(null)
        }
    }
}

