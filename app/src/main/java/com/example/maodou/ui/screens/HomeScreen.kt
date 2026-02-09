package com.example.maodou.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.maodou.navigation.NavRoutes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    innerPadding: PaddingValues
) {
    val items = remember { mutableStateListOf<Int>().apply { addAll(0 until 48) } }
    var isRefreshing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // Colors: #fb2c54, #fb8b25, #32c557, #00c3cc
    val colors = listOf(
        Color(0xFFfb2c54),
        Color(0xFFfb8b25),
        Color(0xFF32c557),
        Color(0xFF00c3cc)
    )

    Surface(
        shape = RoundedCornerShape(40.dp),
        shadowElevation = 10.dp,
        color = if (isSystemInDarkTheme()) Color(0xFF101010) else Color.White
    ) {

        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = {
                scope.launch {
                    isRefreshing = true
                    delay(1000) // Simulate network delay
                    val currentMax = items.maxOrNull() ?: -1
                    // Add 8 new items
                    items.addAll((currentMax + 1)..(currentMax + 8))
                    isRefreshing = false
                }
            },
            modifier = Modifier
                .fillMaxSize()
//            .padding(
//                top = innerPadding.calculateTopPadding(),
//                bottom = innerPadding.calculateBottomPadding()
//            )
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = 8.dp,
                    end = 8.dp,
                    top = 8.dp,
                    bottom = 8.dp + innerPadding.calculateBottomPadding()
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items.size) { index ->
                    val itemValue = items[index]
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(colors[index % colors.size])
                            .clickable {
                                navController.navigate(NavRoutes.detailRoute(itemValue))
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = itemValue.toString(),
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}
