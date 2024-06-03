package com.coderunners.heytripsv.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.coderunners.heytripsv.ui.screen.MainScreen
import com.coderunners.heytripsv.ui.screen.PostViewModel
import com.coderunners.heytripsv.ui.screen.PostViewScreen

@Composable
fun NavBarGraph(
    navController: NavHostController,
    innerPadding: PaddingValues,
    postViewModel: PostViewModel
){
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.Home.route
    ){
        composable(ScreenRoute.Home.route){
            MainScreen(innerPadding, postViewModel, onClick = {navController.navigate(ScreenRoute.PostView.route)})
        }
        composable(ScreenRoute.PostView.route){
            PostViewScreen(innerPadding, postViewModel)
        }
    }
}