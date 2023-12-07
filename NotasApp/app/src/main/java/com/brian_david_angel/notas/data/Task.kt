package com.brian_david_angel.notas.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.brian_david_angel.notas.Constants
import java.text.SimpleDateFormat
import java.util.Date

@Entity(tableName = Constants.TABLE_NAME_TASK, indices = [Index(value = ["id"], unique = true)])
data class Task constructor(
    @PrimaryKey(autoGenerate = true)    val id: Int? = null,
    @ColumnInfo(name = "task")          val task: String,
    @ColumnInfo(name = "title")         val title: String,
    @ColumnInfo(name = "dateUpdated")   val dateUpdated: String,
    @ColumnInfo(name = "hourUpdated")   val hourUpdated: String,
    @ColumnInfo(name = "imageUri")     val imageUri: String? = null
)
