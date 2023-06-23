package com.cursokotlin.todoapp.addtask.data

import androidx.room.Database
import androidx.room.RoomDatabase

//Para crear la base de datos
@Database(entities = [TaskEntity::class], version = 1)
abstract class TodoDatabase:RoomDatabase() {
    //Aqui se pone los DAO es una interfaz
    //El dao nos permite realizar llamadas sql de room
    //para meterlo en la BBDD con esto tendriamos creada la BBDD
    // Esto esta para que automaticamente te devuelva el dao
    abstract fun taskDao():TaskDao
}