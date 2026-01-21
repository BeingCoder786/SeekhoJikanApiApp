package com.example.jikanapi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jikanapi.ui.details.DetailScreen
import com.example.jikanapi.ui.home.HomeScreen

@Composable
fun AnimeNavGraph(modifier: Modifier) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "home",modifier = modifier) {
        composable("home") { HomeScreen { id -> navController.navigate("details/$id") } }
        composable(
            "details/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            DetailScreen(animeId = id)
        }
    }
}
