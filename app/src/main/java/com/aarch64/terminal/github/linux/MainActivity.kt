package com.aarch64.terminal.github.linux

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView

class MainActivity : Activity() {

    private lateinit var terminalOutput: TextView
    private lateinit var terminalInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        terminalOutput = findViewById(R.id.terminalOutput)
        terminalInput = findViewById(R.id.terminalInput)

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
                val command = terminalInput.text.toString()

                if (command.isNotBlank()) {
                    terminalOutput.append("\n$command\n")
                }

                terminalInput.text.clear()
                true
            } else {
                false
            }
        }
    }
}