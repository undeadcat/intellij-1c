package com.simple1c.configuration

import com.intellij.openapi.components.BaseComponent
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.diagnostic.Logger
import java.io.File
import java.lang.reflect.Modifier
import java.nio.file.Paths

class ClassEnumerator(val root: File) {
    val logger = Logger.getInstance(javaClass)
    fun enumerate(): List<Class<*>> {
        return getClasses()
                .distinct()
                .filter {
                    !Modifier.isAbstract(it.modifiers)
                            && !it.isInterface
                            && it != ClassEnumerator::class.java
                            && !PersistentStateComponent::class.java.isAssignableFrom(it)
                            && !BaseComponent::class.java.isAssignableFrom(it)

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

    companion object {
        private var classes: Iterable<Class<*>>? = null

    }
}