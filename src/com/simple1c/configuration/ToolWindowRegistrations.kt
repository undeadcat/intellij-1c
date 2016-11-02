package com.simple1c.configuration

import com.intellij.openapi.application.Application
import com.intellij.openapi.components.AbstractProjectComponent
import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindowAnchor
import com.intellij.openapi.wm.ToolWindowManager
import com.simple1c.dataSources.DataSourceStorage
import com.simple1c.ui.ConsoleLogView
import com.simple1c.ui.DataSourcesToolWindow
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

    class DataSourcesWindowRegistration(val dataSourceStorage: DataSourceStorage,
                                        val dataSourcesToolWindow: DataSourcesToolWindow,
                                        project: Project)
    : AbstractProjectComponent(project), ToolWindowRegistration {
        override fun register(toolWindowManager: ToolWindowManager) {
            val toolWindow = toolWindowManager.registerToolWindow("1C: Data Sources", false, ToolWindowAnchor.RIGHT, true)
            val contentManager = toolWindow.contentManager

            contentManager.addContent(contentManager.factory.createContent(dataSourcesToolWindow.createContent(), "", true))
        }
    }

    class ConsoleWindowRegistration(project: Project, val consoleLogView: ConsoleLogView) : AbstractProjectComponent(project), ToolWindowRegistration {
        override fun register(toolWindowManager: ToolWindowManager) {
            val toolWindow = toolWindowManager.registerToolWindow("1C: Console", false, ToolWindowAnchor.BOTTOM, true)
            consoleLogView.initContent(toolWindow)
        }
    }
}

