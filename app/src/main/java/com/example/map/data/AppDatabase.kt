package com.example.map.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.map.data.PointModel
import com.example.map.data.PointsDao

@Database(entities = [PointModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDao(): PointsDao
}