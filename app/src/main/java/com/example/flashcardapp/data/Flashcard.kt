package com.example.flashcardapp.data

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "flashcards")
data class Flashcard(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val question: String,
    val answer: String
)
