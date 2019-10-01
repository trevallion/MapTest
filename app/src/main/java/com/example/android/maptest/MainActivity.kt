package com.example.android.maptest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android.maptest.ui.map.MapPinFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MapPinFragment.newInstance())
                .commitNow()
        }
    }

}
