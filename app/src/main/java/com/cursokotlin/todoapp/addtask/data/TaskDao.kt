package com.cursokotlin.todoapp.addtask.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    //hacemos las querys, retorna un flow
    @Query("SELECT * from TaskEntity")
    fun getTask(): Flow<List<TaskEntity>>

    @Insert
    suspend fun addTask(item: TaskEntity)
}