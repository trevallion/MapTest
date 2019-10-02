package com.example.android.maptest.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.maptest.data.database.MapPinDatabase
import com.example.android.maptest.data.database.MapPinDatabaseDao
import com.example.android.maptest.data.network.MapApi
import kotlinx.coroutines.*
import java.lang.Exception

class MapPinRepository private constructor(val database: MapPinDatabaseDao) {

    private val _pins = MutableLiveData<List<MapPin>>()

    val pins: LiveData<List<MapPin>>
        get() = _pins

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        uiScope.launch {
            _pins.value = getMapPinsFromDataSources()
        }
    }

    private suspend fun getMapPinsFromDataSources(): List<MapPin>? {
        return withContext(Dispatchers.IO) {
            var newPins: List<MapPin>? = database.getAllMapPins()
            if (newPins == null || newPins.isEmpty()) {
                // Get pins from MapApi if they aren't in the database.
                val getPinsDeferred = MapApi.retrofitService.getPins()
                try {
                    val listResult = getPinsDeferred.await()
                    newPins = listResult
                    database.insert(listResult)
                } catch (e: Exception) {
                    Log.e("MapPinRepository", e.toString())
                    newPins = ArrayList()
                }
            }
            newPins
        }
    }

    companion object {
        private lateinit var instance: MapPinRepository

        fun getInstance(context: Context): MapPinRepository {
            if (!this::instance.isInitialized) {
                val dao = MapPinDatabase.getInstance(context).mapPinDatabaseDao
                instance = MapPinRepository(dao)
            }
            return instance
        }
    }
}