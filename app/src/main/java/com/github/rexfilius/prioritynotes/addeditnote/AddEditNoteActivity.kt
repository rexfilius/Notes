package com.github.rexfilius.prioritynotes.addeditnote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Toast
import com.github.rexfilius.prioritynotes.R
import com.github.rexfilius.prioritynotes.notes.EXTRA_DESCRIPTION
import com.github.rexfilius.prioritynotes.notes.EXTRA_ID
import com.github.rexfilius.prioritynotes.notes.EXTRA_PRIORITY
import com.github.rexfilius.prioritynotes.notes.EXTRA_TITLE

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

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        val intent: Intent = intent
        if (intent.hasExtra(EXTRA_ID)) {
            title = "Edit Note"
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE))
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION))
            numberPicker.value = intent.getIntExtra(EXTRA_PRIORITY, 1)
        } else {
            title = "Add Note"
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_add_edit_note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_save_note) {
            saveNote()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveNote() {
        val title = editTextTitle.text.toString()
        val description = editTextDescription.text.toString()
        val priority = numberPicker.value

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(
                this, "Please insert a title and description",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val data = Intent().apply {
            putExtra(EXTRA_TITLE, title)
            putExtra(EXTRA_DESCRIPTION, description)
            putExtra(EXTRA_PRIORITY, priority)
        }

        val id = intent.getIntExtra(EXTRA_ID, -1)
        if (id != -1)
            data.putExtra(EXTRA_ID, id)

        setResult(RESULT_OK)
        finish()
    }
}