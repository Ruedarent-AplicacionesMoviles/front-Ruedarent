package com.example.ruedarent.notifications.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
data class Notifications (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo
    var title: String? = null,
    @ColumnInfo
    var message: String? = null,

)