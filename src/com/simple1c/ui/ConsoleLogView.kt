package com.simple1c.ui

import com.intellij.execution.impl.ConsoleViewUtil
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.application.Application
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.editor.ex.DocumentEx
import com.intellij.openapi.editor.ex.EditorEx
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.project.ProjectManagerAdapter
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.openapi.wm.ToolWindow
import com.simple1c.execution.QueryListener
import com.simple1c.ui.Actions.MyActionIds

class ConsoleLogView(private val project: Project) {
    //need to get application, not project bus
    private val cancelQueryAction = ActionManager.getInstance().getAction(MyActionIds.cancelQuery)!!
    private val application = ApplicationManager.getApplication()
    private val messageBus = application.messageBus
    private val actionManager = ActionManager.getInstance()
    private val editor: EditorEx = ConsoleViewUtil.setupConsoleEditor(project, false, false)
            .apply {
                this.settings.isWhitespacesShown = false
                this.contextMenuGroupId = null
            }

    fun initContent(toolWindow: ToolWindow) {
        val panel = SimpleToolWindowPanel(false, true)
        val toolbar = actionManager.createActionToolbar(ActionPlaces.UNKNOWN, DefaultActionGroup(getActions()), false)
        toolbar.setTargetComponent(editor.contentComponent)
        panel.setToolbar(toolbar.component)
        panel.setContent(editor.component)
        val contentManager = toolWindow.contentManager
        contentManager.addContent(contentManager.factory.createContent(panel.component, "", true))
        messageBus.connect().subscribe(QueryListener.Topic, MyQueryListener(editor.document, application, toolWindow))
        project.messageBus.connect().subscribe(ProjectManager.TOPIC, object : ProjectManagerAdapter() {
            override fun projectClosed(project: Project?) {
                if (project === project) EditorFactory.getInstance().releaseEditor(editor)
            }
        })
    }

    private fun getActions(): List<AnAction> {
        return listOf(cancelQueryAction, ClearLogAction())
    }

    class MyQueryListener(private val document: DocumentEx,
                          private val application: Application,
                          private val toolWindow: ToolWindow) : QueryListener {

        override fun beginExecute(query: String) {
            appendLine("Executing query: $query")
        }

        override fun errorOccurred(exception: Exception) {
            appendLine("Error occurred: $exception")
        }

        override fun endExecute(columns: List<String>) {
            appendLine(columns.joinToString("|"))
            appendLine("_________")
        }

        override fun rowFetched(values: List<Any>) {
            appendLine(values.joinToString("|"))
        }

        override fun queryCancelled() {
            appendLine("Query cancelled")
        }


        private fun appendLine(string: String) {
            application.invokeLater {
                toolWindow.show(null)
                toolWindow.activate(null)
                document.insertString(document.textLength, string + "\n")
            }

        }

    }

    class ClearLogAction() : AnAction("1C: Clear log", "Clear the 1c console log", AllIcons.Actions.GC) {
        override fun actionPerformed(e: AnActionEvent?) {
            if (e == null)
                return
            val editor = e.getData(CommonDataKeys.EDITOR)!!
            editor.document.deleteString(0, editor.document.textLength)
        }

        override fun update(e: AnActionEvent?) {
            if (e == null)
                return
            val editor = e.getData(CommonDataKeys.EDITOR)
            e.presentation.isEnabled = editor != null && editor.document.textLength > 0
        }
    }

}
