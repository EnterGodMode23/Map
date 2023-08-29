package com.example.map.domain

import androidx.lifecycle.LiveData
import com.example.map.data.PointsDao
import com.example.map.data.PointModel
import com.yandex.mapkit.geometry.Point
import javax.inject.Inject

class PointsRepository @Inject constructor(private val pointsDao: PointsDao) {

    val allPoints : LiveData<List<PointModel>> = pointsDao.getAllPoints()

    suspend fun addPoint(point: Point) {
        pointsDao.addPoint(pointMapping(point))
    }

    private fun pointMapping(point: Point) : PointModel {
        return PointModel(
            latitude = point.latitude,
            longitude = point.longitude
        )
    }

}