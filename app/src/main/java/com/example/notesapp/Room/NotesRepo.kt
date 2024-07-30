package com.example.notesapp.Room

import com.example.notesapp.model.Note

class NotesRepo(private val notesDatabase: NotesDatabase) {
    suspend fun insertNote(note: Note) = notesDatabase.getNoteDAO().insert(note);
    suspend fun deleteNote(note: Note) = notesDatabase.getNoteDAO().delete(note);
    suspend fun updateNote(note: Note) = notesDatabase.getNoteDAO().update(note);

    fun getAllNotes() = notesDatabase.getNoteDAO().getAll();
    fun searchNotes(query: String) = notesDatabase.getNoteDAO().search(query);
}