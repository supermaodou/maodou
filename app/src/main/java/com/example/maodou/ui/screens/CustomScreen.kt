package com.example.maodou.ui.screens

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.maodou.BackdropDemoScaffold
import com.example.maodou.Block
import com.example.maodou.R
import com.example.maodou.components.LiquidBottomTab
import com.example.maodoudou.components.LiquidBottomTabs

@Composable
fun CustomScreen() {
    val isLightTheme = !isSystemInDarkTheme()
    val contentColor = if (isLightTheme) Color.Black else Color.White

    val airplaneModeIcon = painterResource(R.drawable.flight_40px)
    val iconColorFilter = ColorFilter.tint(contentColor)

    Surface(
        shape = RoundedCornerShape(40.dp),
        shadowElevation = 10.dp
    ) {

        BackdropDemoScaffold(
            initialPainterResId = R.drawable.system_home_screen_light
        ) { backdrop ->
            Column(verticalArrangement = Arrangement.spacedBy(32f.dp)) {
                Block {
                    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

                    LiquidBottomTabs(
                        selectedTabIndex = { selectedTabIndex },
                        onTabSelected = { selectedTabIndex = it },
                        backdrop = backdrop,
                        tabsCount = 3,
                        modifier = Modifier.padding(horizontal = 36f.dp)
                    ) {
                        repeat(3) { index ->
                            LiquidBottomTab({
                                selectedTabIndex = index
                                Log.e("CustomScreen", "CustomScreen: ${index + 1}")
                            }) {
                                Box(
                                    Modifier
                                        .size(28f.dp)
                                        .paint(airplaneModeIcon, colorFilter = iconColorFilter)
                                )
                                BasicText(
                                    "Tab ${index + 1}",
                                    style = TextStyle(contentColor, 12f.sp)
                                )
                            }
                        }
                    }
                }

                Block {
                    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

                    LiquidBottomTabs(
                        selectedTabIndex = { selectedTabIndex },
                        onTabSelected = { selectedTabIndex = it },
                        backdrop = backdrop,
                        tabsCount = 4,
                        modifier = Modifier.padding(horizontal = 36f.dp)
                    ) {
                        repeat(4) { index ->
                            LiquidBottomTab({ selectedTabIndex = index }) {
                                Box(
                                    Modifier
                                        .size(28f.dp)
                                        .paint(airplaneModeIcon, colorFilter = iconColorFilter)
                                )
                                BasicText(
                                    "Tab ${index + 1}",
                                    style = TextStyle(contentColor, 12f.sp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
