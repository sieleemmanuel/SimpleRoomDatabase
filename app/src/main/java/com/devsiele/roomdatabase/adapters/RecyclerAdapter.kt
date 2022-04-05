package com.devsiele.roomdatabase.main.listitems

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devsiele.roomdatabase.R
import com.devsiele.roomdatabase.data.model.Note


class RecyclerAdapter(private val itemClickListener: ItemClickListener)
    :ListAdapter<Note,RecyclerAdapter.ViewHolder>(RecyclerDiffCallback()) {
  init {
      setHasStableIds(true)
  }
    private var tracker:SelectionTracker<Note>? = null

    class ViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView){
        private val titleCategory: TextView = itemView.findViewById(R.id.txtTitleCategory)
        private val texts: TextView = itemView.findViewById(R.id.txtContent)
        @SuppressLint("SetTextI18n")
        fun bind(
            note: Note,
        ) {
           titleCategory.text = "${note.noteCategory}: ${note.noteTitle}"
            texts.text = note.noteText
        }

        fun getItemDetails():ItemDetailsLookup.ItemDetails<Note> = object: ItemDetailsLookup.ItemDetails<Note>(){
            override fun getPosition(): Int = bindingAdapterPosition

            override fun getSelectionKey(): Note? = (bindingAdapter as RecyclerAdapter).currentList[position]
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

        tracker.let {
            if (it!!.isSelected(currentList[position])){
                holder.itemView.background = ColorDrawable(Color.parseColor("#80deea"))
            }else{
                holder.itemView.background = null
            }
        }

    }

    override fun getItemId(position: Int): Long = position.toLong()

    class ItemClickListener(val itemClickListener:(note: Note)->Unit){
        fun onItemClicked(note: Note) = itemClickListener(note)
    }

    fun setTracker(tracker: SelectionTracker<Note>){
        this.tracker = tracker
    }

    fun clearTracker(tracker: SelectionTracker<Note>){
        tracker.clearSelection()
    }


    fun getItemAtPosition(position: Int): Note? = currentList[position]

    fun getPosition(title:String) = currentList.indexOfFirst { it.noteTitle == title }
}


class RecyclerDiffCallback: DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
       return oldItem.noteId == newItem.noteId
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }

}

class ItemLookUp(private val recyclerView: RecyclerView):ItemDetailsLookup<Note>(){
    override fun getItemDetails(event: MotionEvent): ItemDetails<Note>? {

        val view = recyclerView.findChildViewUnder(event.x,event.y)
        if (view!=null){
            return (recyclerView.getChildViewHolder(view) as RecyclerAdapter.ViewHolder).getItemDetails()
        }
        return null
    }


    }

