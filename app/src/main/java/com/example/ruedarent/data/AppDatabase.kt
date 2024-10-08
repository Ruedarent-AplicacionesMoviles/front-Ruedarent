package com.example.ruedarent.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class, Vehicle::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun vehicleDao(): VehicleDao
    /**
    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase{
            if (instance == null) {
                instance = Room.databaseBuilder(context, AppDatabase::class.java, "app_database").build()
            }
            return instance as AppDatabase
        }
    }
    **/
}
