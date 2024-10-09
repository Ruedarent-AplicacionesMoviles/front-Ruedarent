// Vehicle.kt
package com.example.ruedarent.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "vehicle",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = VehicleCategory::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId"), Index("categoryId")]
)
data class Vehicle(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String,
    val year: String,
    val description: String,
    val salePrice: String,
    val rentalPrice: String,
    val region: String,
    val imagen: String,
    val userId: Int,
    val categoryId: Int // Nuevo campo para relacionar con VehicleCategory
)
