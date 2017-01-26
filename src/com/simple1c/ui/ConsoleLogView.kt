package com.simple1c.ui

import com.intellij.execution.impl.ConsoleViewUtil
import com.intellij.icons.AllIcons
import com.intellij.openapi.Disposable
import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.application.Application
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.AbstractProjectComponent
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.editor.ex.EditorEx
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowManager
import com.simple1c.configuration.ProjectService
import com.simple1c.execution.QueryListener
import com.simple1c.remote.QueryResult
import com.simple1c.ui.Actions.MyActionIds

private val ID = "1C: Console"

@ProjectService
class ConsoleLogView(private val project: Project) : Disposable {
    private val application = ApplicationManager.getApplication()
    private val actionManager = ActionManager.getInstance()
    private val cancelQueryAction = actionManager.getAction(MyActionIds.cancelQuery)!!
    private var editor: EditorEx? = null

    fun initContent(toolWindow: ToolWindow) {
        val panel = SimpleToolWindowPanel(false, true)
        val toolbar = actionManager.createActionToolbar(ActionPlaces.UNKNOWN, DefaultActionGroup(getActions()), false)
        val theEditor = ConsoleViewUtil.setupConsoleEditor(project, false, false)
                .apply {
                    this.settings.isWhitespacesShown = false
                    this.contextMenuGroupId = null
                }
        this.editor = theEditor

        toolbar.setTargetComponent(theEditor.contentComponent)
        panel.setToolbar(toolbar.component)
        panel.setContent(theEditor.component)
        val contentManager = toolWindow.contentManager
        contentManager.addContent(contentManager.factory.createContent(panel.component, "", true))
    }

    override fun dispose() {
        val myEditor = editor
        if (myEditor != null) {
            EditorFactory.getInstance().releaseEditor(myEditor)
        }
    }

    private fun getActions(): List<AnAction> {
        return listOf(cancelQueryAction, ClearLogAction())
    }

    private fun appendLine(string: String) {
        val myEditor = editor
        if (myEditor != null) {
            myEditor.document.insertString(myEditor.document.textLength, string.replace("\r\n", "\n") + "\n")
        }
    }

    class _1cQueryListener(private val project: Project,
                           private val application: Application,
                           private val consoleLogView: ConsoleLogView) : AbstractProjectComponent(project), QueryListener {

        //need to get application, not project bus
        private val messageBus = application.messageBus

        override fun initComponent() {
            super.initComponent()
            val queryConnection = messageBus.connect()
            queryConnection.subscribe(QueryListener.Topic, this)
            Disposer.register(project, queryConnection)
            Disposer.register(project, consoleLogView)
        }

        override fun log(query: String) {
            appendLine(query)
        }

        override fun endExecute(columns: List<QueryResult.Column>) {
            appendLine(columns.joinToString("|"))
            appendLine("_________")
        }

        override fun rowFetched(values: List<Any>) {
            appendLine(values.joinToString("|"))
        }

        override fun queryCancelled() {
            appendLine("Query cancelled")
        }

        override fun errorOccurred(message: String) {
            appendLine("Error occurred:\n" + message)
        }

        private fun appendLine(string: String) {
            val toolWindow = getToolWindow()
            application.invokeLater {
                toolWindow.show(null)
                toolWindow.activate(null)
                consoleLogView.appendLine(string)
            }
        }

        private fun getToolWindow() = ToolWindowManager.getInstance(project).getToolWindow(ID)!!
    }

    class ClearLogAction : AnAction("1C: Clear log", "Clear the 1c console log", AllIcons.Actions.GC) {
        override fun actionPerformed(e: AnActionEvent?) {
            if (e == null)
                return
            val editor = e.getData(CommonDataKeys.EDITOR)
            if (editor != null)
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
