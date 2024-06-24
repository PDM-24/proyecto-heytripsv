package com.coderunners.heytripsv.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.coderunners.heytripsv.R

@Composable
fun navBarItemList(): List<NavBarItem> {
    return listOf(
        NavBarItem(
            stringResource(id = R.string.navbar_home),
            ScreenRoute.Home.route,
            ImageVector.vectorResource(id = R.drawable.home)
        ),
        NavBarItem(
            stringResource(id = R.string.navbar_search),
            ScreenRoute.addPost.route,
            ImageVector.vectorResource(id = R.drawable.magnify)
        ),
        NavBarItem(
            stringResource(id = R.string.navbar_saved),
            ScreenRoute.Saved.route,
            ImageVector.vectorResource(id = R.drawable.bookmark)
        ),
        NavBarItem(
            stringResource(id = R.string.navbar_profile),
            ScreenRoute.Profile.route,
            ImageVector.vectorResource(id = R.drawable.account_circle)
        )
    )
}