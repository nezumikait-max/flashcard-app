package com.example.flashcardapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.flashcardapp.data.Flashcard

@Composable
fun FlashcardCard(flashcard: Flashcard, onEdit: (Flashcard) -> Unit, onDelete: (Flashcard) -> Unit) {
    Card(modifier = Modifier.padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Question: ${flashcard.question}")
            Text(text = "Answer: ${flashcard.answer}")
            Row {
                Button(onClick = { onEdit(flashcard) }) {
                    Text("Edit")
                }
                Button(onClick = { onDelete(flashcard) }) {
                    Text("Delete")
                }
            }
        }
    }
}