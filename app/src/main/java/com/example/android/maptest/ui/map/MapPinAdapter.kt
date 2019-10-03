package com.example.android.maptest.ui.map

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.maptest.R
import com.example.android.maptest.data.MapPin
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import kotlinx.android.synthetic.main.map_pin_list_item.view.*

class MapPinAdapter(private val pins: List<MapPin>, private val context: Context) :
    RecyclerView.Adapter<ViewHolder>(), PinClickListener {
    companion object {
        private var mapboxMap: MapboxMap? = null

        fun setMapboxMap(newMap: MapboxMap) {
            mapboxMap = newMap
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(context)
                .inflate(R.layout.map_pin_list_item, parent, false), this
        )
    }

    override fun getItemCount(): Int {
        return pins.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.nameView?.text = pins.get(position).name
        holder?.descriptionView?.text = pins.get(position).description
    }

    override fun onClick(position: Int) {
        val newCameraLatLng = LatLng(pins[position].latitude, pins[position].longitude)
        val newCameraPosition = CameraPosition.Builder()
            .target(newCameraLatLng)
            .build()
        mapboxMap?.animateCamera(CameraUpdateFactory.newCameraPosition(newCameraPosition))
    }

}

class ViewHolder(view: View, val clickListener: PinClickListener) : RecyclerView.ViewHolder(view),
    View.OnClickListener {
    override fun onClick(v: View?) {
        clickListener?.onClick(layoutPosition)
    }

    val nameView: TextView = view.name_text
    val descriptionView: TextView = view.description_text

    init {
        view.setOnClickListener(this)
    }
}

interface PinClickListener {
    fun onClick(position: Int)
}