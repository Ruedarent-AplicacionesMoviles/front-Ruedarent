package com.example.ruedarent.vehicle

import com.example.ruedarent.vehicle.data.VehicleAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ruedarent.R
import com.example.ruedarent.vehicle.data.Vehicle

class VehicleActivity : AppCompatActivity() {

    private lateinit var vehicleAdapter: VehicleAdapter
    private lateinit var vehicleList: List<Vehicle>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_highlights)  // Este layout debe contener tu RecyclerView

        val recyclerView: RecyclerView = findViewById(R.id.rvVehicles)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Simulación de lista de vehículos con todos los campos necesarios para la clase Vehicle
        vehicleList = listOf(
            Vehicle(
                id = 1,
                type = "Bike",
                year = "2022",
                description = "Mountain bike",
                salePrice = "500",
                rentalPrice = "50",
                region = "Lima",
                imagen = "https://example.com/bike_image.jpg",
                userId = 1
            ),
            Vehicle(
                id = 2,
                type = "Scooter",
                year = "2021",
                description = "Electric scooter",
                salePrice = "400",
                rentalPrice = "40",
                region = "Lima",
                imagen = "https://example.com/scooter_image.jpg",
                userId = 1
            )
        )

        // Configurar el adaptador para el RecyclerView
        vehicleAdapter = VehicleAdapter(vehicleList)
        recyclerView.adapter = vehicleAdapter
    }
}