package com.example.maodou.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kyant.backdrop.Backdrop
import com.kyant.backdrop.backdrops.layerBackdrop
import com.kyant.backdrop.backdrops.rememberCombinedBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import com.kyant.backdrop.drawBackdrop
import com.kyant.backdrop.effects.blur
import com.kyant.backdrop.effects.lens
import com.kyant.capsule.ContinuousRoundedRectangle
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.Locale
import kotlin.math.abs

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScrollWheel(
    items: List<Int>,
    initialValue: Int,
    onValueChange: (Int) -> Unit,
    label: String,
    backdrop: Backdrop,
    modifier: Modifier = Modifier,
    visibleItems: Int = 5,
    itemHeight: Dp = 50.dp
) {
    val initialIndex = remember(items, initialValue) {
        items.indexOf(initialValue).coerceAtLeast(0)
    }

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = initialIndex)
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)
    val haptic = LocalHapticFeedback.current

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex + (visibleItems / 2) }
            .distinctUntilChanged()
            .collect { centerIndex ->
                val actualIndex = centerIndex.coerceIn(0, items.lastIndex)
                onValueChange(items[actualIndex])
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            }
    }

    val textBackdrop = rememberLayerBackdrop()

    // Combine the main background (gradient) with the text layer
    // This ensures the glass refracts EVERYTHING behind it
    val combinedBackdrop = rememberCombinedBackdrop(backdrop, textBackdrop)

    // CHANGED: Row -> Column to stack label on top
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally // Center label and wheel
    ) {
        // Label on top
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.5f),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp) // Space between label and wheel
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.height(itemHeight * visibleItems)
        ) {
            LazyColumn(
                state = listState,
                flingBehavior = flingBehavior,
                contentPadding = PaddingValues(vertical = itemHeight * (visibleItems / 2)),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .height(itemHeight * visibleItems)
                    .width(60.dp)
                    .layerBackdrop(textBackdrop)
            ) {
                items(items.size) { index ->
                    val item = items[index]

                    val isSelected by remember {
                        derivedStateOf {
                            val centerIndex = listState.firstVisibleItemIndex + (visibleItems / 6)
                            index == centerIndex
                        }
                    }

                    // --- Updated Scaling Logic ---
                    // Selected (Center): Scale = 1f (Biggest)
                    // Others: Scale = 0.5f
                    val scale = if (isSelected) 1f else 0.5f
                    val alpha = if (isSelected) 1f else 0.3f

                    Box(
                        contentAlignment = Alignment.TopStart,
                        modifier = Modifier
                            .height(itemHeight)
                            .scale(scale)
                            .alpha(alpha)
                    ) {
                        Text(
                            text = String.format(Locale.ROOT, "%02d", item),
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(itemHeight + 10.dp)
                    .drawBackdrop(
                        backdrop = combinedBackdrop, // Use the combined backdrop here!
                        shape = { ContinuousRoundedRectangle(16.dp) },
                        effects = {
                            blur(4.dp.toPx())
                            lens(
                                refractionHeight = 12.dp.toPx(),
                                refractionAmount = 16.dp.toPx(),
                                chromaticAberration = true
                            )
                        },
                        onDrawSurface = {
                            drawRect(Color.White.copy(alpha = 0.15f))
                            drawRect(Color.White.copy(alpha = 0.05f))
                        }
                    )
            )
        }
    }
}