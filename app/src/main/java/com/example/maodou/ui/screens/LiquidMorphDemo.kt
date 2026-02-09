package com.example.maodou.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.maodou.R
import com.kyant.backdrop.Backdrop
import com.kyant.backdrop.backdrops.layerBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import com.kyant.backdrop.drawBackdrop
import com.kyant.backdrop.effects.blur
import com.kyant.backdrop.effects.lens
import com.kyant.backdrop.effects.vibrancy
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Preview
@Composable
fun LiquidMorphDemo() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // 1. The Global Backdrop
        val backdrop = rememberLayerBackdrop()

        // 2. Wallpaper Layer
        Image(
            painter = painterResource(id = R.drawable.lol),
            contentDescription = "Wallpaper",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .layerBackdrop(backdrop)
        )

        var isUnlocked by remember { mutableStateOf(false) }

        // 3. Fake Status Bar & Dynamic Island
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 24.dp, end = 24.dp)
        ) {
            // Dynamic Island
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .size(120.dp, 36.dp)
                    .background(Color.Black, RoundedCornerShape(18.dp)),
                contentAlignment = Alignment.Center
            ) {
                // Mini Time inside Dynamic Island
                Text(
                    text = "9:41",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Fake Indicators
            Row(
                modifier = Modifier.align(Alignment.TopEnd),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text("5G", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                // Battery Icon (Simple representation)
                Box(
                    modifier = Modifier
                        .size(24.dp, 12.dp)
                        .background(Color.Transparent, RoundedCornerShape(4.dp))
                        .drawBackdrop(
                            backdrop = backdrop,
                            shape = { RoundedCornerShape(4.dp) },
                            effects = { blur(4.dp.toPx()) },
                            onDrawSurface = {
                                drawRect(
                                    Color.White.copy(alpha = 0.4f),
                                    style = androidx.compose.ui.graphics.drawscope.Stroke(1.dp.toPx())
                                )
                                drawRect(
                                    Color.White,
                                    size = size.copy(width = size.width * 0.8f)
                                ) // 80% battery
                            }
                        )
                )
            }
        }

        // 4. Passcode UI
        AnimatedVisibility(
            visible = !isUnlocked,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            PasscodeScreen(
                backdrop = backdrop,
                onUnlock = { isUnlocked = true }
            )
        }

        // 5. Unlocked UI
        if (isUnlocked) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "🔓",
                        fontSize = 64.sp
                    )
                    Text(
                        "Unlocked",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        "Tap to lock",
                        fontSize = 16.sp,
                        color = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .clickable { isUnlocked = false }
                    )
                }
            }
        }
    }
}

@Composable
fun PasscodeScreen(
    backdrop: Backdrop,
    onUnlock: () -> Unit
) {
    var passcode by remember { mutableStateOf("") }
    var isLockedOut by remember { mutableStateOf(false) }
    var lockoutTime by remember { mutableStateOf(0) }

    // Shake Animation
    val shakeOffset = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    fun triggerShake() {
        scope.launch {
            for (i in 0..2) {
                shakeOffset.animateTo(20f, spring(stiffness = Spring.StiffnessHigh))
                shakeOffset.animateTo(-20f, spring(stiffness = Spring.StiffnessHigh))
            }
            shakeOffset.animateTo(0f)
        }
    }

    LaunchedEffect(isLockedOut) {
        if (isLockedOut) {
            lockoutTime = 10
            while (lockoutTime > 0) {
                delay(1000)
                lockoutTime--
            }
            isLockedOut = false
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Prompt Text
        if (isLockedOut) {
            Text(
                text = "Try again in $lockoutTime seconds",
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        } else {
            Text(
                text = "Enter Passcode",
                fontSize = 22.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }

        // Passcode Dots
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier
                .padding(bottom = 64.dp)
                .offset { IntOffset(shakeOffset.value.roundToInt(), 0) } // Apply shake
        ) {
            repeat(4) { index ->
                val isFilled = index < passcode.length
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(
                            color = if (isFilled) Color.White else Color.White.copy(alpha = 0.3f),
                            shape = CircleShape
                        )
                        .then(
                            if (!isFilled) Modifier.drawBackdrop(
                                backdrop = backdrop,
                                shape = { CircleShape },
                                effects = { blur(8.dp.toPx()) }
                            ) else Modifier
                        )
                )
            }
        }

        // Glass Keypad
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(horizontal = 48.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(9) { idx ->
                val number = (idx + 1).toString()
                GlassKey(backdrop, number, enabled = !isLockedOut) {
                    if (passcode.length < 4) {
                        passcode += number
                        if (passcode == "1234") {
                            onUnlock()
                        } else if (passcode.length == 4) {
                            // Wrong Password Logic
                            triggerShake()
                            passcode = ""
                            // Simple logic: lock out immediately on wrong attempt for demo
                            isLockedOut = true
                        }
                    }
                }
            }
            item {} // Empty slot
            item {
                GlassKey(backdrop, "0", enabled = !isLockedOut) {
                    if (passcode.length < 4) {
                        passcode += "0"
                        if (passcode == "1234") {
                            onUnlock()
                        } else if (passcode.length == 4) {
                            triggerShake()
                            passcode = ""
                            isLockedOut = true
                        }
                    }
                }
            }
            item {
                // Delete / Backspace Button
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clickable(enabled = !isLockedOut) {
                            if (passcode.isNotEmpty()) {
                                passcode = passcode.dropLast(1)
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text("⌫", color = Color.White, fontSize = 24.sp)
                }
            }
        }
    }
}

@Composable
fun GlassKey(
    backdrop: Backdrop,
    number: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // Expand on click animation
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.2f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessMedium)
    )

    Box(
        modifier = Modifier
            .size(80.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clip(CircleShape)
            .drawBackdrop(
                backdrop = backdrop,
                shape = { CircleShape },
                effects = {
                    vibrancy() // More blur when pressed
                    lens(20.dp.toPx(), 40.dp.toPx())
                },
                onDrawSurface = {
                    drawRect(Color.White.copy(alpha = if (isPressed) 0.3f else 0.15f))
                }
            ),
//            .clickable(
//                interactionSource = interactionSource,
//                enabled = enabled,
//                onClick = onClick,
//            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number,
            fontSize = 32.sp,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
    }
}