package com.github.rexfilius.prioritynotes.addeditnote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import android.widget.NumberPicker
import com.github.rexfilius.prioritynotes.R

class AddEditNoteActivity : AppCompatActivity() {

    lateinit var editTextTitle: EditText
    lateinit var editTextDescription: EditText
    lateinit var numberPicker: NumberPicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)

        editTextTitle = findViewById(R.id.editTxtTitle)
        editTextDescription = findViewById(R.id.editTextDescription)
        numberPicker = findViewById(R.id.numPickerPriority)
        numberPicker.apply {
            minValue = 1
            maxValue = 10
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_add_edit_note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_save_note) {
            // saveNote()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}