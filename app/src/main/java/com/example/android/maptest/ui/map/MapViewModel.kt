package com.example.android.maptest.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.android.maptest.data.MapPin
import com.example.android.maptest.data.MapPinRepository

class MapViewModel(repository: MapPinRepository) : ViewModel() {

    private val _pins: MediatorLiveData<List<MapPin>> = MediatorLiveData<List<MapPin>>()

    val pins: LiveData<List<MapPin>>
        get() = _pins

    init {
        _pins.addSource(repository.pins) { newPins ->
            _pins.value = newPins
        }
    }
}
