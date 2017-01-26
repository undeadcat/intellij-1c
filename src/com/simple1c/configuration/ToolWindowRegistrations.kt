package com.simple1c.configuration

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.simple1c.dataSources.DataSourceStorage
import com.simple1c.remote.AnalysisHostProcess
import com.simple1c.ui.Actions.CreateQueryAction
import com.simple1c.ui.ConsoleLogView
import com.simple1c.ui.DataSourcesToolWindow
import com.simple1c.ui.EditDataSourceDialog

class DataSourcesWindowRegistration(val analysisHostProcess: AnalysisHostProcess) : ToolWindowFactory, DumbAware {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val dataSourceStorage = DataSourceStorage.instance(project)
        val actions = listOf(EditDataSourceAction(analysisHostProcess),
                CreateQueryAction(),
                DeleteDataSourceAction(dataSourceStorage))
        val dataSourcesToolWindow = DataSourcesToolWindow(project, actions)
        dataSourcesToolWindow.initContent(toolWindow)
    }

    class DeleteDataSourceAction(val dataSourceStorage: DataSourceStorage)
    : AnAction("1C: Delete data source", "Delete this data source", AllIcons.Actions.Delete) {
        override fun actionPerformed(e: AnActionEvent?) {
            if (e == null)
                return
            val dataSource = e.getData(DataSourcesToolWindow.DataSourceKey)
            dataSourceStorage.delete(dataSource!!)
        }

    }

    class EditDataSourceAction(val analysisHostProcess: AnalysisHostProcess)
    : AnAction("1C: Edit Data Source", "Edit this data source", AllIcons.Actions.Edit) {
        override fun actionPerformed(e: AnActionEvent?) {
            if (e == null)
                return
            val dataSource = e.getData(DataSourcesToolWindow.DataSourceKey)
            val dialog = EditDataSourceDialog(e.project, dataSource!!, analysisHostProcess)
            if (dialog.go())
                DataSourceStorage.instance(e.project!!).addOrUpdate(dialog.dataSource)
        }
    }

}

class ConsoleWindowRegistration : ToolWindowFactory, DumbAware {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        //oh fuck me with an xml config
        ServiceManager.getService(project, ConsoleLogView::class.java).initContent(toolWindow)
    }
}