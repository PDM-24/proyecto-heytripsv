package com.coderunners.heytripsv.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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
import com.coderunners.heytripsv.ui.screen.ReportedScreen
import com.coderunners.heytripsv.ui.screen.SavedScreen
import com.coderunners.heytripsv.ui.screen.SearchScreen
import com.coderunners.heytripsv.ui.screen.UserProfile
import com.google.maps.android.compose.AdvancedMarker


@Composable
fun NavBarGraph(
    navController: NavHostController,
    mainViewModel: MainViewModel
){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute: String? =navBackStackEntry?.destination?.route
    val innerPadding = PaddingValues(10.dp)

    NavHost(
        navController = navController,
        startDestination = ScreenRoute.Home.route
    ){
        composable(ScreenRoute.Home.route){
            MainScreen(currentRoute, mainViewModel, navController)
        }
        composable(ScreenRoute.PostView.route){
            PostViewScreen(currentRoute, mainViewModel, navController)
        }
            composable(ScreenRoute.Profile.route){
                ProfileScreen(currentRoute, navController, mainViewModel)
            }
        composable(ScreenRoute.AboutUs.route){
                AboutUsScreen(currentRoute, navController)
            }
        composable(ScreenRoute.LogIn.route){
            LogIn(navController, mainViewModel)
        }
        composable(ScreenRoute.ForgotPassword.route){
            ForgotPassword(innerPadding, navController, mainViewModel)
        }
        composable(ScreenRoute.CreateAccount.route){
            CreateAccount(navController)
        }
        composable(ScreenRoute.Category.route){
            CategoryScreen(navController = navController, currentRoute = currentRoute,  mainViewModel = mainViewModel) {
                navController.navigate(ScreenRoute.PostView.route)
            }
        }
        composable(ScreenRoute.Search.route){
            SearchScreen(currentRoute, mainViewModel, navController)
        }

        composable(ScreenRoute.Agency.route){
            AgencyScreen(mainViewModel = mainViewModel, currentRoute = currentRoute, navController = navController, onClick = {navController.navigate(ScreenRoute.PostView.route)} )
        }
        composable(ScreenRoute.ConfirmationCode.route){
            ConfirmCode( navController = navController)
        }
        composable(ScreenRoute.ChangePassowrd.route){
            ChangePass(navController = navController)
        }

        composable(ScreenRoute.Saved.route){
            SavedScreen(mainViewModel = mainViewModel, currentRoute = currentRoute ,navController = navController)
        }
        composable(ScreenRoute.RegisterAgency.route){
            RegisterAgency(navController = navController)
        }

        /*TODO: Estas pantallas ya no usan el navbar de la vista pública
        *  Crear nueva lista para los navbaritem de la lista de admin
        *  Y agencia no usa navbar (Quitar los innerpadding como parámetros
        *   e implementar el topAppBar para regresar, un ejemplo está en la pantalla de Login)
        * */
        composable(ScreenRoute.EditAgency.route){
            EditAgencyScreen(mainViewModel = mainViewModel, innerPadding = innerPadding)
        }
        composable(ScreenRoute.userProfile.route){
            UserProfile(innerPadding = innerPadding, navController = navController)
        }
        composable(ScreenRoute.addPost.route){
            AddPostScreen(innerPadding = innerPadding, mainViewModel = mainViewModel)
        }
        composable(ScreenRoute.ReportedPost.route){
            ReportedScreen(navController = navController, mainViewModel = mainViewModel)
        }
    }
}