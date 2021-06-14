package com.github.rexfilius.prioritynotes.addeditnote

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.rexfilius.prioritynotes.R
import com.github.rexfilius.prioritynotes.data.Note
import com.github.rexfilius.prioritynotes.databinding.AddEditNoteBinding

class AddEditNoteFragment : Fragment(R.layout.add_edit_note) {

    private val viewModel: AddEditNoteViewModel by viewModels()
    private var addEditBinding: AddEditNoteBinding? = null
    private val args: AddEditNoteFragmentArgs by navArgs()

    lateinit var note: Note

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = AddEditNoteBinding.bind(view)
        addEditBinding = binding

        binding.addEditNotePriority.apply {
            minValue = 1
            maxValue = 10
        }

        // TODO:
        // set icon of app bar back-button in this fragment to R.drawable.ic_close
        // clicking on the app bar back-button in this fragment,
        // will navigate back to NoteFragment

        // notify fragments to populate options-menu
        setHasOptionsMenu(true)
    }

    override fun onDestroyView() {
        addEditBinding = null
        super.onDestroyView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add_edit_note, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.menu_save_note -> {
                // if note is new - insert note in database
                // if note isn't new - update note in database
                // after insert or update note; navigate to NoteFragment
                // ISSUE: So the insert and update function is working
                // the next problem is to find out why the note title and description
                // does not show when a note is clicked from the NoteFragment,
                // and the funny thing is that the clicked note is updated
                if (args.noteID == 0) {
                    note = addEditBinding?.addEditNotePriority?.value?.let {
                        Note(
                            title = addEditBinding?.addEditNoteTitle?.text.toString(),
                            description = addEditBinding?.addEditNoteDescription?.text.toString(),
                            priority = it
                        )
                    }!!
                    viewModel.insertNote(note)
                    Toast.makeText(activity, "Note saved", Toast.LENGTH_LONG).show()
                } else {
                    val id = args.noteID
                    val title = args.noteTitle
                    note = addEditBinding?.addEditNotePriority?.value?.let {
                        Note(
                            title = title,
                            description = addEditBinding?.addEditNoteDescription?.text.toString(),
                            priority = it,
                            id = id.toLong()
                        )
                    }!!
                    viewModel.updateNote(note)
                    Toast.makeText(activity, "Note updated", Toast.LENGTH_LONG).show()
                }
                this.findNavController().navigate(
                    AddEditNoteFragmentDirections.actionAddEditNoteFragmentToNotesFragment()
                )
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}