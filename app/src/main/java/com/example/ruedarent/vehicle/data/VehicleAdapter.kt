package com.example.ruedarent.vehicle.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ruedarent.R

class VehicleAdapter(private val vehicleList: List<Vehicle>) :
    RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder>() {

    // ViewHolder que contiene las referencias a los elementos del layout
    class VehicleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val vehicleImage: ImageView = itemView.findViewById(R.id.ivVehicle)
        val vehicleTitle: TextView = itemView.findViewById(R.id.tvVehicle)
        val vehicleInfo: TextView = itemView.findViewById(R.id.tvVehicleInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_vehicle, parent, false)
        return VehicleViewHolder(view)
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        val vehicle = vehicleList[position]
        holder.vehicleTitle.text = vehicle.type
        holder.vehicleInfo.text = vehicle.description

        // Cargar imagen usando Glide si la imagen es una URL
        Glide.with(holder.itemView.context)
            .load(vehicle.imagen)
            .placeholder(R.drawable.scooter_image)  // Asegúrate de que este recurso existe
            .into(holder.vehicleImage)
    }

    override fun getItemCount(): Int = vehicleList.size
}