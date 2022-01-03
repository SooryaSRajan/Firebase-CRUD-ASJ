package com.example.crudfirebase

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseReference = FirebaseDatabase.getInstance().reference.child("DATA")


    }

    override fun onStart() {
        super.onStart()

        update.setOnClickListener {
            val data = dataField.text.toString()
            val modelData = Model(data, getDateTme())

            if (data.isNotEmpty()) {
                databaseReference.push().setValue(modelData)
                dataField.text?.clear()
                dataField.error = null
            }
            else{
                dataField.error = "Please enter a value"
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getDateTme(): String {
        val simpleDateFormat = SimpleDateFormat("HH:mm, dd.MM.yyyy")
        return simpleDateFormat.format(Date())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_icon, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.viewData){
            startActivity(Intent(this, ListData::class.java))
        }
        return true
    }
}