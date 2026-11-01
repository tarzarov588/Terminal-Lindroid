package com.aarch64.terminal.github.linux

import android.content.Context
import java.io.File

class LindroidEnvironment(context: Context) {

    val root = File(context.filesDir, "lindroid")
    val usr = File(root, "usr")
    val bin = File(usr, "bin")
    val lib = File(usr, "lib")
    val etc = File(usr, "etc")
    val varDir = File(root, "var")
    val tmp = File(root, "tmp")
    val home = File(root, "home")
    val downloads = File(home, "downloads")

    val bash = File(bin, "bash")

    fun initialize(): Boolean {
        val directories = listOf(
            root,
            usr,
            bin,
            lib,
            etc,
            varDir,
            tmp,
            home,
            downloads
        )

        return directories.all { directory ->
            directory.exists() || directory.mkdirs()
        }
    }

    fun getEnvironment(): Array<String> {
        return arrayOf(
            "HOME=${home.absolutePath}",
            "PREFIX=${usr.absolutePath}",
            "PATH=${bin.absolutePath}:/system/bin:/system/xbin",
            "TMPDIR=${tmp.absolutePath}",
            "LANG=C.UTF-8",
            "TERM=xterm-256color"
        )
    }
}