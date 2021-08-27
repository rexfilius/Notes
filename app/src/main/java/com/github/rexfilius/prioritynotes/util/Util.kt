package com.github.rexfilius.prioritynotes.util

import android.content.Context
import android.widget.Toast

object Constants {
    const val DELETE_NOTE = "Notes deleted"
    const val SAVE_NOTE = "Note saved"
    const val UPDATE_NOTE = "Note updated"
}

fun Any.toast(context: Context, duration: Int = Toast.LENGTH_LONG): Toast {
    return Toast.makeText(context, this.toString(), duration).apply { show() }
}