package com.coderunners.heytripsv.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.coderunners.heytripsv.MainViewModel
import com.coderunners.heytripsv.R

@Composable
fun navBarItemList(mainViewModel: MainViewModel): List<NavBarItem> {
    val userRole = mainViewModel.userRole.collectAsState().value

    return if (userRole == "admin") {
        listOf(
            NavBarItem(
                stringResource(id = R.string.navbar_home),
                ScreenRoute.Home.route,
                ImageVector.vectorResource(id = R.drawable.home)
            ),
            NavBarItem(
                stringResource(id = R.string.navbar_report),
                ScreenRoute.ReportedPost.route,
                ImageVector.vectorResource(id = R.drawable.flag_admin)
            ),
            NavBarItem(
                stringResource(id = R.string.navbar_profile),
                ScreenRoute.Profile.route,
                ImageVector.vectorResource(id = R.drawable.account_circle)
            )
        )
    } else {
        listOf(
            NavBarItem(
                stringResource(id = R.string.navbar_home),
                ScreenRoute.Home.route,
                ImageVector.vectorResource(id = R.drawable.home)
            ),
            NavBarItem(
                stringResource(id = R.string.navbar_search),
                ScreenRoute.Search.route,
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
}