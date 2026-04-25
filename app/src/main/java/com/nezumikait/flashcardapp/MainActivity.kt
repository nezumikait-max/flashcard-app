package com.nezumikait.flashcardapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nezumikait.flashcardapp.ui.theme.FlashcardAppTheme

// ── Sample data ───────────────────────────────────────────────
data class Flashcard(val question: String, val answer: String)

val sampleDeck = listOf(
    Flashcard("What is Jetpack Compose?", "Android's modern declarative UI toolkit built with Kotlin."),
    Flashcard("What does 'remember' do in Compose?", "Stores a value across recompositions without re-initializing it."),
    Flashcard("What is a Composable function?", "A function annotated with @Composable that describes a piece of UI."),
    Flashcard("What is State Hoisting?", "Moving state up to a caller so a composable becomes stateless and reusable."),
    Flashcard("What is recomposition?", "The process where Compose re-runs composables whose inputs have changed."),
)

// ── Main Activity ─────────────────────────────────────────────
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlashcardAppTheme {
                FlashcardScreen(deck = sampleDeck)
            }
        }
    }
}

// ── Flashcard Screen ──────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashcardScreen(deck: List<Flashcard>) {
    var currentIndex by remember { mutableIntStateOf(0) }
    var isFlipped     by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Flashcard Deck") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically)
        ) {
            // Progress
            Text(
                text = "${currentIndex + 1} / ${deck.size}",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.outline
            )
            LinearProgressIndicator(
                progress = { (currentIndex + 1).toFloat() / deck.size },
                modifier = Modifier.fillMaxWidth()
            )

            // Card
            FlipCard(
                card  = deck[currentIndex],
                flipped = isFlipped,
                onClick = { isFlipped = !isFlipped }
            )

            Text(
                text = if (isFlipped) "Showing answer — tap to flip back" else "Tap card to reveal answer",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline
            )

            // Navigation
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        currentIndex = (currentIndex - 1 + deck.size) % deck.size
                        isFlipped = false
                    },
                    modifier = Modifier.weight(1f)
                ) { Text("← Prev") }

                Button(
                    onClick = {
                        currentIndex = (currentIndex + 1) % deck.size
                        isFlipped = false
                    },
                    modifier = Modifier.weight(1f)
                ) { Text("Next →") }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            val context = LocalContext.current
            Button(
                onClick = {
                    if (!Settings.canDrawOverlays(context)) {
                        val intent = Intent(
                            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:${context.packageName}")
                        )
                        context.startActivity(intent)
                    } else {
                        val intent = Intent(context, FloatingService::class.java)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            context.startForegroundService(intent)
                        } else {
                            context.startService(intent)
                        }
                    }
                }
            ) {
                Text("Start Floating Window")
            }
        }
    }
}

// ── Flip Card ─────────────────────────────────────────────────
@Composable
fun FlipCard(card: Flashcard, flipped: Boolean, onClick: () -> Unit) {
    val rotation by animateFloatAsState(
        targetValue = if (flipped) 180f else 0f,
        animationSpec = tween(durationMillis = 400),
        label = "card-flip"
    )

    val isFrontVisible = rotation < 90f

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .graphicsLayer { rotationY = rotation; cameraDistance = 12f * density }
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isFrontVisible)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .graphicsLayer { if (!isFrontVisible) rotationY = 180f },
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = if (isFrontVisible) "Q" else "A",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.outline
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = if (isFrontVisible) card.question else card.answer,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}
