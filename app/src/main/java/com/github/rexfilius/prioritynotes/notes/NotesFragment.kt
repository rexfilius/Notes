package com.github.rexfilius.prioritynotes.notes

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.rexfilius.prioritynotes.R
import com.github.rexfilius.prioritynotes.data.Note
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NotesFragment : Fragment() {

    private lateinit var btnAddNote: FloatingActionButton
    private lateinit var noteRecyclerView: RecyclerView

    private val viewModel: NotesViewModel by viewModels()
    private lateinit var itemTouchHelper: ItemTouchHelper
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_notes, container, false)
        btnAddNote = view.findViewById(R.id.btnAddNote)
        noteRecyclerView = view.findViewById(R.id.noteRecyclerView)

        noteAdapter = NoteAdapter()
        noteRecyclerView.apply {
            layoutManager = LinearLayoutManager(view.context)
            setHasFixedSize(true)
            adapter = noteAdapter
        }

        btnAddNote.setOnClickListener {
            this.findNavController().navigate(
                NotesFragmentDirections.actionNotesFragmentToAddEditNoteFragment()
            )
        }

        viewModel.getAllNotes().observe(viewLifecycleOwner, { note ->
            noteAdapter.submitList(note)
        })

        // function to delete a note with a swipe action
        itemTouchHelper().attachToRecyclerView(noteRecyclerView)

        // notify fragments to populate options-menu
        setHasOptionsMenu(true)

        // TODO:
        // when notes are displayed in this fragment, and a note is clicked,
        // get id of the note and pass as argument to the AddEditNote fragment,
        // so that the note can be edited and updated
        noteAdapter.setOnItemClickListener(object : NoteAdapter.OnItemClickListener {
            override fun onItemClick(note: Note) {
                //
                activity?.findNavController(R.id.noteRecyclerView)?.navigate(
                    NotesFragmentDirections.actionNotesFragmentToAddEditNoteFragment()
                )
            }
        })

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_note, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_delete_all -> {
                viewModel.deleteAllNotes()
                Toast.makeText(activity, "All notes deleted", Toast.LENGTH_SHORT)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun itemTouchHelper(): ItemTouchHelper {
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
                viewModel.deleteNote(noteAdapter.getNoteAt(viewHolder.adapterPosition))
                Toast.makeText(activity, "Note deleted", Toast.LENGTH_SHORT).show()
            }

        })
        return itemTouchHelper
    }


}
