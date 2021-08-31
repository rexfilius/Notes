package com.github.rexfilius.prioritynotes.ui.notes

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.rexfilius.prioritynotes.R
import com.github.rexfilius.prioritynotes.data.model.Note
import com.github.rexfilius.prioritynotes.databinding.FragmentNoteBinding
import com.github.rexfilius.prioritynotes.util.Constants.DELETE_NOTE
import com.github.rexfilius.prioritynotes.util.toast

class NoteFragment : Fragment(R.layout.fragment_note) {

    private val viewModel: NoteViewModel by viewModels()
    private lateinit var noteAdapter: NoteAdapter
    private var noteBinding: FragmentNoteBinding? = null
    // private lateinit var itemTouchHelper: ItemTouchHelper

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
                NoteFragmentDirections.actionNotesFragmentToAddEditNoteFragment()
            )
        }

        // notify fragments to populate options-menu
        setHasOptionsMenu(true)

        // function to delete a note with a swipe action
        // itemTouchHelper().attachToRecyclerView(binding.noteRecyclerView)
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
                viewModel.deleteAllNotes()
                DELETE_NOTE.toast(requireContext())
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
                note.id.toInt()
            )
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
