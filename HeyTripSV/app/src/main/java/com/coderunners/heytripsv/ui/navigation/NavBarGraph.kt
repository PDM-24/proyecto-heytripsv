package com.coderunners.heytripsv.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.coderunners.heytripsv.MainViewModel
import com.coderunners.heytripsv.ui.screen.AboutUsScreen
import com.coderunners.heytripsv.ui.screen.AddPostScreen
import com.coderunners.heytripsv.ui.screen.AgencyScreen
import com.coderunners.heytripsv.ui.screen.CreateAccount
import com.coderunners.heytripsv.ui.screen.LogIn
import com.coderunners.heytripsv.ui.screen.CategoryScreen
import com.coderunners.heytripsv.ui.screen.EditAgencyScreen
import com.coderunners.heytripsv.ui.screen.ChangePass
import com.coderunners.heytripsv.ui.screen.ConfirmCode
import com.coderunners.heytripsv.ui.screen.ForgotPassword
import com.coderunners.heytripsv.ui.screen.MainScreen
import com.coderunners.heytripsv.ui.screen.PostViewScreen
import com.coderunners.heytripsv.ui.screen.ProfileScreen
import com.coderunners.heytripsv.ui.screen.RegisterAgency
import com.coderunners.heytripsv.ui.screen.SavedScreen
import com.coderunners.heytripsv.ui.screen.SearchScreen
import com.coderunners.heytripsv.ui.screen.UserProfile
import com.google.maps.android.compose.AdvancedMarker


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
            PostViewScreen(innerPadding, mainViewModel, navController)
        }
            composable(ScreenRoute.Profile.route){
                ProfileScreen(innerPadding, navController)
            }
        composable(ScreenRoute.AboutUs.route){
                AboutUsScreen(innerPadding)
            }
        composable(ScreenRoute.LogIn.route){
            LogIn(innerPadding, navController, mainViewModel)
        }
        composable(ScreenRoute.ForgotPassword.route){
            ForgotPassword(innerPadding, navController)
        }
        composable(ScreenRoute.CreateAccount.route){
            CreateAccount(innerPadding, navController)
        }
        composable(ScreenRoute.Category.route){
            CategoryScreen(innerPadding = innerPadding, mainViewModel = mainViewModel) {
                navController.navigate(ScreenRoute.PostView.route)
            }
        }
        composable(ScreenRoute.Search.route){
            SearchScreen(innerPadding,mainViewModel, navController)
        }

        composable(ScreenRoute.Agency.route){
            AgencyScreen(mainViewModel = mainViewModel, innerPadding = innerPadding, onClick = {navController.navigate(ScreenRoute.PostView.route)} )
        }
        composable(ScreenRoute.ConfirmationCode.route){
            ConfirmCode(innerPadding = innerPadding, navController = navController)
        }
        composable(ScreenRoute.ChangePassowrd.route){
            ChangePass(innerPadding = innerPadding, navController = navController)
        }

        composable(ScreenRoute.Saved.route){
            SavedScreen(mainViewModel = mainViewModel, innerPadding = innerPadding, navController = navController)
        }

        composable(ScreenRoute.EditAgency.route){
            EditAgencyScreen(mainViewModel = mainViewModel, innerPadding = innerPadding)
        }
        composable(ScreenRoute.userProfile.route){
            UserProfile(innerPadding = innerPadding, navController = navController)
        }
        composable(ScreenRoute.addPost.route){
            AddPostScreen(innerPadding = innerPadding, mainViewModel = mainViewModel)
        }
        composable(ScreenRoute.RegisterAgency.route){
            RegisterAgency(innerPadding = innerPadding, navController = navController)
        }

    }
}