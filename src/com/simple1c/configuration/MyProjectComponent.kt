package com.simple1c.configuration

import com.intellij.openapi.components.AbstractProjectComponent
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import org.picocontainer.MutablePicoContainer

class MyProjectComponent(val project: Project) : AbstractProjectComponent(project) {
    private val logger = Logger.getInstance(javaClass)
    override fun initComponent() {

        val picoContainer = project.picoContainer as MutablePicoContainer
        ClassEnumerator(PluginId.get().path)
                .enumerate()
                .filter { it.getAnnotationsByType(ProjectService::class.java).isNotEmpty() }
                .forEach {
                    val interfaces = it.interfaces + it
                    val logMessage = "Project: Registering class ${it.simpleName} as " +
                            interfaces.map { it.simpleName }.joinToString(",")
                    logger.info(logMessage)
                    /*
                    @Nullable
  private static <T> T doGetService(ComponentManager componentManager, @NotNull Class<T> serviceClass) {
    PicoContainer picoContainer = componentManager.getPicoContainer();
    @SuppressWarnings("unchecked") T instance = (T)picoContainer.getComponentInstance(serviceClass.getName());
                    * */
                    picoContainer.registerComponentImplementation(it.name, it)
                }
    }
}