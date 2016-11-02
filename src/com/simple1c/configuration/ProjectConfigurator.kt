package com.simple1c.configuration

import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.project.Project

interface ProjectConfigurator : ProjectComponent {
    fun run(project: Project)
}