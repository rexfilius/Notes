package com.github.rexfilius.prioritynotes.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.rexfilius.prioritynotes.R
import com.github.rexfilius.prioritynotes.addeditnote.AddEditNoteActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NoteActivity : AppCompatActivity() {

    private val viewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        val buttonAddNote: FloatingActionButton = findViewById(R.id.btnAddNote)
        buttonAddNote.setOnClickListener {
            val intent = Intent(this, AddEditNoteActivity::class.java)
            startActivity(intent)
        }

        val noteRecyclerView: RecyclerView = findViewById(R.id.noteRecyclerView)
        val noteAdapter = NoteAdapter()
        noteRecyclerView.apply {
            adapter = noteAdapter
            layoutManager = LinearLayoutManager(this@NoteActivity)
            hasFixedSize()
        }

        viewModel.getAllNotes().observe(this, { note ->
            noteAdapter.submitList(note)
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete_all) {
            viewModel.deleteAllNotes()
            Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}