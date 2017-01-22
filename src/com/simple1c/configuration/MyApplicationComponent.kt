package com.simple1c.configuration

import com.intellij.openapi.application.Application
import com.intellij.openapi.components.ApplicationComponent
import com.intellij.openapi.diagnostic.Logger
import coreUtils.getComponentsOfType
import org.picocontainer.MutablePicoContainer

class MyApplicationComponent(val application: Application) : ApplicationComponent {
    private val logger = Logger.getInstance(javaClass)
    override fun initComponent() {
        val container = application.picoContainer as MutablePicoContainer
        ClassEnumerator(PluginId.get().path)
                .enumerate()
                .filter { it.getAnnotationsByType(ProjectService::class.java).isEmpty() }
                .forEach {
                    val interfaces = it.interfaces + it
                    val logMessage = "Application: Registering class ${it.simpleName} as " +
                            interfaces.map { it.simpleName }.joinToString(",")
                    logger.info(logMessage)
                    container.registerComponentImplementation(it)
                }
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


