package com.example.map.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.map.data.PointModel

@Dao
interface PointsDao {
    @Query("SELECT * FROM points")
    fun getAllPoints() : LiveData<List<PointModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPoint(point: PointModel)
}