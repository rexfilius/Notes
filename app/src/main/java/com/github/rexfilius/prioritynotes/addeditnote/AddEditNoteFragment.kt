package com.github.rexfilius.prioritynotes.addeditnote

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import android.widget.NumberPicker
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.rexfilius.prioritynotes.R

class AddEditNoteFragment : Fragment() {

    private lateinit var editTextTitle: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var numberPicker: NumberPicker

    private val viewModel: AddEditNoteViewModel by viewModels()
    //val args: AddEditNoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_add_edit_note, container, false)
        editTextTitle = view.findViewById(R.id.editTxtTitle)
        editTextDescription = view.findViewById(R.id.editTextDescription)
        numberPicker = view.findViewById(R.id.numPickerPriority)

        numberPicker.apply {
            minValue = 1
            maxValue = 10
        }

        // TODO:
        // set icon of app bar back-button in this fragment to R.drawable.ic_close
        // clicking on the app bar back-button in this fragment,
        // will navigate back to NotesFragment

        // notify fragments to populate options-menu
        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add_edit_note, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.menu_save_note -> {
                // TODO:
                // if note is new - insert note in database
                // if note isn't new - update note in database
                // after insert or update note; navigate to NotesFragment
                this.findNavController().navigate(
                    AddEditNoteFragmentDirections.actionAddEditNoteFragmentToNotesFragment()
                )
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}