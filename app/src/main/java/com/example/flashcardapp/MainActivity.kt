package com.example.flashcardapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.flashcardapp.ui.theme.FlashcardAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlashcardAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FlashcardApp()
                }
            }
        }
    }
}

@Composable
fun FlashcardApp() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues -> // Added paddingValues parameter
        NavHost(navController = navController, startDestination = "study", Modifier.padding(paddingValues)) { // Added Modifier.padding
            composable("study") { StudyScreen() }
            composable("deck") { DeckScreen() }
            composable("settings") { SettingsScreen() }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf("Study", "Deck", "Settings")
    NavigationBar {
        items.forEach { item -> // Changed from forEachIndexed to forEach for simplicity
            NavigationBarItem(
                icon = {
                    // TODO: Add actual icons
                },
                label = { Text(item) },
                selected = false, // TODO: Implement proper selection
                onClick = { navController.navigate(item.lowercase()) }
            )
        }
    }
}

@Composable
fun StudyScreen() {
    Text(text = "Study Screen")
}

// DeckScreen and SettingsScreen are defined elsewhere and will be imported.
// Ensure DeckScreen and SettingsScreen are correctly defined and imported.
