package com.example.ruedarent.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ruedarent.data.sampledata.Notifications
import com.example.ruedarent.data.sampledata.NotificationsDao
import com.example.ruedarent.data.sampledata.Plan
import com.example.ruedarent.data.sampledata.PlanDao

@Database(entities = [User::class, Plan::class, Vehicle::class, Notifications::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun planDao(): PlanDao
    abstract fun vehicleDao(): VehicleDao
    abstract fun notificationsDao(): NotificationsDao
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
