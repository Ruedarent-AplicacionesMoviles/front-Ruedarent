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
    private lateinit var backarrow : ImageView
    private lateinit var s_Vehicle : Spinner
    private lateinit var etYear : EditText
    private lateinit var etDescription : EditText
    private lateinit var etSale : EditText
    private lateinit var etRent : EditText
    private lateinit var btEdit : Button

    private var vehicleList: List<Vehicle> = listOf()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_vehicle)

        s_Vehicle = findViewById(R.id.s_Vehicle)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.vehicle_options,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        s_Vehicle.adapter = adapter


        val db = DatabaseBuilder.getInstance(applicationContext)


        //creating database
        CoroutineScope(Dispatchers.IO).launch {
            vehicleList = db.vehicleDao().getAll()  // Obtener todos los vehículos
        }


        backarrow = findViewById(R.id.backButton)
        etYear = findViewById(R.id.etYear)
        etDescription = findViewById(R.id.etDescription)
        etSale = findViewById(R.id.etSale)
        etRent = findViewById(R.id.etRental)
        btEdit = findViewById(R.id.btEdit)

        btEdit.setOnClickListener {
            val yearString = etYear.text.toString()
            val description = etDescription.text.toString()
            val sale = etSale.text.toString()
            val rent = etRent.text.toString()
            val selectedVehicleType = s_Vehicle.selectedItem.toString()

            val year = yearString.toIntOrNull()
            if (year == null || year < 1970 || year > 2024) {
                Toast.makeText(this, "Por favor, introduce un año entre 1970 y 2024", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if(description.isNotEmpty() && sale.isNotEmpty() && rent.isNotEmpty() && selectedVehicleType.isNotEmpty()){
                CoroutineScope(Dispatchers.IO).launch {
                    // Verifica si estás editando un vehículo existente o creando uno nuevo
                    if (vehicleList.isNotEmpty()) {
                        // Si ya tienes un vehículo, actualízalo
                        val vehicleToUpdate = vehicleList[0].copy(
                            year = year.toString(),
                            description = description,
                            salePrice = sale,
                            rentalPrice = rent,
                            type = selectedVehicleType
                        )
                        db.vehicleDao().update(vehicleToUpdate)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@EditVehicleActivity, "Vehicle updated successfully", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        val newVehicle = Vehicle(
                            type = selectedVehicleType,
                            year = year.toString(),
                            description = description,
                            salePrice = sale,
                            rentalPrice = rent,
                            region = "Lima, Peru",
                            imagen = "https://ibb.co/2W75S2L",
                            userId = 1
                        )
                        db.vehicleDao().insert(newVehicle)
                    }
                }
            }

        }

        backarrow.setOnClickListener {
            finish()
        }
    }
}