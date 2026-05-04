Hack or Myth?

This is a simple, lightweight Android app built with Jetpack Compose. It’s a "Life Hack" quiz where you have to decide if a viral tip is actually a genius hack or just an urban myth.
I built this to practice modern Android development—specifically moving away from the old, messy "multiple activities" way of doing things and using a single-activity architecture instead.
 
Features

Uses a single MainActivity with a Compose-based navigation logic. Much cleaner, much faster.
Instantly tells you if you're right or wrong.
At the end of the quiz, you can open a review dialog to read the why behind each answer.
Uses Material3 components for a modern look and feel.

Components
Language: Kotlin
Toolkit: Jetpack Compose (UI design)
Architecture: State-driven navigation 

How it’s structured
QuestionRepo: This is where the data lives. If you want to add more hacks, you just drop them into the questions list here.
AppNavigation: The "brain" of the app. It manages the state and decides whether to show the Welcome, Quiz, or Score screen.
Screens: Each UI state (WelcomeScreen, QuizScreen, ScoreScreen) is its own Composable function, making it super easy to tweak the design without breaking the logic.
The syntax was simplified to make it easier for Gradle to process and for other devs to read at a glance.
