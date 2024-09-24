package com.example.ruedarent.plans.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("plan")
data class Plan (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    @ColumnInfo
    val plan_name: String,
    @ColumnInfo
    val plan_description: String,
    @ColumnInfo
    val plan_benefit1: String,
    @ColumnInfo
    val plan_benefit2: String,
)
