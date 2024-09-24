package com.example.ruedarent.notifications.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NotificationsDao {
    @Query("SELECT * FROM notifications")
    fun getAll(): List<Notifications>
    @Insert
    fun insert(vararg notification: Notifications)
    @Delete
    fun delete(vararg notification: Notifications)


}