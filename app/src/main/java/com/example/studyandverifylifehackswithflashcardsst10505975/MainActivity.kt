package com.example.studyandverifylifehackswithflashcardsst10505975

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//I removed all other activities and used a Jetpack Compose
//I switched to one main.activity to clean the syntax and have an organized manner rather end different activities or functions
data class Question(
    val statement: String,
    val isHack: Boolean,
    val explanation: String
)

object QuestionRepo {
        val questions = listOf(
            Question("Putting an onion in your sock cures a cold.", false, "This is an urban myth with no medical basis."),
            Question("Use a binder clip to organize loose cables on your desk.", true, "Binder clips are a classic hack for cable management!"),
            Question("Rice can save a water-damaged phone.", false, "It's a myth; silica gel or open air is actually more effective."),
            Question("Freeze grapes to chill wine without watering it down.", true, "Genuine hack! It keeps drinks cold and tasty.")
        )
    }

//The main.activity logic for the welcome screen.
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}

//The logic for the navigation between different pages
//This is syntax provide much easier communication for Gradle to understand
@Composable
fun AppNavigation() {
    var currentScreen by remember { mutableStateOf("welcome") }
    var score by remember { mutableStateOf(0) }

    when (currentScreen) {
        "welcome" -> WelcomeScreen(onStartClick = { currentScreen = "quiz" })
        "quiz" -> QuizScreen(onFinish = { finalScore ->
            score = finalScore
            currentScreen = "score"
        })
        "score" -> ScoreScreen(
            score = score,
            total = QuestionRepo.questions.size,
            onRestart = { currentScreen = "welcome" }
        )
    }
}

//The ui design for the welcome screen
@Composable
fun WelcomeScreen(onStartClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to Hack or Myth!", fontSize = 26.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Test your knowledge on viral life hacks.", fontSize = 18.sp, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(40.dp))
        Button(onClick = onStartClick, modifier = Modifier.width(200.dp).height(50.dp)) {
            Text("Start Quiz")
        }
    }
}

//The ui design for the question screen
@Composable
fun QuizScreen(onFinish: (Int) -> Unit) {
    var currentIndex by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var feedback by remember { mutableStateOf("") }
    var isAnswered by remember { mutableStateOf(false) }

    val current = QuestionRepo.questions[currentIndex]

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Question ${currentIndex + 1}/${QuestionRepo.questions.size}", color = Color.Gray)
        Spacer(modifier = Modifier.height(20.dp))
        Text(current.statement, fontSize = 22.sp, textAlign = TextAlign.Center, modifier = Modifier.heightIn(min = 100.dp))

        Spacer(modifier = Modifier.height(30.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(
                onClick = {
                    if (current.isHack) score++
                    feedback = if (current.isHack) "Correct!" else "Wrong!"
                    isAnswered = true
                },
                enabled = !isAnswered,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) { Text("Hack") }

            Button(
                onClick = {
                    if (!current.isHack) score++
                    feedback = if (!current.isHack) "Correct!" else "Wrong!"
                    isAnswered = true
                },
                enabled = !isAnswered,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF44336))
            ) { Text("Myth") }
        }

        Text(feedback, modifier = Modifier.padding(top = 20.dp), fontWeight = FontWeight.Bold)

        if (isAnswered) {
            Button(
                modifier = Modifier.padding(top = 30.dp),
                onClick = {
                    if (currentIndex < QuestionRepo.questions.size - 1) {
                        currentIndex++
                        isAnswered = false
                        feedback = ""
                    } else {
                        onFinish(score)
                    }
                }
            ) {
                Text(if (currentIndex < QuestionRepo.questions.size - 1) "Next Question" else "See Results")
            }
        }
    }
}

//The ui design for the score screen
@Composable
fun ScoreScreen(score: Int, total: Int, onRestart: () -> Unit) {
    var showReview by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Final Score", fontSize = 20.sp)
        Text("$score / $total", fontSize = 60.sp, fontWeight = FontWeight.ExtraBold)

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { showReview = true }, modifier = Modifier.fillMaxWidth()) {
            Text("Review Hacks")
        }
        OutlinedButton(onClick = onRestart, modifier = Modifier.fillMaxWidth()) {
            Text("Restart Quiz")
        }
    }

    if (showReview) {
        AlertDialog(
            onDismissRequest = { showReview = false },
            title = { Text("Hack Review") },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    QuestionRepo.questions.forEach { q ->
                        Text("Statement: ${q.statement}", fontWeight = FontWeight.Bold)
                        Text("Answer: ${if (q.isHack) "Real Hack" else "Myth"}", color = Color.Blue)
                        Text("Why: ${q.explanation}\n", fontSize = 14.sp)
                    }
                }
            },
            confirmButton = { TextButton(onClick = { showReview = false }) { Text("Close") } }
        )
    }
}