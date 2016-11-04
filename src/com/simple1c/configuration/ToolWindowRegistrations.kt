package com.simple1c.configuration

import com.intellij.openapi.application.Application
import com.intellij.openapi.components.AbstractProjectComponent
import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindowAnchor
import com.intellij.openapi.wm.ToolWindowManager
import com.simple1c.ui.Actions.CreateQueryAction
import com.simple1c.ui.Actions.EditDataSourceAction
import com.simple1c.ui.ConsoleLogView
import com.simple1c.ui.DataSourcesToolWindow
import com.simple1c.ui.MyToolWindowIds
import coreUtils.getComponentsOfType

class ToolWindowRegistrations(val toolWindowManager: ToolWindowManager,
                              val application: Application, val project: Project)
: AbstractProjectComponent(project), ProjectConfigurator {

    override fun run(project: Project) {
        for (registration in project.picoContainer.getComponentsOfType(ToolWindowRegistration::class.java)) {
            application.invokeLater {
                registration.register(toolWindowManager)
            }
        }
    }

    interface ToolWindowRegistration : ProjectComponent {
        fun register(toolWindowManager: ToolWindowManager)
    }

    class DataSourcesWindowRegistration(val project: Project,
                                        val editDataSourceAction: EditDataSourceAction,
                                        val createQueryAction: CreateQueryAction)
    : AbstractProjectComponent(project), ToolWindowRegistration {
        override fun register(toolWindowManager: ToolWindowManager) {
            val toolWindow = toolWindowManager.registerToolWindow(MyToolWindowIds.dataSources, false, ToolWindowAnchor.RIGHT, true)
            val contentManager = toolWindow.contentManager

            val dataSourcesToolWindow = DataSourcesToolWindow(project, listOf(editDataSourceAction, createQueryAction))
            contentManager.addContent(contentManager.factory.createContent(dataSourcesToolWindow.createContent(), "", true))
        }
    }

    class ConsoleWindowRegistration(project: Project, val consoleLogView: ConsoleLogView) : AbstractProjectComponent(project), ToolWindowRegistration {
        override fun register(toolWindowManager: ToolWindowManager) {
            val toolWindow = toolWindowManager.registerToolWindow(MyToolWindowIds.console, false, ToolWindowAnchor.BOTTOM, true)
            consoleLogView.initContent(toolWindow)
        }
    }
}

