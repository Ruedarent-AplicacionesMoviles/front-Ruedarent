package com.example.ruedarent.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey


@Entity("vehicle",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ])
data class Vehicle (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,



    @ColumnInfo
    val type: String,

    @ColumnInfo
    val year: String,

    @ColumnInfo
    val description: String,

    @ColumnInfo
    val salePrice: String,

    @ColumnInfo
    val rentalPrice: String,

    @ColumnInfo
    val region: String,

    @ColumnInfo
    val imagen: String,

    @ColumnInfo(index = true)
    val userId: Int
)
