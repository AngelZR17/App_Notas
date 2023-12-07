package com.brian_david_angel.notas

import com.brian_david_angel.notas.data.Note
import com.brian_david_angel.notas.data.Task


object Constants {
    const val TABLE_NAME = "Notes"
    const val TABLE_NAME_TASK = "Task"
    const val DATABASE_NAME = "NotesDatabase"
    var NOTE_EDIT=0;


    fun List<Note>?.orPlaceHolderList(): List<Note> {
        fun placeHolderList(): List<Note> {
            return listOf(Note(id = 0, title = "No Notes Found", note = "Please create a note.", dateUpdated = ""))
        }
        return if (this != null && this.isNotEmpty()){
            this
        } else placeHolderList()
    }

    fun List<Task>?.orPlaceHolderListTask(): List<Task> {
        fun placeHolderList(): List<Task> {
            return listOf(Task(id = 0, title = "No Notes Found", task = "Please create a note.", dateUpdated = "", hourUpdated = ""))
        }
        return if (this != null && this.isNotEmpty()){
            this
        } else placeHolderList()
    }

    val noteDetailPlaceHolder = Note(note = "Cannot find note details", id = 0, title = "Cannot find note details")
    val taskDetailPlaceHolder = Task(task = "Cannot find note details", id = 0, title = "Cannot find note details", dateUpdated = "", hourUpdated = "")
}