package com.example.maodou.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.maodou.navigation.SetupNavGraph
import com.example.maodou.navigation.bottomBarTabs
import com.example.maodou.navigation.BottomNavigationBar
import com.kyant.backdrop.backdrops.layerBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination?.route

    val backgroundColor = Color.White
    val backdrop = rememberLayerBackdrop {
        drawRect(backgroundColor)
        drawContent()
    }

//    Box(
//        modifier = Modifier.fillMaxSize()
//    ) {

    // 内容层 - 应用 layerBackdrop
    Box(
        modifier = Modifier
            .fillMaxSize()
            .layerBackdrop(backdrop)
    ) {

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                // edge-to-edge
                .windowInsetsPadding(WindowInsets.navigationBars),
            topBar = {
                TopAppBar(
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text("Top app bar")
                    }
                )
            },
            bottomBar = {},
//                floatingActionButton = {
//                    LiquidFloatingActionButton(
//                        onClick = {
//                            Log.e("LiquidFloatingActionButton", "Clicked")
//                        },
//                        shape = CircleShape
//                    ) {
//                        Icon(Icons.Default.Search, contentDescription = "Search")
//                    }
//                }
        ) { innerPadding ->
            SetupNavGraph(
                navController = navController,
                innerPadding = innerPadding
            )
        }
    }

    // 底部栏 - 使用 backdrop
    Box(
        modifier = Modifier
//                .align(Alignment.BottomCenter)
            .padding(bottom = 28.dp)
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        BottomNavigationBar(
            tabs = bottomBarTabs,
            currentRoute = currentDestination,
            backdrop = backdrop,
            onTabSelected = { route ->
                navController.navigate(route) {
                    // 避免重复创建
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
//    }
}