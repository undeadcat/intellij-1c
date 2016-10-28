package com.simple1c.configuration

import com.intellij.ide.plugins.IdeaPluginDescriptor
import com.intellij.ide.plugins.PluginManager
import com.intellij.openapi.extensions.PluginId

object PluginId {

    private var plugin: IdeaPluginDescriptor? = null

    fun get(): IdeaPluginDescriptor {
        val myPlugin = plugin
        if (myPlugin == null) {
            val id = "com.simple1c.unique.plugin.id"
            val result = PluginManager.getPlugin(com.intellij.openapi.extensions.PluginId.getId(id))
            if (result == null)
                throw Exception("Could not find plugin by id $id. Maybe plugin id changed?")
            plugin = result
            return result
        }
        return myPlugin
    }
}
