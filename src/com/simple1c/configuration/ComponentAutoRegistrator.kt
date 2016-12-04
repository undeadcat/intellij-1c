package com.simple1c.configuration

import com.intellij.openapi.components.ApplicationComponent
import com.intellij.openapi.components.impl.ComponentManagerImpl
import com.intellij.openapi.diagnostic.Logger
import java.io.File
import java.lang.reflect.Modifier
import java.nio.file.Paths

//TODO. kill excludeTypes?
class ComponentAutoRegistrator(val root: File, val excludeType: Class<*>) {
    private var classes: Iterable<Class<*>>? = null
    val logger = Logger.getInstance(javaClass)
    fun autoRegister(description: String,
                     componentManager: ComponentManagerImpl, classFilter: (Class<*>) -> Boolean = { true }) {
        for (clazz in getClasses()
                .distinct()
                .filter(classFilter)
                .filter {
                    it != excludeType
                            && !Modifier.isAbstract(it.modifiers)
                            && !it.isInterface
                            && !ApplicationComponent::class.java.isAssignableFrom(it)
                            && it.getAnnotationsByType(ProjectService::class.java).isEmpty()
                }) {
            val interfaces = clazz.interfaces + clazz
            val logMessage = "$description: Registering class ${clazz.simpleName} as " +
                    interfaces.map { it.simpleName }.joinToString(",")
            logger.info(logMessage)
            componentManager.picoContainer.registerComponentImplementation(clazz)
        }
    }

    private fun getClasses(): Iterable<Class<*>> {
        var myClasses = classes
        if (myClasses != null) return myClasses
        myClasses = enumerateClasses()
        return myClasses
    }

    private fun enumerateClasses(): Iterable<Class<*>> {
        val classesRoot = Paths.get(root.absolutePath, "classes").toFile()
        val ourClassesRoot = Paths.get(classesRoot.absolutePath, "com", "simple1c").toFile()
        return getFilesOfType(ourClassesRoot, ".class")
                .flatMap {
                    val relativePath = it.relativeTo(classesRoot)
                    val className = relativePath.toString().replace(File.separator, ".").removeSuffix(".class")
                    val outerClass = Class.forName(className)
                    val result = outerClass.declaredClasses.toList() + outerClass
                    result
                }
    }

    private fun getFilesOfType(root: File, extension: String): Iterable<File> {
        val files = root.listFiles() ?: emptyArray()
        return files
                .flatMap { getFilesOfType(it, extension) + it }
                .filter { it.name.endsWith(extension) }
    }
}