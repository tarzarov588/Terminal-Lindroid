package com.aarch64.terminal.github.linux

import android.app.Activity
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        )

        val terminal = TextView(this).apply {
            text = "Terminal-Lindroid\n"
            textSize = 14f
            typeface = android.graphics.Typeface.MONOSPACE
            setPadding(16, 16, 16, 16)
        }

        setContentView(terminal)
    }
}