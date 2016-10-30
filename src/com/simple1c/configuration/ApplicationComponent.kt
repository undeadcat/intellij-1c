package com.simple1c.configuration

import com.intellij.openapi.application.Application
import com.intellij.openapi.components.ApplicationComponent
import com.intellij.openapi.diagnostic.Logger
import org.picocontainer.MutablePicoContainer

class ApplicationComponent(val application: Application) : ApplicationComponent {
    private val logger = Logger.getInstance(javaClass)
    override fun initComponent() {
        val container = application.picoContainer as MutablePicoContainer
        val autoRegistrator = ComponentAutoRegistrator(javaClass)
        autoRegistrator.autoRegister(PluginId.get().path, container)

        for (configurator in container.getComponentInstancesOfType(ApplicationConfigurator::class.java)) {
            logger.info("Running configurator ${configurator.toString()}")
            (configurator as ApplicationConfigurator).configure(container)
        }
    }

    override fun disposeComponent() {
    }

    override fun getComponentName(): String {
        return "ApplicationComponent"
    }
}


