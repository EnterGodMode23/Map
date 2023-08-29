package com.example.map.app.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.map.data.PointModel
import com.example.map.domain.PointsRepository
import com.yandex.mapkit.geometry.Point
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class MapViewModel @Inject constructor(
    private val pointsRepository: PointsRepository
): ViewModel() {

    var lastPosition: Point? = null
    val placeMarks: LiveData<List<PointModel>>

    init {
        placeMarks = pointsRepository.allPoints
    }

    fun addPoint(point: Point) {
        viewModelScope.launch(Dispatchers.IO){
            pointsRepository.addPoint(point)
        }
    }

}