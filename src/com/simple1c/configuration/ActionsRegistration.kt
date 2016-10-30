package com.simple1c.configuration

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.simple1c.ui.Actions.CreateQueryAction
import com.simple1c.ui.Actions.EditDataSourceAction
import com.simple1c.ui.Actions.MyActionConstants
import com.simple1c.ui.Actions.NewDataSourceAction
import org.picocontainer.PicoContainer

class ActionsRegistration(val actionManager: ActionManager) : ApplicationConfigurator {
    override fun configure(container: PicoContainer) {
        registerAction(container, "1C.NewDataSource", NewDataSourceAction::class.java, MyActionConstants.Groups.DataSourcesToolbar)
        registerAction(container, "1C.EditDataSource", EditDataSourceAction::class.java, MyActionConstants.Groups.DataSources)
        registerAction(container, "1C.CreateQuery", CreateQueryAction::class.java, MyActionConstants.Groups.DataSources)
    }

    private fun registerAction(container: PicoContainer, name: String, clazz: Class<*>, vararg groups: String) {
        val theAction = container.getComponentInstanceOfType(clazz) as AnAction
        actionManager.registerAction(name, theAction)
        for (groupName in groups)
            getOrCreateGroup(groupName).add(theAction)
    }

    private fun getOrCreateGroup(groupName: String): DefaultActionGroup {
        val existing = actionManager.getAction(groupName)
        if (existing == null) {
            val group = DefaultActionGroup()
            actionManager.registerAction(groupName, group)
            return group
        }
        if (existing !is DefaultActionGroup)
            throw Exception("Expected action group by name $groupName but was $existing")
        return existing as DefaultActionGroup
    }

}