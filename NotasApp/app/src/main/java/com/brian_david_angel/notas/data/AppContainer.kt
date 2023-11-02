package com.brian_david_angel.notas.data

import android.content.Context

interface AppContainer {
    val itemsRepository : ItemsRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val itemsRepository: ItemsRepository by lazy {
        OfflineItemsRepository(NotesDatabase.getDatabase(context).itemDao())
    }
}