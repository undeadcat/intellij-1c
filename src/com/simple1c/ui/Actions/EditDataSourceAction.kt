package com.simple1c.ui.Actions

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.simple1c.dataSources.DataSourceStorage
import com.simple1c.ui.DataSourcesToolWindow
import com.simple1c.ui.EditDataSourceDialog

//TODO. Make it not an action? This is used only in one local popup
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

    override fun update(e: AnActionEvent?) {
        if (e == null)
            return
        e.presentation.isVisible = e.place == MyActionConstants.Places.DataSources
    }
}

