package com.simple1c.configuration

import com.intellij.openapi.application.Application
import com.intellij.openapi.components.AbstractProjectComponent
import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import coreUtils.getComponentsOfType
import org.picocontainer.MutablePicoContainer

class MyProjectComponent(
        val application: Application,
        val project: Project,
        val componentAutoRegistrator: ComponentAutoRegistrator) : AbstractProjectComponent(project) {

    private val logger = Logger.getInstance(javaClass)
    override fun projectOpened() {
        componentAutoRegistrator.autoRegister(project.picoContainer as MutablePicoContainer,
                {
                    ProjectComponent::class.java.isAssignableFrom(it)
                            && it != MyProjectComponent::class.java
                })
        for (it in project.picoContainer.getComponentsOfType(ProjectConfigurator::class.java)) {
            logger.info("Running configurator ${it.javaClass.simpleName}")
            it.run(project)
        }
    }

}

