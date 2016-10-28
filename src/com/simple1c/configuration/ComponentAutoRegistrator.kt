package com.simple1c.configuration

import com.intellij.openapi.diagnostic.Logger
import org.picocontainer.MutablePicoContainer
import java.io.File
import java.lang.reflect.Modifier
import java.nio.file.Paths

class ComponentAutoRegistrator(val excludeClass: Class<*>) {
    val logger = Logger.getInstance(javaClass)
    fun autoRegister(root: File, container: MutablePicoContainer) {
        for (clazz in getClasses(root.absolutePath)
                .filter { it != excludeClass }
                .filter { !Modifier.isAbstract(it.modifiers) && !it.isInterface }) {
            val interfaces = clazz.interfaces + clazz
            val logMessage = "Registered class ${clazz.simpleName} as " +
                    interfaces.map { it.simpleName }.joinToString(",")
            logger.info(logMessage)
            container.registerComponentImplementation(clazz)
        }
    }

    fun getClasses(root: String): Iterable<Class<*>> {
        val classesRoot = Paths.get(root, "classes").toFile()
        val ourClassesRoot = Paths.get(classesRoot.absolutePath, "com", "simple1c").toFile()
        return getFilesOfType(ourClassesRoot, ".class")
                .map {
                    val relativePath = it.relativeTo(classesRoot)
                    val className = relativePath.toString().replace(File.separator, ".").removeSuffix(".class")
                    Class.forName(className)
                }
    }

    private fun getFilesOfType(root: File, extension: String): Iterable<File> {
        val files = root.listFiles() ?: emptyArray()
        return files
                .flatMap { getFilesOfType(it, extension) + it }
                .filter { it.name.endsWith(extension) }
    }
}