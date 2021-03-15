package com.github.rexfilius.prioritynotes.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.rexfilius.prioritynotes.R
import com.github.rexfilius.prioritynotes.data.Note

class NoteAdapter : ListAdapter<Note, NoteAdapter.NoteViewHolder>(DIFF_UTIL) {

    private var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_notes_item, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote: Note = getItem(position)
        holder.textViewPriority.text = currentNote.priority.toString()
        holder.textViewTitle.text = currentNote.title
        holder.textViewDescription.text = currentNote.description
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewPriority: TextView = itemView.findViewById(R.id.txtViewPriority)
        val textViewTitle: TextView = itemView.findViewById(R.id.txtViewTitle)
        val textViewDescription: TextView = itemView.findViewById(R.id.txtViewDescription)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (itemClickListener != null && position != RecyclerView.NO_POSITION) {
                    itemClickListener?.onItemClick(getItem(position))
                }
            }
        }
    }

    companion object {
        private val DIFF_UTIL: DiffUtil.ItemCallback<Note> =
            object : DiffUtil.ItemCallback<Note>() {
                override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                    return oldItem.title == newItem.title &&
                            oldItem.description == newItem.description &&
                            oldItem.priority == newItem.priority
                }
            }
    }

    fun getNoteAt(position: Int): Note {
        return getItem(position)
    }

    interface OnItemClickListener {
        fun onItemClick(note: Note)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

}
