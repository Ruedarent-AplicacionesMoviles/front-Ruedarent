package com.example.ruedarent

import VehicleAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ruedarent.data.AppDatabase
import com.example.ruedarent.data.DatabaseBuilder
import com.example.ruedarent.data.Vehicle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VehicleListActivity : AppCompatActivity() {

    private lateinit var vehicleAdapter: VehicleAdapter
    private lateinit var vehicleList: List<Vehicle>
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_list)

        db = DatabaseBuilder.getInstance(applicationContext)

        val recyclerView = findViewById<RecyclerView>(R.id.rvVehicles)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Cargar los vehículos desde la base de datos
        CoroutineScope(Dispatchers.IO).launch {
            vehicleList = db.vehicleDao().getAll()
            withContext(Dispatchers.Main) {
                vehicleAdapter = VehicleAdapter(vehicleList) { vehicle ->
                    // Al hacer clic en una card, abrir VehicleDetailActivity con el ID del vehículo
                    val intent = Intent(this@VehicleListActivity, VehicleDetailActivity::class.java)
                    intent.putExtra("vehicle_id", vehicle.id)
                    startActivity(intent)
                }
                recyclerView.adapter = vehicleAdapter
            }
        }

        // Configurar el botón para agregar vehículos
        val addButton = findViewById<Button>(R.id.btnAddVehicle)
        addButton.setOnClickListener {
            val intent = Intent(this, AddVehicleActivity::class.java)
            startActivity(intent) // Llevar al formulario para agregar vehículos
        }
    }

    override fun onResume() {
        super.onResume()
        // Recargar los vehículos desde la base de datos cada vez que se vuelve a la actividad
        CoroutineScope(Dispatchers.IO).launch {
            vehicleList = db.vehicleDao().getAll()
            withContext(Dispatchers.Main) {
                vehicleAdapter = VehicleAdapter(vehicleList) { vehicle ->
                    // Manejar clic en la card
                    val intent = Intent(this@VehicleListActivity, VehicleDetailActivity::class.java)
                    intent.putExtra("vehicle_id", vehicle.id)
                    startActivity(intent)
                }
                findViewById<RecyclerView>(R.id.rvVehicles).adapter = vehicleAdapter
            }
        }
    }
}
