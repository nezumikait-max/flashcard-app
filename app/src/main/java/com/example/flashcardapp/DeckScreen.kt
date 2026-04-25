package com.example.flashcardapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flashcardapp.data.Flashcard
import com.example.flashcardapp.data.FlashcardViewModel
import com.example.flashcardapp.data.FlashcardViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeckScreen(viewModel: FlashcardViewModel = viewModel(factory = FlashcardViewModelFactory(FlashcardDatabase.getDatabase(LocalContext.current).flashcardDao()))) {
    val flashcards by viewModel.allFlashcards.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var editingFlashcard by remember { mutableStateOf<Flashcard?>(null) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true; editingFlashcard = null }) {
                Text(\"Add Flashcard\")
            }
        }
    ) {\ paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)) {
            if (flashcards.isEmpty()) {
                Text(\"No flashcards yet. Add one!\")
            } else {
                LazyColumn {
                    items(flashcards) {\ flashcard ->
                        FlashcardCard(
                            flashcard = flashcard,
                            onEdit = { cardToEdit ->
                                editingFlashcard = cardToEdit
                                showDialog = true
                            },
                            onDelete = { cardToDelete ->
                                viewModel.deleteFlashcard(cardToDelete)
                            }
                        )
                    }
                }
            }
        }
    }

    if (showDialog) {
        FlashcardDialog(
            flashcard = editingFlashcard,
            onDismiss = { showDialog = false },
            onConfirm = {\ question, answer ->
                if (editingFlashcard == null) {
                    viewModel.insertFlashcard(Flashcard(question = question, answer = answer))
                } else {
                    editingFlashcard?.copy(question = question, answer = answer)?.let {
                        viewModel.updateFlashcard(it)
                    }
                }
                showDialog = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashcardDialog(flashcard: Flashcard?, onDismiss: () -> Unit, onConfirm: (String, String) -> Unit) {
    var question by remember { mutableStateOf(flashcard?.question ?: \"\") }
    var answer by remember { mutableStateOf(flashcard?.answer ?: \"\") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (flashcard == null) \"Add Flashcard\" else \"Edit Flashcard\") },
        text = {
            Column {
                OutlinedTextField(
                    value = question,
                    onValueChange = { question = it },
                    label = { Text(\"Question\") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = answer,
                    onValueChange = { answer = it },
                    label = { Text(\"Answer\") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(question, answer) }) {
                Text(\"Confirm\")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(\"Cancel\")
            }
        }
    )
}
