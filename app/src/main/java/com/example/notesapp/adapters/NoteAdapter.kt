package com.example.notesapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.FragmentUpdateNoteBinding
import com.example.notesapp.databinding.NoteItemLayoutBinding
import com.example.notesapp.model.Note

class NoteAdapter(val moveToUpdateFragment: (note: Note) -> Unit) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    class NoteViewHolder(val itemBinding: NoteItemLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return (oldItem.id == newItem.id
                    && oldItem.body == newItem.body
                    && oldItem.title == newItem.title)
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem;
        }
    }

    val differ = AsyncListDiffer(this, differCallBack);

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            NoteItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        );
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        var currentItem = differ.currentList.get(position);
        holder.itemBinding.noteBody.text = currentItem.body
        holder.itemBinding.noteTitle.text = currentItem.title;
        holder.itemView.setOnClickListener {
            //moveToUpdateNotes Fragment
            moveToUpdateFragment(currentItem);
        }
    }
}