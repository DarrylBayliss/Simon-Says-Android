package com.darrylbayliss.simonsays

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.darrylbayliss.simonsays.domain.PlayViewModel
import com.darrylbayliss.simonsays.presentation.InstructionsScreen
import com.darrylbayliss.simonsays.presentation.PlayScreen
import com.darrylbayliss.simonsays.presentation.WelcomeScreen
import com.darrylbayliss.simonsays.ui.theme.SimonSaysTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            SimonSaysTheme {
                SimonSaysApp()
            }
        }
    }
}

@Composable
fun SimonSaysApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomeScreen(
                onNavigateToPlay = {
                    navController.navigate("play")
                },
                onNavigateToInstructions = {
                    navController.navigate("instructions")
                })
        }
        composable("play") { PlayScreen(hiltViewModel<PlayViewModel>()) }
        composable("instructions") { InstructionsScreen() }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SimonSaysTheme {
        WelcomeScreen(
            onNavigateToPlay = { },
            onNavigateToInstructions = { }
        )
    }
}
