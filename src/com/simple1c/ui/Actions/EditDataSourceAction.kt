package com.simple1c.ui.Actions

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.simple1c.dataSources.DataSourceStorage
import com.simple1c.ui.DataSourcesToolWindow
import com.simple1c.ui.EditDataSourceDialog

class EditDataSourceAction(val dataSourceStorage: DataSourceStorage)
: AnAction("1C: Edit Data Source", "1c: Edit Data Source", AllIcons.Actions.Edit) {
    override fun actionPerformed(e: AnActionEvent?) {
        if (e == null)
            return
        val dataSource = e.getData(DataSourcesToolWindow.DataSourceKey)
        if (dataSource == null)
            throw Exception("DataSource was null")
        val dialog = EditDataSourceDialog(e.project, dataSource)
        if (dialog.go())
            dataSourceStorage.addOrUpdate(dialog.dataSource)
    }
}

class CreateQueryAction() : AnAction("1C: Create query") {
    override fun actionPerformed(e: AnActionEvent?) {
        print(e!!.dataContext)
    }

}