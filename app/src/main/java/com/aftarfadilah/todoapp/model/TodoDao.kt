package com.aftarfadilah.todoapp.model

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg todo:Todo)

    @Query("SELECT * FROM todo")
    fun selectAllTodo(): List<Todo>

    @Query("SELECT * FROM todo WHERE uuid= :id")
    fun selectTodo(id:Int): Todo

    @Delete
    fun deleteTodo(todo:Todo)

    @Database(entities = arrayOf(Todo::class), version =  1)
    abstract class TodoDatabase: RoomDatabase() {
        abstract fun todoDao(): TodoDao

        companion object {
            @Volatile private var instance: TodoDatabase ?= null
            private val LOCK = Any()

            fun buildDatabase(context: Context) =
                Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    "newtododb").build()
            operator fun invoke(context:Context) {
                if(instance!=null) {
                    synchronized(LOCK) {
                        instance ?: buildDatabase(context).also {
                            instance = it
                        }
                    }
                }
            }

        }
    }


}
