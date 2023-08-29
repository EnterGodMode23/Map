package com.example.map.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "points")
data class PointModel(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val longitude: Double,
    val latitude: Double
)