package com.github.rexfilius.notes.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.rexfilius.notes.model.Note

/**
 * - This class has one method that either creates an instance of the database
 * if the database doesn't exist, or returns a reference to an existing database
 * - NOTE: whenever you change the schema, you have to increase the version number,
 * and set exportSchema to false so as  not to keep schema version history backups.
 * exportSchema has to be set to true for apps in production
 */
@Database(entities = [Note::class], version = 5, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {

    abstract val notesDao: NotesDao

}