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
    object CreateAccount : ScreenRoute("createAccount")

    object Category : ScreenRoute("category")
    object Agency : ScreenRoute("agency")
    object EditAgency : ScreenRoute("editAgency")

    object ConfirmationCode : ScreenRoute("confirmationCode")

    object ChangePassowrd : ScreenRoute("changePass")
    object userProfile : ScreenRoute("userProf")
    object addPost : ScreenRoute("addPost")

}