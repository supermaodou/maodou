package com.example.maodou.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.maodou.ui.components.BottomNavigationBar
import com.example.maodou.ui.navigation.Screen
import com.example.maodou.ui.screens.FavoriteScreen
import com.example.maodou.ui.screens.HomeScreen
import com.example.maodou.ui.screens.ProfileScreen
import com.example.maodou.ui.screens.SettingsScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Settings.route) {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
            // Default transitions for bottom nav tabs (none or fade)
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None }
        ) {
            composable(Screen.Home.route) { HomeScreen() }
            composable(Screen.Favorite.route) { FavoriteScreen() }
            composable(Screen.Profile.route) { ProfileScreen(navController) }
            
            // Custom transition only for Settings screen
            composable(
                route = Screen.Settings.route,
                enterTransition = {
                    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(500))
                },
                exitTransition = {
                     // Exiting to push another screen (if any) or when tab switching (unlikely from here)
                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(500))
                },
                popEnterTransition = {
                    // This is not usually hit for Settings unless we push something on top of it and come back
                    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(500))
                },
                popExitTransition = {
                    // Back from Settings -> Profile
                    // Current page scales down AND slides out to right
                    scaleOut(targetScale = 0.9f, animationSpec = tween(500)) + 
                    slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(500))
                }
            ) { SettingsScreen(navController) }
        }
    }
}
