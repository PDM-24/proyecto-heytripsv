package com.coderunners.heytripsv.ui.navigation

sealed class ScreenRoute (var route: String) {
    object Home : ScreenRoute("home")
    object Search : ScreenRoute("search")
    object Saved : ScreenRoute("saved")
    object Profile : ScreenRoute("profile")

    object AboutUs : ScreenRoute("aboutUs")

    object PostView : ScreenRoute("postView")
    object LogIn : ScreenRoute("logIn")
    object ForgotPassword : ScreenRoute("forgotPassword")
    object SignUp: ScreenRoute("signUp")

}