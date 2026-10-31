package com.aarch64.terminal.github.linux

import com.termux.terminal.TerminalSession

class LindroidSessionManager(
    private val environment: LindroidEnvironment,
    private val client: LindroidTerminalClient
) {

    fun createShellSession(): TerminalSession {
        check(environment.initialize()) {
            "Não foi possível preparar o ambiente Lindroid."
        }

        val shell = environment.bash

        check(shell.exists() && shell.canExecute()) {
            "Bash não encontrado. Instale o Bash no ambiente Lindroid."
        }

        return TerminalSession(
            shell.absolutePath,
            environment.home.absolutePath,
            arrayOf("bash", "--login"),
            environment.getEnvironment(),
            null,
            client
        )
    }

    fun closeSession(session: TerminalSession) {
        if (session.isRunning) {
            session.finishIfRunning()
        }
    }
}