package com.brian_david_angel.notas

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.brian_david_angel.notas.data.Task
import com.brian_david_angel.notas.data.TaskDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(
    private val db: TaskDao,
) : ViewModel() {

    val task: LiveData<List<Task>> = db.getTask()

    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO){
            db.deleteTask(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO){
            db.updateTask(task)
        }
    }

    fun createTask(title: String, task: String, date: String, hour: String, image: String? = null) {
        viewModelScope.launch(Dispatchers.IO){
            db.insertTask(Task(title = title, task = task, dateUpdated = date, hourUpdated = hour, imageUri = image))
        }
    }

    suspend fun getTask(taskId : Int) : Task? {
        return db.getTaskById(taskId)
    }

}

class TaskViewModelFactory(
    private val db: TaskDao,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return  TaskViewModel(
            db = db,
        ) as T
    }

}