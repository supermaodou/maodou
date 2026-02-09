package com.example.maodou.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.maodou.R
import com.example.maodou.ui.screens.DetailScreen
import com.example.maodou.ui.screens.FavoriteScreen
import com.example.maodou.ui.screens.HomeScreen
import com.example.maodou.ui.screens.ProfileScreen
import com.example.maodou.ui.screens.SettingsScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.HOME_ROUTE,
        // 预测性返回动画
        popEnterTransition = {
            EnterTransition.None
        },
        popExitTransition = {
            scaleOut(
                targetScale = 0.9f,
                transformOrigin = TransformOrigin(pivotFractionX = 0.5f, pivotFractionY = 0.5f)
            )
        },
//        enterTransition = {
//            scaleIn(
//                initialScale = 0.9f,
//                transformOrigin = TransformOrigin(pivotFractionX = 0.5f, pivotFractionY = 0.5f)
//            )
//        },
//        exitTransition = {
//            scaleOut(
//                targetScale = 0.9f,
//                transformOrigin = TransformOrigin(pivotFractionX = 0.5f, pivotFractionY = 0.5f)
//            )
//        },
    ) {
        composable(NavRoutes.HOME_ROUTE) {
            HomeScreen(navController, innerPadding)
        }
        composable(NavRoutes.FAVORITES_ROUTE) {
            FavoriteScreen(navController, innerPadding)
        }
        composable(NavRoutes.PROFILE_ROUTE) {
            ProfileScreen(navController, innerPadding)
        }
        composable(NavRoutes.SETTINGS_ROUTE) {
            SettingsScreen(navController, innerPadding)
        }
        // 新增详情页面路由
        composable(
            route = NavRoutes.DETAIL_ROUTE,
            arguments = listOf(navArgument("itemNumber") { type = NavType.IntType })
        ) { backStackEntry ->
            val itemNumber = backStackEntry.arguments?.getInt("itemNumber") ?: 0
            DetailScreen(
                navController,
                itemNumber,
                innerPadding
            )
        }
    }
}

data class BottomBarTab(
    val title: Int,
    val icon: ImageVector,
    val route: String
)

val bottomBarTabs = listOf(
    BottomBarTab(
        title = R.string.home,
        icon = Icons.Default.Home,
        route = NavRoutes.HOME_ROUTE
    ),
    BottomBarTab(
        title = R.string.favorites,
        icon = Icons.Default.Favorite,
        route = NavRoutes.FAVORITES_ROUTE
    ),
    BottomBarTab(
        title = R.string.profile,
        icon = Icons.Default.Person,
        route = NavRoutes.PROFILE_ROUTE
    )
)