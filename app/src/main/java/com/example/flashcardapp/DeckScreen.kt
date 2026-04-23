package com.example.flashcardapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flashcardapp.data.FlashcardViewModel
import com.example.flashcardapp.data.FlashcardViewModelFactory

@Composable
fun DeckScreen(viewModel: FlashcardViewModel = viewModel(factory = FlashcardViewModelFactory(FlashcardDatabase.getDatabase(LocalContext.current).flashcardDao()))) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Deck Screen with CRUD functionality")
        // TODO: Implement UI for Create, Read, Update, Delete flashcards
    }
}