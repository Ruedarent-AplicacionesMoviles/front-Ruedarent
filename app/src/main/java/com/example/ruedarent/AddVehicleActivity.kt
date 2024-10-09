package com.example.ruedarent

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
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

class AddVehicleActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var spinnerVehicleType: Spinner
    private lateinit var etYear: EditText
    private lateinit var etDescription: EditText
    private lateinit var etSalePrice: EditText
    private lateinit var etRentalPrice: EditText
    private lateinit var btnSaveVehicle: Button

    private var categoryId: Int = -1 // Almacenar categoryId

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_vehicle)

        db = DatabaseBuilder.getInstance(applicationContext)

        // Recibir el categoryId pasado desde la actividad anterior
        categoryId = intent.getIntExtra("CATEGORY_ID", -1)

        if (categoryId == -1) {
            // Manejar el error de categoryId no válido
            Toast.makeText(this, "Invalid category selected", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Vinculación de vistas
        spinnerVehicleType = findViewById(R.id.spinnerVehicleType)
        etYear = findViewById(R.id.etYear)
        etDescription = findViewById(R.id.etDescription)
        etSalePrice = findViewById(R.id.etSalePrice)
        etRentalPrice = findViewById(R.id.etRentalPrice)
        btnSaveVehicle = findViewById(R.id.btnSaveVehicle)

        // Configurar spinner con tipos de vehículos
        ArrayAdapter.createFromResource(
            this,
            R.array.vehicle_types, // Define los tipos de vehículos en strings.xml
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerVehicleType.adapter = adapter
        }

        btnSaveVehicle.setOnClickListener {
            val type = spinnerVehicleType.selectedItem.toString()
            val year = etYear.text.toString()
            val description = etDescription.text.toString()
            val salePrice = etSalePrice.text.toString()
            val rentalPrice = etRentalPrice.text.toString()

            if (type.isNotEmpty() && year.isNotEmpty() && description.isNotEmpty() && salePrice.isNotEmpty() && rentalPrice.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    val newVehicle = Vehicle(
                        type = type,
                        year = year,
                        description = description,
                        salePrice = salePrice,
                        rentalPrice = rentalPrice,
                        region = "Lima",  // Ajusta si es necesario
                        imagen = "bike_image.png",  // Siempre usamos la imagen bike_image.png
                        userId = 1,  // Ajusta según sea necesario
                        categoryId = categoryId  // Pasamos el categoryId aquí
                    )
                    db.vehicleDao().insert(newVehicle)

                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@AddVehicleActivity, "Vehicle added successfully", Toast.LENGTH_SHORT).show()
                        finish()  // Vuelve a la lista de vehículos
                    }
                }
            } else {
                Toast.makeText(this@AddVehicleActivity, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}



