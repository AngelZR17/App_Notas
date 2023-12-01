package com.brian_david_angel.notas.app

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.room.Room
import com.brian_david_angel.notas.Constants
import com.brian_david_angel.notas.data.NotesDao
import com.brian_david_angel.notas.data.NotesDatabase
import com.brian_david_angel.notas.data.TaskDao

class NotesApplication : Application() {
    private var db : NotesDatabase? = null

    init {
        instance = this
    }

    private fun getDb(): NotesDatabase {
        return if (db != null){
            db!!
        } else {
            db = Room.databaseBuilder(
                instance!!.applicationContext,
                NotesDatabase::class.java, Constants.DATABASE_NAME
            ).fallbackToDestructiveMigration()
                .build()
            db!!
        }
    }


    companion object {
        private var instance: NotesApplication? = null

        fun getDao(): NotesDao {
            return instance!!.getDb().NotesDao()
        }

        fun getDaoTask(): TaskDao {
            return instance!!.getDb().TaskDao()
        }

        fun getUriPermission(uri: Uri){
            instance!!.applicationContext.contentResolver.takePersistableUriPermission(
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        }

    }
}