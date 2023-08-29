package com.example.map

import android.app.Application
import com.example.map.utils.Constants
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    init {
        MapKitFactory.setApiKey(Constants.MAPKIT_API_KEY)
    }

}