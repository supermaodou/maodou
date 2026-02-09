package com.example.maodou.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import com.kyant.backdrop.drawBackdrop
import com.kyant.backdrop.effects.blur
import com.kyant.backdrop.effects.lens
import com.kyant.backdrop.effects.vibrancy
import com.kyant.capsule.ContinuousCapsule

@Composable
fun LiquidFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = FloatingActionButtonDefaults.shape,
    containerColor: Color = FloatingActionButtonDefaults.containerColor,
    contentColor: Color = contentColorFor(containerColor),
    interactionSource: MutableInteractionSource? = null,
    content: @Composable () -> Unit,
) {
    val backgroundColor = Color.White
    val backdrop = rememberLayerBackdrop {
        drawRect(backgroundColor)
        drawContent()
    }

    Surface(
        onClick = onClick,
        shape = shape,
        color = containerColor,
        contentColor = contentColor,
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        interactionSource = interactionSource ?: remember { MutableInteractionSource() },
//        modifier = modifier
//            .size(64.dp)
//            .drawBackdrop(
//                backdrop = backdrop,
//                shape = { ContinuousCapsule },
//                effects = {
//                    vibrancy()
//                    blur(4f.dp.toPx())
//                    lens(16f.dp.toPx(), 32f.dp.toPx())
//                },
//                onDrawSurface = {
//                    val tint = Color(0xFF0088FF)
//                    drawRect(tint, blendMode = BlendMode.Hue)
//                    drawRect(tint.copy(alpha = 0.75f))
//                }
//            )
    ) {
//        Box(contentAlignment = Alignment.Center) {
//            content()
//        }

        Box(
            Modifier
                .width(64.dp)
                .height(64.dp)
                .drawBackdrop(
                    backdrop = backdrop,
                    shape = { ContinuousCapsule },
                    effects = {
                        vibrancy()
                        blur(4f.dp.toPx())
                        lens(16f.dp.toPx(), 32f.dp.toPx())
                    },
//                    onDrawSurface = {
//                        val tint = Color(0xFF0088FF)
//                        drawRect(tint, blendMode = BlendMode.Hue)
//                        drawRect(tint.copy(alpha = 0.75f))
//                    }
                    onDrawSurface = { drawRect(Color.White.copy(alpha = 0.5f)) }
                )
                .aspectRatio(1f)
        )
    }

}