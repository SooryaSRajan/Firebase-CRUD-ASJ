package com.example.crudfirebase

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.activity_list_data.*

class ListData : AppCompatActivity() {

    private lateinit var listOfData: MutableList<Model>
    private lateinit var adapter: RecyclerAdapter
    lateinit var mRef: DatabaseReference
    private lateinit var eventListener: ValueEventListener;
    private val TAG = "ListData"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_data)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "List Of Data"

        listOfData = mutableListOf()
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RecyclerAdapter(listOfData, this)
        recyclerView.adapter = adapter

        eventListener = object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listOfData.clear()
                adapter.notifyDataSetChanged()
                for (i in dataSnapshot.children) {
                    Log.d(TAG, "onDataChange: ${i.getValue(Model::class.java)} $")
                    val model : Model = i.getValue<Model>()!!
                    model.key = i.key
                    listOfData.add(model)
                    adapter.notifyItemInserted(adapter.itemCount - 1)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        }

        mRef = FirebaseDatabase.getInstance().reference.child("DATA")

        mRef.addValueEventListener(eventListener)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        mRef.removeEventListener(eventListener)
    }
}