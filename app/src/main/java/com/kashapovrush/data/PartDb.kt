package com.kashapovrush.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "parts3")
data class PartDb(
    val name: String,
    val count: Int,
    val date: String,
    val number: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val time: String,
    val location: String
)
