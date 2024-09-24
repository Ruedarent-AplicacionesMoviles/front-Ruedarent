package com.example.ruedarent.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ruedarent.loginregister.User.User
import com.example.ruedarent.loginregister.User.UserDao
import com.example.ruedarent.notifications.data.Notifications
import com.example.ruedarent.notifications.data.NotificationsDao
import com.example.ruedarent.plans.data.Plan
import com.example.ruedarent.plans.data.PlanDao
import com.example.ruedarent.vehicle.data.Vehicle
import com.example.ruedarent.vehicle.data.VehicleDao

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
