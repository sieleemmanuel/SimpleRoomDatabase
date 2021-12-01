package com.devsiele.roomdatabase.main.listitems

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devsiele.roomdatabase.R
import com.devsiele.roomdatabase.model.Note


class RecyclerAdapter(private val itemClickListener: ItemClickListener)
    :RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    var noteList = listOf<Note>()
    set(value) {
         field = value
        notifyDataSetChanged()
    }


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
        val note = noteList[position]
        holder.bind(note)
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClicked(note)
        }
    }
    override fun getItemCount(): Int = noteList.size

    class ItemClickListener(val itemClickListener:(note:Note)->Unit){
        fun onItemClicked(note: Note) = itemClickListener(note)
    }
}
