package com.example.flashcardapp

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import com.example.flashcardapp.data.Flashcard
import com.example.flashcardapp.data.FlashcardDao

@Database(entities = [Flashcard::class], version = 1, exportSchema = false)
abstract class FlashcardDatabase : RoomDatabase() {
    abstract fun flashcardDao(): FlashcardDao

    companion object {
        @Volatile
        private var Instance: FlashcardDatabase? = null

        fun getDatabase(context: Context): FlashcardDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, FlashcardDatabase::class.java, "flashcard_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
