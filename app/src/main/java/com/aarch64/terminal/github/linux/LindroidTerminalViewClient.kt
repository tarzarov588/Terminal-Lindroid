package com.aarch64.terminal.github.linux

import android.view.KeyEvent
import android.view.MotionEvent
import com.termux.terminal.TerminalSession
import com.termux.view.TerminalViewClient

class LindroidTerminalViewClient : TerminalViewClient {

    override fun onScale(scale: Float): Float {
        return scale
    }

    override fun onSingleTapUp(e: MotionEvent) {
    }

    override fun shouldBackButtonBeMappedToEscape(): Boolean {
        return false
    }

    override fun shouldEnforceCharBasedInput(): Boolean {
        return false
    }

    override fun shouldUseCtrlSpaceWorkaround(): Boolean {
        return false
    }

    override fun isTerminalViewSelected(): Boolean {
        return true
    }

    override fun copyModeChanged(copyMode: Boolean) {
    }

    override fun onKeyDown(
        keyCode: Int,
        e: KeyEvent,
        session: TerminalSession
    ): Boolean {
        return false
    }

    override fun onKeyUp(
        keyCode: Int,
        e: KeyEvent
    ): Boolean {
        return false
    }

    override fun onLongPress(event: MotionEvent): Boolean {
        return false
    }

    override fun readControlKey(): Boolean = false

    override fun readAltKey(): Boolean = false

    override fun readShiftKey(): Boolean = false

    override fun readFnKey(): Boolean = false

    override fun onCodePoint(
        codePoint: Int,
        ctrlDown: Boolean,
        session: TerminalSession
    ): Boolean {
        return false
    }

    override fun onEmulatorSet() {
    }

    override fun logError(tag: String, message: String) {
        android.util.Log.e(tag, message)
    }

    override fun logWarn(tag: String, message: String) {
        android.util.Log.w(tag, message)
    }

    override fun logInfo(tag: String, message: String) {
        android.util.Log.i(tag, message)
    }

    override fun logDebug(tag: String, message: String) {
        android.util.Log.d(tag, message)
    }

    override fun logVerbose(tag: String, message: String) {
        android.util.Log.v(tag, message)
    }

    override fun logStackTraceWithMessage(
        tag: String,
        message: String,
        e: Exception
    ) {
        android.util.Log.e(tag, message, e)
    }

    override fun logStackTrace(
        tag: String,
        e: Exception
    ) {
        android.util.Log.e(tag, "Erro", e)
    }
}