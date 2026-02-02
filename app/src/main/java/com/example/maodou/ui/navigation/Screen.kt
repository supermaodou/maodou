package com.example.maodou.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "首页", Icons.Filled.Home)
    object Favorite : Screen("favorite", "收藏", Icons.Filled.Favorite)
    object Profile : Screen("profile", "我的", Icons.Filled.Person)
}
