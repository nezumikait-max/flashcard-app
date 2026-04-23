package com.example.flashcardapp.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.flashcardapp.Flashcard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FlashcardViewModel(private val repository: FlashcardRepository) : ViewModel() {

    private val _allFlashcards = MutableStateFlow<List<Flashcard>>(emptyList())
    val allFlashcards: StateFlow<List<Flashcard>> = _allFlashcards.asStateFlow()

    init {
        viewModelScope.launch {
            repository.allFlashcards.collect { flashcards ->
                _allFlashcards.value = flashcards
            }
        }
    }

    fun insertFlashcard(flashcard: Flashcard) = viewModelScope.launch {
        repository.insert(flashcard)
    }

    fun updateFlashcard(flashcard: Flashcard) = viewModelScope.launch {
        repository.update(flashcard)
    }

    fun deleteFlashcard(flashcard: Flashcard) = viewModelScope.launch {
        repository.delete(flashcard)
    }
}

class FlashcardViewModelFactory(private val repository: FlashcardRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlashcardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FlashcardViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}