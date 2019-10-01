package com.example.android.maptest.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.android.maptest.data.MapPin

@Database(entities = [MapPin::class], version = 1, exportSchema = false)
abstract class MapPinDatabase : RoomDatabase() {
    abstract val mapPinDatabaseDao: MapPinDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: MapPinDatabase? = null

        fun getInstance(context: Context): MapPinDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MapPinDatabase::class.java,
                        "map_pin_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}