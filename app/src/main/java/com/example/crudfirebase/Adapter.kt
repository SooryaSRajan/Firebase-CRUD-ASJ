package com.example.crudfirebase

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.list_item.view.*

class RecyclerAdapter(
    private var mutableList: MutableList<Model>,
    private var context: Context
) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    var databaseReference = FirebaseDatabase.getInstance().reference.child("DATA")


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.data.text = mutableList[position].data
        holder.time.text = mutableList[position].time
    }

    override fun getItemCount(): Int = mutableList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var data = itemView.data!!
        var time = itemView.time!!

        init {
            itemView.setOnClickListener {
                context.startActivity(
                    Intent(context, ModifyActivity::class.java).putExtra(
                        "DATA",
                        mutableList[adapterPosition]
                    )
                )
            }
            itemView.delete.setOnClickListener {
                databaseReference.child(mutableList[adapterPosition].key!!).removeValue()
                mutableList.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }
        }
    }

}