package com.example.maodou.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    navController: NavController,
    innerPadding: PaddingValues
) {
    val items = List(20) { "收藏项目 ${it + 1}" }
    Surface(
        shape = RoundedCornerShape(40.dp),
        shadowElevation = 10.dp,
        color = Color(0xFF32c557)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp)
        ) {
            items(items.size) { index ->
                Card(
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    ListItem(
                        headlineContent = { Text(items[index]) },
                        supportingContent = { Text("这是 ${items[index]} 的详细描述信息") }
                    )
                }
            }
        }
    }
}
