package com.aarch64.terminal.github.linux

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ScrollView
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
            "Welcome to the Terminal-Lindroid app!\n" +
            "Type help to see available commands.\n" +
            "Our app is inspired by Termux!\n" +
            "You can also type help --complete to see an extended command list.\n\n"

        setupKeyboardButtons()
        setupCommandInput()
    }

    private fun setupCommandInput() {
        terminalInput.setOnEditorActionListener { _, actionId, event ->

            val enterPressed =
                actionId == EditorInfo.IME_ACTION_DONE ||
                (event?.keyCode == KeyEvent.KEYCODE_ENTER &&
                 event.action == KeyEvent.ACTION_DOWN)

            if (enterPressed) {
                val command = terminalInput.text.toString().trim()

                if (command.isNotEmpty()) {
                    terminalOutput.append("~ $ $command\n")
                    terminalInput.text.clear()
                    executeCommand(command)
                }

                true
            } else {
                false
            }
        }
    }

    private fun executeCommand(command: String) {
        when (command) {
            "help" -> {
                terminalOutput.append(
                    """
                    Available commands:

                    help          Show this help.
                    help --complete  Show extended command list.
                    pwd           Show current directory.
                    ls            List files.
                    echo          Display text.
                    clear         Clear terminal.

                    """.trimIndent() + "\n\n"
                )
            }

            "help --complete" -> {
                terminalOutput.append(
                    """
                    TERMINAL-LINDROID COMMAND CATALOG

                    FILES:
                    ls, cat, cp, mv, rm, mkdir, rmdir,
                    touch, chmod, find, pwd, basename,
                    dirname, stat, du, df

                    TEXT:
                    echo, printf, grep, sed, sort,
                    head, tail, wc, cut, tr, uniq

                    SHELL:
                    sh, cd, exit, export, unset,
                    read, test, true, false

                    SYSTEM:
                    getprop, logcat, pm, am,
                    settings, id, date, uname

                    NETWORK:
                    ping, toybox

                    PACKAGE MANAGEMENT:
                    pkg, apt, apt-get, dpkg

                    Note: Some commands require additional
                    packages or a compatible Linux environment.

                    """.trimIndent() + "\n\n"
                )
            }

            "clear" -> {
                terminalOutput.text = ""
            }

            else -> {
                executor.execute {
                    val result = commandExecutor.execute(command)

                    mainHandler.post {
                        if (result.isNotEmpty()) {
                            terminalOutput.append("$result\n")
                        }

                        scrollToBottom()
                    }
                }
            }
        }

        scrollToBottom()
    }

    private fun setupKeyboardButtons() {

        findViewById<Button>(R.id.keyEsc).setOnClickListener {
            insertText("\u001B")
        }

        findViewById<Button>(R.id.keyTab).setOnClickListener {
            insertText("\t")
        }

        findViewById<Button>(R.id.keyCtrl).setOnClickListener {
            it.isSelected = !it.isSelected
        }

        findViewById<Button>(R.id.keyAlt).setOnClickListener {
            it.isSelected = !it.isSelected
        }

        findViewById<Button>(R.id.keyUp).setOnClickListener {
            moveCursor(-1)
        }

        findViewById<Button>(R.id.keyDown).setOnClickListener {
            moveCursor(1)
        }

        findViewById<Button>(R.id.keyLeft).setOnClickListener {
            moveCursor(-1)
        }

        findViewById<Button>(R.id.keyRight).setOnClickListener {
            moveCursor(1)
        }
    }

    private fun insertText(text: String) {
        val start = terminalInput.selectionStart.coerceAtLeast(0)
        val end = terminalInput.selectionEnd.coerceAtLeast(0)

        terminalInput.text.replace(start, end, text)
    }

    private fun moveCursor(direction: Int) {
        val position = terminalInput.selectionStart
        val newPosition =
            (position + direction).coerceIn(0, terminalInput.text.length)

        terminalInput.setSelection(newPosition)
    }

    private fun scrollToBottom() {
        terminalOutput.post {
            val scrollView = terminalOutput.parent as? ScrollView
            scrollView?.fullScroll(View.FOCUS_DOWN)
        }
    }

    override fun onDestroy() {
        executor.shutdownNow()
        super.onDestroy()
    }
}