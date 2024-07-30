package com.example.notesapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.util.query
import androidx.transition.Visibility
import com.example.notesapp.MainActivity
import com.example.notesapp.R
import com.example.notesapp.ViewModel.NotesViewModel
import com.example.notesapp.adapters.NoteAdapter
import com.example.notesapp.databinding.FragmentHomeBinding
import com.example.notesapp.model.Note

class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener {
    private var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!

    private lateinit var notesViewModel: NotesViewModel
    private lateinit var notesAdapter: NoteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesViewModel = (activity as MainActivity).notesViewModel
        setRecyclerView()
        binding.fabAddNote.setOnClickListener {
            //moveToNewNote Fragment
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_holder, NewNote()).commit()
        }
    }

    private fun setRecyclerView() {
        notesAdapter = NoteAdapter(::moveToUpdateFragment)
        binding.notesRecylerView.apply {
            layoutManager = StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL
            )
            setHasFixedSize(true)
            adapter = notesAdapter
        }

        activity?.let {
            notesViewModel.getAllNotes().observe(
                viewLifecycleOwner
            ) { note ->
                notesAdapter.differ.submitList(note)
                updateUI(note)
            }
        }
    }

    fun moveToUpdateFragment(note:Note)
    {
        (activity as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_holder, UpdateNote(note)).commit()
    }

    private fun updateUI(note: List<Note>?) {
        if (note != null) {
            if (note.isNotEmpty()) {
                binding.emptyNotesLayout.visibility = View.GONE
                binding.notesRecylerView.visibility = View.VISIBLE
            } else {
                binding.emptyNotesLayout.visibility = View.VISIBLE
                binding.notesRecylerView.visibility = View.GONE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.home_menu, menu)
        val mMenuSearch = menu.findItem(R.id.menu_search).actionView as SearchView
        mMenuSearch.isSubmitButtonEnabled = false
        mMenuSearch.setOnQueryTextListener(this);
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
//        searchNote(query)
        return false;
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchNote(newText)
        }

        return true;
    }

    private fun searchNote(query: String?) {
        val searchQuery = "%$query"
        if (query != null) {
            notesViewModel.searchNotes(query).observe(this) { list ->
                notesAdapter.differ.submitList(list)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null;
    }
}