package com.example.notesapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.notesapp.MainActivity
import com.example.notesapp.R
import com.example.notesapp.ViewModel.NotesViewModel
import com.example.notesapp.databinding.FragmentUpdateNoteBinding
import com.example.notesapp.model.Note

class UpdateNote(val note: Note) : Fragment(R.layout.fragment_update_note) {
    private var _binding: FragmentUpdateNoteBinding? = null
    val binding get() = _binding!!

    private lateinit var notesViewModel: NotesViewModel

    private lateinit var currenNote: Note
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateNoteBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesViewModel = (activity as MainActivity).notesViewModel
        currenNote = note!!

        binding.updateTitleEditText.setText(currenNote.title)
        binding.updateBodyEditText.setText(currenNote.body)

        binding.fabUpdateNote.setOnClickListener {
            val title = binding.updateTitleEditText.text.toString().trim();
            val body = binding.updateBodyEditText.text.toString().trim();

            if (title.isNotEmpty()) {
                val note = Note(currenNote.id, title, body)
                notesViewModel.updateNote(note)
                (activity as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_holder, HomeFragment()).commit()

            } else {
                Toast.makeText(context, "Pls enter a note title", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun deleteNote() {
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("You want to delete this Note?")
            setPositiveButton("Delete") { _, _ ->
                notesViewModel.removeNote(currenNote)

                (activity as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_holder, HomeFragment()).commit()
            }
            setNegativeButton("Cancel", null)
        }.create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_update_note, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete -> deleteNote()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null;
    }
}