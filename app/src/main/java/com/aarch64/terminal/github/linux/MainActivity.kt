package com.aarch64.terminal.github.linux

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import java.util.concurrent.Executors

class MainActivity : Activity() {

    private lateinit var terminalOutput: TextView
    private lateinit var terminalInput: EditText
    private lateinit var commandExecutor: LindroidCommandExecutor

    private val executor = Executors.newSingleThreadExecutor()
    private val mainHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        terminalOutput = findViewById(R.id.terminalOutput)
        terminalInput = findViewById(R.id.terminalInput)

        commandExecutor = LindroidCommandExecutor(this)

        terminalOutput.text =
            "Terminal-Lindroid 3.8571.591_aarch64\n" +
            "GNU Bash 5.3.9\n\n" +
            "Welcome to Terminal-Lindroid!\n\n"

        terminalInput.setOnEditorActionListener { _, actionId, event ->

            val enterPressed =
                actionId == EditorInfo.IME_ACTION_DONE ||
                (event?.keyCode == KeyEvent.KEYCODE_ENTER &&
                 event.action == KeyEvent.ACTION_DOWN)

            if (enterPressed) {
                val command = terminalInput.text.toString().trim()

                if (command.isNotEmpty()) {
                    terminalOutput.append("$ $command\n")
                    terminalInput.text.clear()

                    executor.execute {
                        val result = commandExecutor.execute(command)

                        mainHandler.post {
                            if (result.isNotEmpty()) {
                                terminalOutput.append("$result\n")
                            }

                            terminalOutput.append("\n")
                        }
                    }
                }

                true
            } else {
                false
            }
        }
    }

    override fun onDestroy() {
        executor.shutdownNow()
        super.onDestroy()
    }
}