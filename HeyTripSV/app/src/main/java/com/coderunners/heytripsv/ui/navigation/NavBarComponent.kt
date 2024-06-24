package com.coderunners.heytripsv.ui.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coderunners.heytripsv.ui.theme.NavBarGreen
import com.coderunners.heytripsv.ui.theme.NavGray
import com.coderunners.heytripsv.ui.theme.White

@Composable
fun BottomNavigationBar(
    itemsList: List<NavBarItem>,
    currentRoute: String?,
    onClick: (NavBarItem) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.shadow(elevation = 10.dp, ambientColor = MaterialTheme.colorScheme.onBackground)
    ) {
        itemsList.forEach { navBarItem ->
            NavigationBarItem(
                selected = currentRoute == navBarItem.route,
                onClick = { onClick(navBarItem) },
                icon = {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = navBarItem.icon,
                        contentDescription = navBarItem.title,
                        tint = if (currentRoute == navBarItem.route) NavBarGreen else NavGray
                    )
                },
                label = {
                    Text(
                        text = navBarItem.title,
                        fontSize = 10.sp,
                        color = if (currentRoute == navBarItem.route) NavBarGreen else Color.Unspecified
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.background
                )
            )
        }
    }
}