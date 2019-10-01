package com.example.android.maptest.data.network

import com.example.android.maptest.data.MapPin
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://annetog.gotenna.com/development/scripts/get_map_pins.php"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()

    interface MapApiService {
        @GET
        fun getPins(): Deferred<List<MapPin>>
    }

    object MapApi {
        val retrofitService : MapApiService by lazy {
            retrofit.create(MapApiService::class.java)
        }
    }