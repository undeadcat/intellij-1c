package com.simple1c.configuration

import com.intellij.openapi.actionSystem.ActionManager
import com.simple1c.NewDataSourceAction

class ActionsRegistration(val newDataSourceAction: NewDataSourceAction,
                          val actionManager: ActionManager) : ApplicationConfigurator {
    override fun configure() {
        actionManager.registerAction("1C.NewDataSource", newDataSourceAction)
    }

}