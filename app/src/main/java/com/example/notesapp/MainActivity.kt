package com.example.notesapp

import android.os.Bundle
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import com.example.notesapp.Room.NotesDatabase
import com.example.notesapp.Room.NotesRepo
import com.example.notesapp.ViewModel.NoteViewModelFactory
import com.example.notesapp.ViewModel.NotesViewModel
import com.example.notesapp.databinding.ActivityMainBinding
import com.example.notesapp.fragments.HomeFragment
import org.jetbrains.annotations.ApiStatus.NonExtendable

class MainActivity : AppCompatActivity() {
    lateinit var toolbar: androidx.appcompat.widget.Toolbar;
    lateinit var notesViewModel: NotesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar);
        setUpViewModel();
        supportFragmentManager.beginTransaction().add(R.id.fragment_holder, HomeFragment()).commit()
    }

    private fun setUpViewModel() {
        val notesRepo: NotesRepo = NotesRepo(NotesDatabase.invoke(this))
        val viewModelProviderFactory = NoteViewModelFactory(application, notesRepo);
        notesViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(NotesViewModel::class.java);
    }
}