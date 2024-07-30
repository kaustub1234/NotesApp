package com.example.notesapp.fragments

import android.R.attr.maxLength
import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
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
import com.example.notesapp.databinding.FragmentNewNoteBinding
import com.example.notesapp.model.Note


class NewNote : Fragment(R.layout.fragment_new_note) {
    private var _binding: FragmentNewNoteBinding? = null
    val binding get() = _binding!!

    private lateinit var notesViewModel: NotesViewModel
    private lateinit var mView: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewNoteBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesViewModel = (activity as MainActivity).notesViewModel
        mView = view;
    }

    private fun saveNote(view: View) {
        val noteTitle = binding.titleEditText.text.toString();
        val noteBody = binding.bodyEditText.text.toString();

        if (noteTitle.isNotEmpty()) {
            val note = Note(0, noteTitle, noteBody);
            notesViewModel.addNote(note)
            Toast.makeText(mView.context, "NOTE SAVED SUCESSFULLY", Toast.LENGTH_LONG).show()

            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_holder, HomeFragment()).commit()
        } else {
            Toast.makeText(mView.context, "Pls Enter a note title...", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_new_note, menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> saveNote(mView)
        }
        return super.onOptionsItemSelected(item)
    }
}