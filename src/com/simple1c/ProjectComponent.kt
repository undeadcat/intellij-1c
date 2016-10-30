package com.simple1c

import com.intellij.openapi.application.Application
import com.intellij.openapi.components.AbstractProjectComponent
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindowAnchor
import com.intellij.openapi.wm.ToolWindowManager
import com.simple1c.dataSources.DataSourceStorage
import com.simple1c.ui.DataSourcesToolWindow

class ProjectComponent(val toolWindowManager: ToolWindowManager,
                       project: Project,
                       val dataSourceStorage: DataSourceStorage,
                       val application: Application) : AbstractProjectComponent(project) {

    override fun projectOpened() {
        application.invokeLater { registerToolWindow() }
    }

    private fun registerToolWindow() {
        val toolWindow = toolWindowManager.registerToolWindow("1C: Data Sources", true, ToolWindowAnchor.RIGHT, true)
        val contentManager = toolWindow.contentManager

        val content = DataSourcesToolWindow(dataSourceStorage)
        content.initContent()
        contentManager.addContent(contentManager.factory.createContent(content, "", true))
    }
}

