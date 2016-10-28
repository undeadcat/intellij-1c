package com.simple1c

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.simple1c.dataSources.DataSource
import com.simple1c.dataSources.DataSourceStorage
import com.simple1c.ui.EditDataSourceDialog

class NewDataSourceAction(val dataSourceStorage: DataSourceStorage)
: AnAction("1C: New Data Source", "1c: New Data Source", AllIcons.General.Add) {
    override fun actionPerformed(event: AnActionEvent?) {
        if (event == null) return
        val dialog = EditDataSourceDialog(event.project, DataSource("New Data source"))
        if (dialog.go())
            dataSourceStorage.addOrUpdate(dialog.dataSource)
    }

    override fun update(e: AnActionEvent?) {
        if (e == null)
            return
        e.presentation.isEnabled = true
        e.presentation.isVisible = true
    }

}