package com.example.crudfirebase

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
data class Model(var data: String? = null, var time: String? = null, @Exclude @JvmField var key : String? = null) : Serializable {
}