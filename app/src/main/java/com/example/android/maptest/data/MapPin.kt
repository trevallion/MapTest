package com.example.android.maptest.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "map_pin_table")
data class MapPin(
    @PrimaryKey
    val id: Long,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val description: String
) : Parcelable {
}