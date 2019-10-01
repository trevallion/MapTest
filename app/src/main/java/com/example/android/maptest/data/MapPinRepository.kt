package com.example.android.maptest.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.maptest.data.database.MapPinDatabase
import com.example.android.maptest.data.database.MapPinDatabaseDao
import com.example.android.maptest.data.network.MapApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class MapPinRepository private constructor(val database : MapPinDatabaseDao) {

    private val _pins = MutableLiveData<List<MapPin>>()

    val pins: LiveData<List<MapPin>>
        get() = _pins

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        // Check if pins are in database first.
        var newPins : List<MapPin>? = database.getAllMapPins().value
        if (newPins == null || newPins.isEmpty()) {
            coroutineScope.launch {
                // Get pins from MapApi if they aren't in the database.
                val getPropertiesDeferred = MapApi.retrofitService.getPins()
                try {
                    val listResult = getPropertiesDeferred.await()
                    newPins = listResult
                    database.insert(newPins!!)
                } catch (e: Exception) {
                    newPins = ArrayList()
                }
            }
        }
        _pins.value = newPins
    }

    companion object {
        private lateinit var instance : MapPinRepository

        fun getInstance(context: Context) : MapPinRepository {
            if(!this::instance.isInitialized){
                val dao = MapPinDatabase.getInstance(context).mapPinDatabaseDao
                instance = MapPinRepository(dao)
            }
            return instance
        }
    }
}