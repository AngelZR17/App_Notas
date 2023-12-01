package com.brian_david_angel.notas.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {

    @Query("SELECT * FROM Task WHERE task.id=:id")
    suspend fun getTaskById(id: Int) : Task?

    @Query("SELECT * FROM Task ORDER BY dateUpdated DESC")
    fun getTask() : LiveData<List<Task>>

    @Delete
    fun deleteTask(task: Task) : Int

    @Update
    fun updateTask(task: Task) : Int

    @Insert
    fun insertTask(task: Task)

}