package com.cursokotlin.todoapp.addtask.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import android.content.Context
import androidx.room.Room
import com.cursokotlin.todoapp.addtask.data.TaskDao
import com.cursokotlin.todoapp.addtask.data.TodoDatabase

//Esta bbdd tiene que ser un singleton
@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    //Vamos a tener que proveernos el dao y la BBDD
    //Esto necesita un contexto por lo tanto se le pasa un applicationContext
    //Devolviendo un TodoDatabase
    //creamos la BBDD aqui
    @Provides
    @Singleton
    fun provideTodoDatabase(@ApplicationContext appContext:Context): TodoDatabase{
        return Room.databaseBuilder(appContext, TodoDatabase::class.java, "TaskDatabase").build()
    }

    //Interfaz del dao y esto recibe la BBDD
    @Provides
    fun provideTaskDao(todoDatabase: TodoDatabase):TaskDao {
        return todoDatabase.taskDao()
    }
}