package com.example.ruedarent

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ruedarent.data.DatabaseBuilder
import com.example.ruedarent.data.Vehicle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditVehicleActivity : AppCompatActivity() {
    private lateinit var backArrow: ImageView
    private lateinit var s_Vehicle: Spinner
    private lateinit var etYear: EditText
    private lateinit var etDescription: EditText
    private lateinit var etSale: EditText
    private lateinit var etRent: EditText
    private lateinit var btEdit: Button

    private var vehicleList: List<Vehicle> = listOf()

    // Variable para almacenar el categoryId
    private var categoryId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_vehicle)

        // Obtener el categoryId desde el Intent
        categoryId = intent.getIntExtra("CATEGORY_ID", -1)

        if (categoryId == -1) {
            // Si el categoryId no es válido, manejar el error
            Toast.makeText(this, "Categoría no válida", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Vinculación de vistas
        s_Vehicle = findViewById(R.id.s_Vehicle)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.vehicle_options,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        s_Vehicle.adapter = adapter

        // Inicializar base de datos
        val db = DatabaseBuilder.getInstance(applicationContext)

        // Cargar todos los vehículos desde la base de datos
        CoroutineScope(Dispatchers.IO).launch {
            vehicleList = db.vehicleDao().getAllVehicles()  // Obtener todos los vehículos
        }

        // Vinculación de vistas
        backArrow = findViewById(R.id.backButton)
        etYear = findViewById(R.id.etYear)
        etDescription = findViewById(R.id.etDescription)
        etSale = findViewById(R.id.etSale)
        etRent = findViewById(R.id.etRental)
        btEdit = findViewById(R.id.btEdit)

        // Listener para el botón de edición
        btEdit.setOnClickListener {
            val yearString = etYear.text.toString()
            val description = etDescription.text.toString()
            val sale = etSale.text.toString()
            val rent = etRent.text.toString()
            val selectedVehicleType = s_Vehicle.selectedItem.toString()

            // Validación del año
            val year = yearString.toIntOrNull()
            if (year == null || year < 1970 || year > 2024) {
                Toast.makeText(this, "Por favor, introduce un año entre 1970 y 2024", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Verificar si todos los campos están completos
            if (description.isNotEmpty() && sale.isNotEmpty() && rent.isNotEmpty() && selectedVehicleType.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    // Verifica si ya existe un vehículo para editar
                    if (vehicleList.isNotEmpty()) {
                        // Si ya tienes un vehículo, actualízalo
                        val vehicleToUpdate = vehicleList[0].copy(
                            year = year.toString(),
                            description = description,
                            salePrice = sale,
                            rentalPrice = rent,
                            type = selectedVehicleType,
                            categoryId = categoryId // Asegúrate de pasar el categoryId
                        )
                        db.vehicleDao().update(vehicleToUpdate)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@EditVehicleActivity, "Vehicle updated successfully", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Crear un nuevo vehículo si no existe
                        val newVehicle = Vehicle(
                            type = selectedVehicleType,
                            year = year.toString(),
                            description = description,
                            salePrice = sale,
                            rentalPrice = rent,
                            region = "Lima, Peru",
                            imagen = "https://ibb.co/2W75S2L",
                            userId = 1, // Ajusta según sea necesario
                            categoryId = categoryId // Pasar el categoryId al crear el vehículo
                        )
                        db.vehicleDao().insert(newVehicle)
                    }
                }
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Listener para la flecha de atrás
        backArrow.setOnClickListener {
            finish() // Cierra la actividad actual
        }
    }
}
