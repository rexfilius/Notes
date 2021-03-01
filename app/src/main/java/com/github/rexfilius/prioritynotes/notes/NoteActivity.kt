package com.github.rexfilius.prioritynotes.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.rexfilius.prioritynotes.R

class NoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
    }
}