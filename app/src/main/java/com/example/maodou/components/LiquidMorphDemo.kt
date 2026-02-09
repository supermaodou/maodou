package com.example.maodou.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.example.maodou.R
import com.kyant.backdrop.backdrops.layerBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import com.kyant.backdrop.drawBackdrop
import com.kyant.backdrop.effects.blur
import com.kyant.backdrop.effects.lens
import com.kyant.backdrop.effects.vibrancy
import com.kyant.capsule.ContinuousRoundedRectangle

@Composable
fun LiquidMorphDemo() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopEnd
    ) {
        // 1. Create the Backdrop
        // We use the default behavior now, which records whatever component we attach it to
        val backdrop = rememberLayerBackdrop()

        // 2. The Background Image
        // This replaces the circles. We attach the backdrop recorder here so the glass knows what to refract.
        Image(
            painter = painterResource(id = R.drawable.image),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .layerBackdrop(backdrop) // <--- Important: This captures the image for the glass effect
        )

        // 3. State for our morphing animation
        var isOpen by remember { mutableStateOf(false) }

        // Animate from 0f (Circle) to 1f (Big Box)
        val progress by animateFloatAsState(
            targetValue = if (isOpen) 1f else 0f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessLow
            ),
            label = "MorphProgress"
        )

        // 4. The Morphing Glass Element
        val density = LocalDensity.current

        // Calculate dynamic size
        val currentWidth = 64.dp + (216.dp * progress)
        val currentHeight = 64.dp + (296.dp * progress)

        // Dynamic Shape Calculation
        val currentShape = remember(progress, currentWidth, currentHeight) {
            val widthPx = with(density) { currentWidth.toPx() }
            val heightPx = with(density) { currentHeight.toPx() }
            val minDim = minOf(widthPx, heightPx)

            val maxRadius = minDim / 2f
            val minRadius = with(density) { 24.dp.toPx() }

            val radiusPx = lerp(maxRadius, minRadius, progress)
            ContinuousRoundedRectangle(radiusPx)
        }

        Box(
            modifier = Modifier
                .padding(24.dp)
                .size(width = currentWidth, height = currentHeight)
                .clickable { isOpen = !isOpen }
                .drawBackdrop(
                    backdrop = backdrop,
                    shape = { currentShape },
                    effects = {
                        vibrancy()
                        blur(12.dp.toPx())
                        lens(
                            refractionHeight = 24.dp.toPx(),
                            refractionAmount = 32.dp.toPx(),
                            chromaticAberration = true
                        )
                    },
                    onDrawSurface = {
                        // Glass tint
                        drawRect(Color.White.copy(alpha = 0.2f))
                        // Glass border
                        drawRect(
                            color = Color.White.copy(alpha = 0.4f),
                            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.dp.toPx())
                        )
                    }
                )
        ) {
            // Menu Content
            if (progress > 0.1f) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 24.dp),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val contentAlpha = (progress - 0.2f).coerceAtLeast(0f) / 0.8f

                    val buttonShape = remember {
                        with(density) { ContinuousRoundedRectangle(16.dp.toPx()) }
                    }

                    @Composable
                    fun MenuButton(text: String) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .padding(horizontal = 24.dp)
                                .graphicsLayer { alpha = contentAlpha }
                                .background(
                                    color = Color(0xFF007AFF).copy(alpha = 0.8f),
                                    shape = buttonShape
                                )
                                .clickable { isOpen = false },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = text,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )
                        }
                    }

                    MenuButton("Profile")
                    MenuButton("FAQ")
                    MenuButton("Settings")
                    MenuButton("Profile")
                    MenuButton("FAQ")
                    
                }
            }
        }
    }
}