package com.example.ruedarent.vehicle.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface VehicleDao {

    @Query("Select * from 'vehicle' ")
    fun getAll(): List<Vehicle>

    @Query("""
        SELECT vehicle.*, users.firstName, users.lastName
        FROM vehicle
        INNER JOIN users ON vehicle.userId = users.id
    """)
    fun getAllWithUserNames(): List<VehicleWithUser>


    @Insert
    fun insert(vehicle: Vehicle)

    @Delete
    fun delete(vehicle: Vehicle)

    @Update
    fun update(vehicle: Vehicle)
}