package com.example.ruedarent

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ruedarent.data.AppDatabase
import com.example.ruedarent.data.DatabaseBuilder
import com.example.ruedarent.data.Vehicle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VehicleDetailActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var vehicle: Vehicle
    private lateinit var btnEdit: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_detail)

        db = DatabaseBuilder.getInstance(applicationContext)

        // Obtener el ID del vehículo desde el Intent
        val vehicleId = intent.getIntExtra("vehicle_id", -1)

        // Cargar los detalles del vehículo
        CoroutineScope(Dispatchers.IO).launch {
            vehicle = db.vehicleDao().getVehicleById(vehicleId) // Necesitas agregar este método a `VehicleDao`

            withContext(Dispatchers.Main) {
                // Mostrar los detalles en la vista
                findViewById<TextView>(R.id.tv_Vehicle).text = vehicle.type
                findViewById<TextView>(R.id.tv_Year).text = vehicle.year
                findViewById<TextView>(R.id.tv_Description).text = vehicle.description
                findViewById<TextView>(R.id.tv_SaleP).text = vehicle.salePrice
                findViewById<TextView>(R.id.tv_Rental).text = vehicle.rentalPrice
            }
        }

        // Configurar el botón de edición
        btnEdit = findViewById(R.id.btEdit)
        btnEdit.setOnClickListener {
            val intent = Intent(this, EditVehicleActivity::class.java)
            intent.putExtra("vehicle_id", vehicleId)
            startActivity(intent)
        }

        // Configurar el botón de eliminación
        btnDelete = findViewById(R.id.btDelete)
        btnDelete.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.vehicleDao().delete(vehicle)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@VehicleDetailActivity, "Vehicle deleted", Toast.LENGTH_SHORT).show()
                    finish() // Volver a la lista después de eliminar
                }
            }
        }
    }
}
