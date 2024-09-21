package com.example.ruedarent

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class EditVehicleActivity : AppCompatActivity() {
    private lateinit var backarrow : ImageView
    private lateinit var s_Vehicle : Spinner
    private lateinit var etYear : EditText
    private lateinit var etDescription : EditText
    private lateinit var etSale : EditText
    private lateinit var etRent : EditText
    private lateinit var btEdit : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        s_Vehicle = findViewById(R.id.s_Vehicle)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.vehicle_options,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        s_Vehicle.adapter = adapter


        backarrow = findViewById(R.id.backButton)
        etYear = findViewById(R.id.etYear)
        etDescription = findViewById(R.id.etDescription)
        etSale = findViewById(R.id.etSale)
        etRent = findViewById(R.id.etRental)
        btEdit = findViewById(R.id.btEdit)

        backarrow.setOnClickListener {
            finish()
        }
    }
}