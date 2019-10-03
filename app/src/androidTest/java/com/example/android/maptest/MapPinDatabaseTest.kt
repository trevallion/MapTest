package com.example.android.maptest

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.android.maptest.data.MapPin
import com.example.android.maptest.data.database.MapPinDatabase
import com.example.android.maptest.data.database.MapPinDatabaseDao
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Exception

const val TEST_PIN_ID:Long = 1
const val TEST_PIN_NAME:String = "test"
const val TEST_PIN_LATITUDE:Double = 5.0
const val TEST_PIN_LONGITUDE:Double = -5.0
const val TEST_PIN_DESCRIPTION:String = "description"

@RunWith(AndroidJUnit4::class)
class MapPinDatabaseTest {

    private lateinit var mapPinDao: MapPinDatabaseDao
    private lateinit var db: MapPinDatabase

    @Before
    fun createDb(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Use in-memory database for testing.
        db = Room.inMemoryDatabaseBuilder(context, MapPinDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        mapPinDao = db.mapPinDatabaseDao
    }

    @After
    fun closeDb(){
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetPin(){
        val pin = MapPin(
            TEST_PIN_ID,
            TEST_PIN_NAME,
            TEST_PIN_LATITUDE,
            TEST_PIN_LONGITUDE,
            TEST_PIN_DESCRIPTION
            )
        mapPinDao.insert(pin)
        val pinFromDb = mapPinDao.get(TEST_PIN_ID)

        assertEquals(pinFromDb?.id, TEST_PIN_ID)
        assertEquals(pinFromDb?.name, TEST_PIN_NAME)
        assertEquals(pinFromDb?.latitude, TEST_PIN_LATITUDE)
        assertEquals(pinFromDb?.longitude, TEST_PIN_LONGITUDE)
        assertEquals(pinFromDb?.description, TEST_PIN_DESCRIPTION)
    }
}