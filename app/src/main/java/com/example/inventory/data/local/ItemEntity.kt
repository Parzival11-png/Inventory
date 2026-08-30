package com.example.inventory.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val imagePath: String,
    val description: String,
    val dateAdded: Long = System.currentTimeMillis()
)