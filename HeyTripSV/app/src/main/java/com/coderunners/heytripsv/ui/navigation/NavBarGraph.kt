package com.coderunners.heytripsv.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.coderunners.heytripsv.MainViewModel
import com.coderunners.heytripsv.ui.screen.AboutUsScreen
import com.coderunners.heytripsv.ui.screen.CreateAccount
import com.coderunners.heytripsv.ui.screen.LogIn
import com.coderunners.heytripsv.ui.screen.CategoryScreen
import com.coderunners.heytripsv.ui.screen.MainScreen
import com.coderunners.heytripsv.ui.screen.PostViewScreen
import com.coderunners.heytripsv.ui.screen.ProfileScreen

@Composable
fun NavBarGraph(
    navController: NavHostController,
    innerPadding: PaddingValues,
    mainViewModel: MainViewModel
){
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.Home.route
    ){
        composable(ScreenRoute.Home.route){
            MainScreen(innerPadding, mainViewModel, navController)
        }
        composable(ScreenRoute.PostView.route){
            PostViewScreen(innerPadding, mainViewModel)
        }
            composable(ScreenRoute.Profile.route){
                ProfileScreen(innerPadding, navController)
            }
        composable(ScreenRoute.AboutUs.route){
                AboutUsScreen(innerPadding)
            }
        composable(ScreenRoute.LogIn.route){
            LogIn(innerPadding, navController)
        }
        composable(ScreenRoute.ForgotPassword.route){

        }
        composable(ScreenRoute.CreateAccount.route){
            CreateAccount(innerPadding, navController)
        }
        composable(ScreenRoute.Category.route){
            CategoryScreen(innerPadding = innerPadding, mainViewModel = mainViewModel) {
                navController.navigate(ScreenRoute.PostView.route)
            }
        }


    }
}