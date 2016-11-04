package com.simple1c.configuration

import com.intellij.openapi.application.Application
import com.intellij.openapi.components.ApplicationComponent
import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.components.impl.ComponentManagerImpl
import com.intellij.openapi.diagnostic.Logger
import coreUtils.getComponentsOfType
import org.picocontainer.MutablePicoContainer

class ApplicationComponent(val application: Application) : ApplicationComponent {
    private val selfClass = javaClass
    private val logger = Logger.getInstance(selfClass)
    override fun initComponent() {
        val container = application.picoContainer as MutablePicoContainer
        val autoRegistrator = ComponentAutoRegistrator(PluginId.get().path, selfClass)
        autoRegistrator.autoRegister("Application", application as ComponentManagerImpl, {
            !ProjectComponent::class.java.isAssignableFrom(it)
                    && it != ComponentAutoRegistrator::class.java
        })

        container.registerComponentInstance(autoRegistrator)
        for (configurator in container.getComponentsOfType(ApplicationConfigurator::class.java)) {
            logger.info("Running configurator $configurator")
            configurator.configure(container)
        }
    }

    override fun disposeComponent() {
    }

    override fun getComponentName(): String {
        return "ApplicationComponent"
    }
}


