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
import java.net.ServerSocket
import java.nio.file.Files

//TODO. need to add pinger (http/psql) and notify on failure
class AnalysisHostProcess(private val application: Application) {
    private val logger = Logger.getInstance(javaClass)
    private val retryAction = RetryAction(this)
    private val executableName = "Simple1C.AnalysisHost.exe"
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
        return ensureInitialized() != null
    }

    private fun ensureInitialized(): Handle? {
        if (handle != null)
            return handle
        return synchronized(sync, {
            val handle = createProcess()
            this.handle = handle
            return handle
        })
    }

    private fun createProcess(): Handle? {
        currentNotifications.forEach { it.expire() }
        currentNotifications.clear()

        val classLoader = AnalysisHostProcess::class.java.classLoader
        val resources = File(classLoader.getResource("Simple1C").path).list()
        val tempDir = Files.createTempDirectory("simple1c-tempdir")
        tempDir.toFile().deleteOnExit()
        val tempFiles = resources.map { resource ->
            val sourceFile = File(classLoader.getResource("Simple1C/" + resource).path)
            val tempFile = sourceFile.copyTo(File(tempDir.toAbsolutePath().toString(), sourceFile.name))
            tempFile.deleteOnExit()
            tempFile
        }

        var commandLine = emptyList<String>()
        try {
            val openPort = findPort()
            logger.info("Found open port $openPort")
            val executablePath = tempFiles.first { it.path.contains(executableName) }
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
            val stacktrace = e.formatStacktrace()
            val msg = """Error starting analysis host process.
Command line: ${commandLine.joinToString(" ")}
Ensure that
1) you have .NET or Mono installed
2) path to $executableName is correct

StackTrace: $stacktrace"""
            val notification = Notification("1C Plugin", "1C Plugin", msg, NotificationType.ERROR)
            notification.isImportant = true
            notification.addAction(retryAction)
            Notifications.Bus.notify(notification)
            currentNotifications.add(notification)
            return Handle(null, null)
        }
    }

    private fun getCommandLine(port: Int, path: String): List<String> {
        val commandLine = listOf(path, "-port", port.toString())
        if (SystemUtils.IS_OS_WINDOWS)
            return commandLine
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
            analysisHostProcess.handle = analysisHostProcess.ensureInitialized()
        }
    }

    private class Handle(val process: Process?, val port: Int?)
}