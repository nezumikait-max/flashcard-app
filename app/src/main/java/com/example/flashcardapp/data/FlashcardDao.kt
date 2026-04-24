package com.example.flashcardapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashcardDao {
    @Query("SELECT * FROM flashcards ORDER BY id ASC")
    fun getAllFlashcards(): Flow<List<Flashcard>>

    @Insert
    suspend fun insert(flashcard: Flashcard)

    @Update
    suspend fun update(flashcard: Flashcard)

    @Delete
    suspend fun delete(flashcard: Flashcard)
}