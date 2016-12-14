package com.simple1c.remote

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.Disposable
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.Application
import com.intellij.openapi.components.ApplicationComponent
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.util.Disposer
import coreUtils.formatStacktrace
import coreUtils.readString
import org.apache.commons.lang.SystemUtils
import java.io.File
import java.io.IOException
import java.net.ServerSocket

class AnalysisHostProcess(private val application: Application) : ApplicationComponent {
    private val logger = Logger.getInstance(javaClass)
    private val retryAction = RetryAction(this)
    private val exeDir = "/Users/jetbrains/sources/simple-1c/bin/"
    private val executableName = "Simple1c.AnalysisHost.exe"
    private val currentNotifications = arrayListOf<Notification>()

    private val startingPort = 12345
    private var port: Int? = 12345

    fun getTransportOrNull(): HttpTransport? {
        if (port == null)
            return null

        return HttpTransport(port!!)
    }

    override fun getComponentName(): String {
        return AnalysisHostProcess::class.java.simpleName
    }

    override fun disposeComponent() {
    }

    override fun initComponent() {
        port = createProcess()
    }

    fun isAvailable(): Boolean {
        return port != null
    }

    private fun createProcess(): Int? {
        currentNotifications.forEach { it.expire() }
        var commandLine = emptyList<String>()
        try {
            val openPort = findPort()
            logger.info("Found open port $openPort")
            commandLine = getCommandLine(openPort)

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

            return openPort
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
            return null
        }
    }

    private fun getCommandLine(port: Int): List<String> {
        val executablePath = File(exeDir, executableName).absolutePath
        val commandLine = listOf(executablePath, "-port", port.toString())
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

    class RetryAction(private val analysisHostProcess: AnalysisHostProcess) : AnAction("Retry", "Some message", null) {
        override fun actionPerformed(e: AnActionEvent?) {
            if (e == null || e.project == null)
                return
            analysisHostProcess.createProcess()
        }
    }
}