package com.github.rexfilius.notes.ui.addeditnote

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.rexfilius.notes.R
import com.github.rexfilius.notes.model.Note
import com.github.rexfilius.notes.databinding.FragmentAddEditNoteBinding
import com.github.rexfilius.notes.util.Constants.SAVE_NOTE
import com.github.rexfilius.notes.util.Constants.UPDATE_NOTE
import com.github.rexfilius.notes.util.toast

class AddEditNoteFragment : Fragment(R.layout.fragment_add_edit_note) {

    private val viewModel: AddEditNoteViewModel by viewModels()
    private var addEditBinding: FragmentAddEditNoteBinding? = null
    private val args: AddEditNoteFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentAddEditNoteBinding.bind(view)
        addEditBinding = binding

        binding.addEditNotePriority.apply {
            minValue = 1
            maxValue = 10
        }

        viewModel.getNote(args.noteID).observe(viewLifecycleOwner, { note: Note? ->
            if (note != null) {
                binding.addEditNoteTitle.setText(note.title)
                binding.addEditNoteDescription.setText(note.description)
                binding.addEditNotePriority.value = note.priority
            }
        })

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
                if (args.noteID == 0L) {
                    val note = Note(
                        title = addEditBinding?.addEditNoteTitle?.text.toString(),
                        description = addEditBinding?.addEditNoteDescription?.text.toString(),
                        priority = addEditBinding?.addEditNotePriority?.value!!
                    )
                    viewModel.insertNote(note)
                    SAVE_NOTE.toast(requireContext())
                } else {
                    val note = Note(
                        title = addEditBinding?.addEditNoteTitle?.text.toString(),
                        description = addEditBinding?.addEditNoteDescription?.text.toString(),
                        priority = addEditBinding?.addEditNotePriority?.value!!,
                        id = args.noteID.toLong()
                    )
                    viewModel.updateNote(note)
                    UPDATE_NOTE.toast(requireContext())
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