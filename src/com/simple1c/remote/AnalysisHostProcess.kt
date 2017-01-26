package com.simple1c.remote

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.Disposable
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.Application
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.util.Disposer
import coreUtils.formatStacktrace
import coreUtils.readString
import org.apache.commons.lang.SystemUtils
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.net.ServerSocket
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption


//TODO. need to add pinger (http/psql) and notify on failure
class AnalysisHostProcess(private val application: Application) {
    private val macOsMonoPath = "/Library/Frameworks/Mono.framework/Versions/Current/bin/mono"
    private val requiredFiles = arrayListOf(
            "Simple1C.AnalysisHost",
            "Simple1C",
            "Irony",
            "Npgsql",
            "Newtonsoft.Json")
    private val extensions = arrayListOf("dll", "exe", "dll.mdb", "exe.mdb", "pdb", "dll")
    private val executableName = "Simple1C.AnalysisHost.exe"
    private val resourceDir = "Simple1C"
    private val logger = Logger.getInstance(javaClass)
    private val retryAction = RetryAction(this)
    private val currentNotifications = arrayListOf<Notification>()
    private val sync = Object()

    private val startingPort = 12345
    private var handle: Handle? = null

    fun getTransport(): HttpTransport {
        val myHandle = ensureInitialized()
        if (myHandle == null || myHandle.port == null)
            throw RuntimeException("AnalysisHostProcess is not initialized")

        return HttpTransport(myHandle.port)
    }

    fun isAvailable(): Boolean {
        val handle = ensureInitialized()
        return handle != null && handle.port != null
    }

    private fun ensureInitialized(): Handle? {
        if (handle != null)
            return handle
        return synchronized(sync, {
            val handle = createProcess()
            this.handle = handle
            handle
        })
    }

    private fun createProcess(): Handle? {
        currentNotifications.forEach { it.expire() }
        currentNotifications.clear()

        val tempFiles = copyResources()
        val executablePath = tempFiles.first { it.path.contains(executableName) }

        var commandLine = emptyList<String>()
        try {
            val openPort = findPort()
            logger.info("Found open port $openPort")
            commandLine = getCommandLine(openPort, executablePath.absolutePath)

            val process = ProcessBuilder()
                    .command(commandLine)
                    .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                    .redirectError(ProcessBuilder.Redirect.INHERIT)
                    .start()
            logger.info("Started analysis host process")
            Disposer.register(application, Disposable {
                try {
                    logger.info("Stopping process")
                    process.destroy()
                } catch (e: Exception) {
                    logger.warn("Error stopping process: ${e.formatStacktrace()}")
                }
            })
            Thread.sleep(750)
            if (!process.isAlive) {
                val output = readString(process.inputStream)
                val error = readString(process.errorStream)
                throw Exception("Process exited unexpectedly with exit code ${process.exitValue()}: $output $error")
            }

            return Handle(process, openPort)
        } catch (e: Exception) {
            val isMacOS = SystemUtils.IS_OS_MAC_OSX
            val macOSItem = "3) On Mac OS X, mono is not installed in path, and path value differs for GUI and non-GUI applications." +
                    "Make sure mono executable is in $macOsMonoPath"
            val stacktrace = e.formatStacktrace()
            val msg = """Error starting analysis host process.
Command line: ${commandLine.joinToString(" ")}
Ensure that
1) you have .NET or Mono installed
2) $executableName and dependencies exist in path $executablePath
${if (isMacOS) macOSItem else ""}

StackTrace: $stacktrace"""
            val notification = Notification("1C Plugin", "1C Plugin", msg, NotificationType.ERROR)
            notification.isImportant = true
            notification.addAction(retryAction)
            Notifications.Bus.notify(notification)
            currentNotifications.add(notification)
            return Handle(null, null)
        }
    }

    private fun copyResources(): List<File> {
        val tempDir = Files.createTempDirectory("simple1c-tempdir")
        tempDir.toFile().deleteOnExit()
        val candidates = requiredFiles.flatMap { fileName -> extensions.map { extension -> fileName + "." + extension } }
        return candidates.map {
            val stream = getResourceStreamOrNull(File(resourceDir, it).toString())
            if (stream == null) null
            else {
                val targetPath = Paths.get(tempDir.toString(), File(it).name)
                Files.copy(stream, targetPath, StandardCopyOption.REPLACE_EXISTING)
                stream.close()
                targetPath.toFile().deleteOnExit()
                targetPath.toFile()
            }
        }.filterNotNull()
    }

    private fun getResourceStreamOrNull(path: String): InputStream? {
        try {
            return AnalysisHostProcess::class.java.classLoader.getResourceAsStream(path)
        } catch (e: Exception) {
            logger.info("Could not load resource {$path}", e)
            return null
        }
    }

    private fun getCommandLine(port: Int, path: String): List<String> {
        val commandLine = listOf(path, "-port", port.toString())
        if (SystemUtils.IS_OS_WINDOWS)
            return commandLine
        if (SystemUtils.IS_OS_MAC_OSX)
            return listOf(macOsMonoPath) + commandLine
        return listOf("mono") + commandLine
    }

    private fun findPort(): Int {
        var port = startingPort
        while (port < 65000) {
            try {
                ServerSocket(port).use({ socket ->
                    return socket.localPort
                })
            } catch (e: IOException) {
                port++
            }
        }
        throw Exception("Could not find open port")
    }

    private class RetryAction(private val analysisHostProcess: AnalysisHostProcess)
        : AnAction("Retry", "Some message", null) {
        override fun actionPerformed(e: AnActionEvent?) {
            synchronized(analysisHostProcess.sync) {
                analysisHostProcess.handle = null
                analysisHostProcess.handle = analysisHostProcess.ensureInitialized()
            }
        }
    }

    private class Handle(val process: Process?, val port: Int?)
}