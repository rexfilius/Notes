package com.github.rexfilius.notes.ui.noteslist

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.rexfilius.notes.R
import com.github.rexfilius.notes.databinding.FragmentNoteBinding
import com.github.rexfilius.notes.model.Note
import com.github.rexfilius.notes.util.Constants.DELETE_ALL_NOTES
import com.github.rexfilius.notes.util.Constants.DELETE_NOTE
import com.github.rexfilius.notes.util.toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteFragment : Fragment(R.layout.fragment_note) {

    private lateinit var noteAdapter: NoteAdapter
    private var noteBinding: FragmentNoteBinding? = null
    private val viewModel: NotesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentNoteBinding.bind(view)
        noteBinding = binding

        noteAdapter = NoteAdapter {
            noteAdapterOnClick(it)
        }
        //val s = NoteAdapter(::noteAdapterOnClick)

        viewModel.getAllNotes().observe(viewLifecycleOwner, {
            noteAdapter.submitList(it)
        })

        binding.noteRecyclerView.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = noteAdapter
            setHasFixedSize(true)
        }

        binding.noteFAB.setOnClickListener {
            this.findNavController().navigate(
                NoteFragmentDirections.actionNotesFragmentToAddEditNoteFragment(
                    title = resources.getString(R.string.add_note)
                )
            )
        }

        // notify fragments to populate options-menu
        setHasOptionsMenu(true)

        itemTouchHelper().attachToRecyclerView(binding.noteRecyclerView)
    }

    override fun onDestroyView() {
        noteBinding = null
        super.onDestroyView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_note, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_delete_all -> {
                showDeleteDialog()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Notes are displayed in this fragment. When a note is clicked, get ID of the note
     * and pass as argument to the AddEditNoteFragment, so that the note can be
     * edited and updated to the database.
     */
    private fun noteAdapterOnClick(note: Note) {
        this.findNavController().navigate(
            NoteFragmentDirections.actionNotesFragmentToAddEditNoteFragment(
                noteID = note.id,
                title = resources.getString(R.string.edit_note)
            )
        )
    }

    /**Function to delete a note with a swipe action*/
    private fun itemTouchHelper(): ItemTouchHelper {
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0, LEFT or RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                val note = noteAdapter.getNoteAt(viewHolder.absoluteAdapterPosition)
                viewModel.deleteNote(note)

                Snackbar
                    .make(requireView(), DELETE_NOTE, Snackbar.LENGTH_LONG)
                    .setAction(R.string.undo_delete) {
                        viewModel.insertNote(note)
                    }.show()
            }
        })
        return itemTouchHelper
    }

    /**Alert Dialog to confirm deletion of all notes*/
    private fun showDeleteDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.alert_title)
            .setMessage(R.string.alert_text)
            .setNegativeButton(R.string.alert_no) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(R.string.alert_yes) { _, _ ->
                viewModel.deleteAllNotes()
                DELETE_ALL_NOTES.toast(requireContext())
            }
            .show()
    }

}
