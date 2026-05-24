package pnm.bahrul3d.gymhome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import pnm.bahrul3d.gymhome.ui.AuthViewModel
import pnm.bahrul3d.gymhome.ui.GymViewModel
import pnm.bahrul3d.gymhome.ui.screens.ArticleScreen
import pnm.bahrul3d.gymhome.ui.screens.ExerciseListScreen
import pnm.bahrul3d.gymhome.ui.screens.HistoryScreen
import pnm.bahrul3d.gymhome.ui.screens.HomeScreen
import pnm.bahrul3d.gymhome.ui.screens.LoginScreen
import pnm.bahrul3d.gymhome.ui.screens.RegisterScreen
import pnm.bahrul3d.gymhome.ui.screens.TimerScreen
import pnm.bahrul3d.gymhome.ui.theme.GymHomeTheme

class MainActivity : ComponentActivity() {

    private val gymViewModel: GymViewModel by viewModels()

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            GymHomeTheme {

                val user by authViewModel.user

                GymApp(
                    gymViewModel = gymViewModel,
                    authViewModel = authViewModel,
                    startDestination =
                        if (user != null) "home"
                        else "login"
                )
            }
        }
    }
}

@Composable
fun GymApp(
    gymViewModel: GymViewModel,
    authViewModel: AuthViewModel,
    startDestination: String
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        // LOGIN
        composable("login") {

            LoginScreen(

                viewModel = authViewModel,

                onLoginSuccess = {

                    navController.navigate("home") {

                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                },

                onNavigateToRegister = {

                    navController.navigate("register")
                }
            )
        }

        // REGISTER
        composable("register") {

            RegisterScreen(

                viewModel = authViewModel,

                onRegisterSuccess = {

                    navController.navigate("home") {

                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                },

                onNavigateToLogin = {

                    navController.popBackStack()
                }
            )
        }

        // HOME
        composable("home") {

            HomeScreen(

                authViewModel = authViewModel,

                onDifficultySelected = { difficulty ->

                    navController.navigate(
                        "exercises/$difficulty"
                    )
                },

                onViewHistory = {

                    navController.navigate("history")
                },

                onLogout = {

                    navController.navigate("login") {

                        popUpTo("home") {
                            inclusive = true
                        }
                    }
                },

                onOpenArticle = {

                    navController.navigate("article")
                }
            )
        }

        // EXERCISE
        composable(

            route = "exercises/{difficulty}",

            arguments = listOf(
                navArgument("difficulty") {
                    type = NavType.StringType
                }
            )

        ) { backStackEntry ->

            val difficulty =
                backStackEntry.arguments
                    ?.getString("difficulty")
                    ?: "Beginner"

            ExerciseListScreen(

                difficulty = difficulty,

                viewModel = gymViewModel,

                onExerciseSelected = { id ->

                    navController.navigate(
                        "timer/$id"
                    )
                },

                onBack = {

                    navController.popBackStack()
                }
            )
        }

        // TIMER
        composable(

            route = "timer/{exerciseId}",

            arguments = listOf(
                navArgument("exerciseId") {
                    type = NavType.IntType
                }
            )

        ) { backStackEntry ->

            val exerciseId =
                backStackEntry.arguments
                    ?.getInt("exerciseId")
                    ?: 0

            TimerScreen(

                exerciseId = exerciseId,

                viewModel = gymViewModel,

                onBack = {

                    navController.popBackStack()
                }
            )
        }

        // HISTORY
        composable("history") {

            HistoryScreen(

                viewModel = gymViewModel,

                onBack = {

                    navController.popBackStack()
                }
            )
        }

        // ARTICLE
        composable("article") {

            ArticleScreen(

                onBack = {

                    navController.popBackStack()
                }
            )
        }
    }
}