package com.example.ruedarent.data.sampledata

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao //solo reconoce el tipo list y cursor
interface PlanDao {

    @Query("SELECT * FROM `plan`")
    fun getAll(): List<Plan>

    @Insert
    fun insertPlan(vararg plan: Plan)

    @Delete
    fun deletePlan(vararg plan: Plan)

    @Update
    fun updatePlan(vararg plan: Plan)




}