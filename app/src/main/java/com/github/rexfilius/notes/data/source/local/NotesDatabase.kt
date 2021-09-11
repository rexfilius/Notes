package com.github.rexfilius.notes.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

    /**
     * With ServiceLocator pattern for dependency injection, this block of code
     * is no longer needed
     */
    /*companion object {

        /**
         * the value of a Volatile variable will never be cached, and all reads and
         * write will be done to and from the main memory
         */
        @Volatile
        private var INSTANCE: NotesDatabase? = null

        /**
         * wrapping the code with synchronized means that only one thread of execution
         * at a time can enter this block of code, which makes sure the database
         * only gets initialized once
         */
        fun getInstance(context: Context): NotesDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance =
                        Room.databaseBuilder(
                            context.applicationContext,
                            NotesDatabase::class.java,
                            "notes_database"
                        ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }*/

}