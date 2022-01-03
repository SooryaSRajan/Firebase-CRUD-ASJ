package com.example.crudfirebase

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_modify.*
import kotlinx.android.synthetic.main.activity_modify.update
import java.text.SimpleDateFormat
import java.util.*

class ModifyActivity : AppCompatActivity() {

    lateinit var data: Model
    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Update Data"

        data = (intent.getSerializableExtra("DATA") as Model?)!!
        databaseReference = FirebaseDatabase.getInstance().reference.child("DATA")

        modifyField.setText(data.data)

    }

    override fun onStart() {
        super.onStart()

        update.setOnClickListener {
            val dataString = modifyField.text.toString()

            if (dataString.isNotEmpty()) {
                data.data = dataString
                data.time = getDateTme()
                databaseReference.child(data.key!!).setValue(data)
                modifyField.error = null
                Toast.makeText(this, "Updated!", Toast.LENGTH_SHORT).show()
            }
            else{
                modifyField.error = "Please enter a value"
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    @SuppressLint("SimpleDateFormat")
    private fun getDateTme(): String {
        val simpleDateFormat = SimpleDateFormat("HH:mm, dd.MM.yyyy")
        return simpleDateFormat.format(Date())
    }
}