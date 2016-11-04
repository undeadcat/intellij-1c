package com.simple1c.configuration

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.simple1c.ui.ConsoleLogView
import com.simple1c.ui.DataSourcesToolWindow

class DataSourcesWindowRegistration : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val dataSourcesToolWindow = DataSourcesToolWindow(project)

        dataSourcesToolWindow.initContent(toolWindow)
    }
}

class ConsoleWindowRegistration : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val consoleLogView = ConsoleLogView(project)

        consoleLogView.initContent(toolWindow)
    }

}