package com.simple1c.configuration

import com.intellij.openapi.components.BaseComponent
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.diagnostic.Logger
import coreUtils.equalsIgnoreCase
import java.io.File
import java.io.FileInputStream
import java.lang.reflect.Modifier
import java.util.*
import java.util.zip.ZipInputStream

class ClassEnumerator(val root: File) {
    private val logger = Logger.getInstance(javaClass)
    private val ourNamespace = "com/simple1c"
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
        val classesDir = File(root.absolutePath, "classes")
        val classesRoot = if (classesDir.exists()) classesDir else root
        logger.info("Class root: ${classesRoot.absoluteFile}")

        val files: List<String> =
                if (classesRoot.isFile && classesRoot.extension.equalsIgnoreCase("jar")) {
                    loadFilesFromJar(classesRoot)
                } else {
                    loadFilesFromDir(classesRoot).map { it.relativeTo(classesRoot).toString() }
                }
        logger.info("Files loaded " + files.size)

        return files.flatMap {
            if (!it.startsWith(ourNamespace) || !it.endsWith(".class"))
                emptyList<Class<*>>()
            else {
                val className = it.replace(File.separator, ".").removeSuffix(".class")
                val outerClass = Class.forName(className)
                outerClass.declaredClasses.toList() + outerClass
            }
        }
    }

    private fun loadFilesFromJar(path: File): List<String> {
        val result = ArrayList<String>()
        ZipInputStream(FileInputStream(path))
                .use { zip ->
                    var entry = zip.nextEntry
                    while (entry != null) {
                        if (!entry.isDirectory) {
                            result.add(entry.name)
                        }
                        entry = zip.nextEntry
                    }
                }
        return result
    }

    private fun loadFilesFromDir(path: File): List<File> {
        val files = path.listFiles() ?: emptyArray()
        return files
                .flatMap { loadFilesFromDir(it) + it }
    }


    companion object {
        private var classes: Iterable<Class<*>>? = null

    }
}