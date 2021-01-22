package com.spyrdonapps.weightreductor.android.ui.custom

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.spyrdonapps.weightreductor.android.ui.features.home.HomeTab

@Composable
fun DefaultBottomBar(
    currentTab: HomeTab,
    onTabSelected: (HomeTab) -> Unit,
    items: List<HomeTab>
) {
    BottomNavigation {
        items.forEach { homeTab ->
            BottomNavigationItem(
                icon = { Icon(homeTab.icon) },
                label = { Text(stringResource(id = homeTab.titleResId)) },
                selected = homeTab == currentTab,
                onClick = { onTabSelected(homeTab) })
        }
    }
}