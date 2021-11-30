package com.devsiele.roomdatabase.main.listitems

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.devsiele.roomdatabase.R
import com.devsiele.roomdatabase.model.Note
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




class RecyclerAdapter(private val listener:ClickListener):RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    var noteList = listOf<Note>()
    set(value) {
         field = value
        notifyDataSetChanged()
    }


    class ViewHolder(itemView:View, private val listener:ClickListener) :RecyclerView.ViewHolder(itemView){
        private val title_category: TextView = itemView.findViewById(R.id.txtTitleCategory)
        private val texts: TextView = itemView.findViewById(R.id.txtContent)
        fun bind(
            note: Note,
        ) {
           title_category.text = "${note.noteCategory}: ${note.noteTitle}"
            texts.text = note.noteText
            itemView.setOnClickListener {
                listener.onClick(it,note)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.list_items,
            parent,
            false
        ),listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = noteList[position]
        holder.bind(note)
    }
    override fun getItemCount(): Int = noteList.size

}

interface ClickListener{
    fun onClick(view:View,note: Note){
    }

}