// VehicleDao.kt
package com.example.ruedarent.data

import androidx.room.*

@Dao
interface VehicleDao {

    // Métodos existentes para Vehicle

    @Insert
    suspend fun insert(vehicle: Vehicle)

    @Update
    suspend fun update(vehicle: Vehicle)

    @Delete
    suspend fun delete(vehicle: Vehicle)

    @Query("SELECT * FROM vehicle WHERE id = :vehicleId LIMIT 1")
    suspend fun getVehicleById(vehicleId: Int): Vehicle?

    @Query("SELECT * FROM vehicle WHERE categoryId = :categoryId")
    suspend fun getVehiclesByCategory(categoryId: Int): List<Vehicle>

    // Métodos para VehicleCategory

    @Insert
    suspend fun insertCategory(category: VehicleCategory)

    @Update
    suspend fun updateCategory(category: VehicleCategory)

    @Delete
    suspend fun deleteCategory(category: VehicleCategory)

    @Query("SELECT * FROM vehicle_category WHERE userId = :userId")
    suspend fun getCategoriesByUser(userId: Int): List<VehicleCategory>

    @Query("SELECT * FROM vehicle_category WHERE id = :categoryId LIMIT 1")
    suspend fun getCategoryById(categoryId: Int): VehicleCategory?

    @Query("SELECT * FROM vehicle")
    fun getAllVehicles(): List<Vehicle>
}
