package com.example.maodou.navigation

import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.maodou.R
import com.example.maodou.components.LiquidBottomTab
import com.kyant.backdrop.Backdrop
import com.example.maodou.components.LiquidSearchButton
import com.example.maodoudou.components.LiquidBottomTabs

@Composable
fun BottomNavigationBar(
    tabs: List<BottomBarTab>,
    currentRoute: String?,
    backdrop: Backdrop,
    onTabSelected: (String) -> Unit
) {
//    NavigationBar {
//        tabs.forEach { tab ->
//            val isSelected = currentRoute == tab.route
////            NavigationBarItem(
////                selected = isSelected,
////                onClick = { onTabSelected(tab.route) },
////                icon = {
////                    Icon(
////                        imageVector = tab.icon,
////                        contentDescription = stringResource(id = tab.title)
////                    )
////                },
////                label = {
////                    Text(text = stringResource(id = tab.title))
////                }
////            )
//
//            CustomNavItem(
//                selected = isSelected,
//                icon = tab.icon,
//                label = stringResource(id = tab.title),
//                onClick = {
//                    onTabSelected(tab.route)
//                }
//            )
//        }
//    }

    val airplaneModeIcon = painterResource(R.drawable.flight_40px)
    val iconColorFilter = ColorFilter.tint(contentColor)

    val animationScope = rememberCoroutineScope()
    val progressAnimation = remember { Animatable(0f) }

    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    val tabsCount = tabs.size

    val context = LocalContext.current

    Box(Modifier.fillMaxSize()) {
        Row(
            Modifier
                .safeContentPadding()
                .height(64f.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.spacedBy(8f.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Navigation
            Box(
                Modifier
                    .fillMaxHeight()
                    .height(64f.dp)
//                    .wrapContentWidth()
                    .weight(1f)
            ) {
                LiquidBottomTabs(
                    selectedTabIndex = { selectedTabIndex },
                    onTabSelected = { onTabSelected(tabs[it].route) },
                    backdrop = backdrop,
                    tabsCount = tabsCount,
//                    modifier = Modifier.padding(horizontal = 36f.dp)
                ) {
                    tabs.forEachIndexed { index, tab ->
                        LiquidBottomTab({ selectedTabIndex = index }) {
                            Icon(
                                modifier = Modifier.size(28f.dp),
                                imageVector = tab.icon,
                                contentDescription = stringResource(id = tab.title)
                            )
                            BasicText(
                                text = stringResource(id = tab.title),
                                style = TextStyle(
                                    contentColor,
                                    11f.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }

            // Search
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .aspectRatio(1f)
            ) {
                LiquidSearchButton(backdrop = backdrop, onClick = {
                    Toast.makeText(context, "Search", Toast.LENGTH_SHORT).show()
                }) {
                    Icon(
                        modifier = Modifier
                            .size(28.dp)
                            .align(Alignment.Center),
                        painter = painterResource(R.drawable.search),
                        contentDescription = "Search",
                        tint = contentColor
                    )
                }
            }
        }
    }
}
