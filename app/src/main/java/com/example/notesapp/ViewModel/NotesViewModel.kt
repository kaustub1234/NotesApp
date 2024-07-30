package com.example.notesapp.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.Room.NotesRepo
import com.example.notesapp.model.Note
import kotlinx.coroutines.launch

class NotesViewModel(val app: Application, private val repo: NotesRepo) : AndroidViewModel(app) {
    fun addNote(note: Note) = viewModelScope.launch {
        repo.insertNote(note)
    }

    fun removeNote(note: Note) = viewModelScope.launch {
        repo.deleteNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        repo.updateNote(note)
    }

    fun getAllNotes() = repo.getAllNotes()

    fun searchNotes(query: String) = repo.searchNotes(query)
}