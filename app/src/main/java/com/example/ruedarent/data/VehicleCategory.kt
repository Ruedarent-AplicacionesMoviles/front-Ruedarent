package com.example.ruedarent.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicle_category") // Asegúrate de especificar el nombre correcto de la tabla
data class VehicleCategory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Clave primaria
    val name: String,
    val description: String?,
    val image: String?,
    val userId: Int
)



