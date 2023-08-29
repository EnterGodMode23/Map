package com.example.map.app.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.map.data.PointModel
import com.example.map.domain.PointsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
public class PointsListViewModel @Inject constructor(
    private val pointsRepository: PointsRepository
): ViewModel() {

    val liveDataPoints: LiveData<List<PointModel>>

    init {
        liveDataPoints = pointsRepository.allPoints
    }

}