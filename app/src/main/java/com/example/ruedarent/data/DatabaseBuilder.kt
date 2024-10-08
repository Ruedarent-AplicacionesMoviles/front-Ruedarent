package com.example.ruedarent.data

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {

    private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
        if (INSTANCE == null) {
            synchronized(AppDatabase::class) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ruedarent_db4" // Nombre de la base de datos
                )
                    .fallbackToDestructiveMigration()  // Aquí se agrega el fallback
                    .build()
            }
        }
        return INSTANCE!!
    }
}