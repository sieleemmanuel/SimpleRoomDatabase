package com.devsiele.roomdatabase.main.listitems

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devsiele.roomdatabase.R
import com.devsiele.roomdatabase.model.Note


class RecyclerAdapter(private val itemClickListener: ItemClickListener)
    :ListAdapter<Note,RecyclerAdapter.ViewHolder>(RecyclerDiffCallback()) {
   /* var noteList = listOf<Note>()
    set(value) {
         field = value
        notifyDataSetChanged()
    }
*/

    class ViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView){
        private val title_category: TextView = itemView.findViewById(R.id.txtTitleCategory)
        private val texts: TextView = itemView.findViewById(R.id.txtContent)
        fun bind(
            note: Note,
        ) {
           title_category.text = "${note.noteCategory}: ${note.noteTitle}"
            texts.text = note.noteText
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.list_items,
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = getItem(position)
        holder.bind(note)
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClicked(note)
        }
    }

    class ItemClickListener(val itemClickListener:(note:Note)->Unit){
        fun onItemClicked(note: Note) = itemClickListener(note)
    }
}
class RecyclerDiffCallback: DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
       return oldItem.noteId == newItem.noteId
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }

}
