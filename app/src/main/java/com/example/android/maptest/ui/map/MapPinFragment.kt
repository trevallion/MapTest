package com.example.android.maptest.ui.map

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.android.maptest.R
import com.example.android.maptest.data.MapPin
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.annotations.Marker
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.geometry.LatLngBounds
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style

class MapPinFragment : Fragment() {

    private var mapView: MapView? = null
    private var mapboxMap : MapboxMap? = null

    companion object {
        fun newInstance() = MapPinFragment()
    }

    private lateinit var viewModel: MapViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_map_pin, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val application = requireNotNull(this.activity).application

        // Setup recycler view
        val layoutManager = LinearLayoutManager(application, LinearLayoutManager.HORIZONTAL, false)
        val snapHelper = LinearSnapHelper()
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView?.layoutManager = layoutManager
        snapHelper.attachToRecyclerView(recyclerView)

        // Setup view model
        val viewModelFactory = MapViewModelFactory(application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MapViewModel::class.java)

        // Setup map view.
        mapView = view?.findViewById(R.id.mapView)
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync { map ->
            map.setStyle(Style.MAPBOX_STREETS)
            mapboxMap = map
            MapPinAdapter.setMapboxMap(map)
        }

        // Observe LiveData in view model.
        viewModel.pins.observe(this, Observer {mapPins ->
            onMapPinsUpdated(mapPins)
            val adapter = MapPinAdapter(mapPins, application)
            recyclerView?.adapter = adapter
        })

    }

    // This logic should be moved to the view model.
    private fun onMapPinsUpdated(mapPins : List<MapPin>?){
        if(mapPins != null && mapPins.isNotEmpty()) {
            mapView?.getMapAsync { map ->
                // Clear all existing pins first.
                // It would be better to track pins as live data and insert and remove them as
                // the source data changes instead of updating them all at once.
                map.clear()
                val boundsBuilder : LatLngBounds.Builder = LatLngBounds.Builder()
                for (mapPin in mapPins) {
                    val latLng = LatLng(mapPin.latitude, mapPin.longitude)
                    boundsBuilder.include(latLng)
                    map.addMarker(
                        MarkerOptions()
                            .position(latLng)
                            .title(mapPin.name)
                    )
                }
                map.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 100))
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

}
