package com.github.rexfilius.prioritynotes.ui.notes

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.rexfilius.prioritynotes.R
import com.github.rexfilius.prioritynotes.data.model.Note
import com.github.rexfilius.prioritynotes.databinding.NotesBinding

class NoteFragment : Fragment(R.layout.notes) {

    private val viewModel: NoteViewModel by viewModels()
    private lateinit var noteAdapter: NoteAdapter
    private var notesBinding: NotesBinding? = null
    // private lateinit var itemTouchHelper: ItemTouchHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = NotesBinding.bind(view)
        notesBinding = binding

        noteAdapter = NoteAdapter {
            noteAdapterOnClick(it)
        }

        viewModel.getAllNotes().observe(viewLifecycleOwner, {
            noteAdapter.submitList(it)
            noteAdapter.notifyDataSetChanged()
        })

        binding.noteRecyclerView.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = noteAdapter
            setHasFixedSize(true)
        }

        binding.noteFAB.setOnClickListener {
            this.findNavController().navigate(
                NoteFragmentDirections.actionNotesFragmentToAddEditNoteFragment()
            )
        }

        // notify fragments to populate options-menu
        setHasOptionsMenu(true)

        // function to delete a note with a swipe action
        // itemTouchHelper().attachToRecyclerView(binding.noteRecyclerView)
    }

    override fun onDestroyView() {
        notesBinding = null
        super.onDestroyView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_note, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_delete_all -> {
                viewModel.deleteAllNotes()
                Toast.makeText(activity, "Notes deleted", Toast.LENGTH_SHORT)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun noteAdapterOnClick(note: Note) {
        // TODO:
        // when notes are displayed in this fragment, and a note is clicked,
        // get id of the note and pass as argument to the AddEditNote fragment,
        // so that the note can be edited and updated
        this.findNavController().navigate(
            NoteFragmentDirections.actionNotesFragmentToAddEditNoteFragment(note.id.toInt())
        )
    }






    /*private fun itemTouchHelper(): ItemTouchHelper {
        itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //viewModel.deleteNote(noteAdapter.getNoteAt(viewHolder.adapterPosition))
                Toast.makeText(activity, "Note deleted", Toast.LENGTH_SHORT).show()
            }

        })
        return itemTouchHelper
    }*/


}
