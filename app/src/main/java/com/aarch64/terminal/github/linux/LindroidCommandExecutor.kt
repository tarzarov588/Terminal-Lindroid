package com.aarch64.terminal.github.linux

import android.content.Context
import java.io.File

class LindroidCommandExecutor(context: Context) {

    private val home = File(context.filesDir, "lindroid/home")

    init {
        home.mkdirs()
    }

    fun execute(command: String): String {
        if (command.isBlank()) {
            return ""
        }

        return try {
            val process = ProcessBuilder(
                "/system/bin/sh",
                "-c",
                command
            )
                .directory(home)
                .redirectErrorStream(true)
                .apply {
                    environment()["HOME"] = home.absolutePath
                    environment()["PWD"] = home.absolutePath
                    environment()["TERM"] = "xterm-256color"
                    environment()["PATH"] =
                        "/system/bin:/system/xbin"
                }
                .start()

            val output = process.inputStream
                .bufferedReader()
                .use { it.readText() }

            val exitCode = process.waitFor()

            buildString {
                append(output)

                if (exitCode != 0) {
                    if (output.isNotEmpty() && !output.endsWith("\n")) {
                        append("\n")
                    }

                    append("Exit code: ")
                    append(exitCode)
                }
            }
        } catch (e: Exception) {
            "Erro ao executar comando: ${e.message}"
        }
    }
}