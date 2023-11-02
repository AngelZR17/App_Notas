package com.brian_david_angel.notas.app

import android.app.Application
import com.brian_david_angel.notas.data.AppContainer
import com.brian_david_angel.notas.data.AppDataContainer

class NotesApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}