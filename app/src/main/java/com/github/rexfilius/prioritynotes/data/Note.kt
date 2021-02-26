package com.github.rexfilius.prioritynotes.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * By default, Room uses the class name as the database table name. If you want the table to
 * have a different name, set the tableName property of the @Entity annotation
 */
@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var title: String,
    var description: String,
    var priority: Int
)