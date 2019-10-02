package com.example.android.maptest.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.android.maptest.data.MapPin

@Dao
interface MapPinDatabaseDao {

    @Insert
    fun insert(mapPin: MapPin)

    @Insert
    fun insert(mapPins: List<MapPin>)

    @Update
    fun update(mapPin: MapPin)

    @Query("SELECT * from map_pin_table WHERE id = :key")
    fun get(key: Long): MapPin?

    @Query("DELETE FROM map_pin_table")
    fun clear()

    @Query("SELECT * FROM map_pin_table ORDER BY id DESC")
    fun getAllMapPins():List<MapPin>
}