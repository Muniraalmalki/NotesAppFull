package com.example.notesappfull

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesappfull.databinding.ItemRowBinding

class RecyclerViewAdapter( private var noteList: ArrayList<Note>,private var activity: MainActivity): RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>()  {
    class ItemViewHolder(val binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return  ItemViewHolder(ItemRowBinding.inflate(
            LayoutInflater.from(parent.context)
            , parent,
            false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val notes = noteList[position]
        holder.binding.apply {
            tvNote.text = notes.note

            ibEditNote.setOnClickListener {
                activity.raiseDialog(notes.pk)
            }
            ibDeleteNote.setOnClickListener {
//                activity.deleteNote(notes.pk)
                val builder = AlertDialog.Builder(holder.itemView.context)
                builder.setTitle("Are you sure want to delete this note?")
                builder.setPositiveButton("Delete"){_,_ ->
                    activity.deleteNote(notes.pk)
                }
                builder.setNegativeButton("Cancel"){_,_ ->}
                builder.show()
            }

        }
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    fun update(noteList: ArrayList<Note>){
        this.noteList = noteList
        notifyDataSetChanged()
    }
}