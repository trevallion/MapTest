package com.example.android.maptest.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "map_pin_table")
data class MapPin(
    @PrimaryKey
    val id: Long,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val description: String
) {
}