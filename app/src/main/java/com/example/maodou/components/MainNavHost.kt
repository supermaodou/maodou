package com.example.maodou.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavHost(modifier: Modifier) {
    val items = remember { mutableStateListOf<Int>().apply { addAll(0 until 40) } }

    // Colors: #fb2c54, #fb8b25, #32c557, #00c3cc
    val colors = listOf(
        Color(0xFFfb2c54),
        Color(0xFFfb8b25),
        Color(0xFF32c557),
        Color(0xFF00c3cc)
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items.size) { index ->
            val itemValue = items[index]
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(colors[index % colors.size]),
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

//    Image(
//        painterResource(id = R.drawable.wallpaper_light),
//        contentDescription = null,
//        modifier = Modifier.fillMaxSize()
//    )
}