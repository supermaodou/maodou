package com.example.maodou.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.maodou.ui.components.BottomNavigationBar
import com.example.maodou.ui.navigation.Screen
import com.example.maodou.ui.screens.FavoriteScreen
import com.example.maodou.ui.screens.HomeScreen
import com.example.maodou.ui.screens.ProfileScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            composable(Screen.Home.route) { HomeScreen() }
            composable(Screen.Favorite.route) { FavoriteScreen() }
            composable(Screen.Profile.route) { ProfileScreen() }
        }
    }
}
