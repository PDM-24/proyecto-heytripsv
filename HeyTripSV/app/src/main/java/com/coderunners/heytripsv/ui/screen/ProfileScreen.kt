package com.coderunners.heytripsv.ui.screen

import android.icu.text.ListFormatter.Width
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.coderunners.heytripsv.MainViewModel
import com.coderunners.heytripsv.R
import com.coderunners.heytripsv.ui.navigation.BottomNavigationBar
import com.coderunners.heytripsv.ui.navigation.ScreenRoute
import com.coderunners.heytripsv.ui.navigation.navBarItemList
import com.coderunners.heytripsv.ui.theme.DSpacer
import com.coderunners.heytripsv.ui.theme.MainGreen
import com.coderunners.heytripsv.ui.theme.TextGray
import com.coderunners.heytripsv.ui.theme.White
import com.coderunners.heytripsv.utils.UiState

@Composable
fun ProfileScreen(currentRoute: String?, navController: NavController, mainViewModel: MainViewModel) {

    val userRole = mainViewModel.userRole.collectAsState()
    val navItems = navBarItemList(mainViewModel)

    val logInViewState = mainViewModel.uiState.collectAsState()

    when(logInViewState.value) {
        is UiState.Error -> {
            val message = (logInViewState.value as UiState.Error).msg
            Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show()
            mainViewModel.setStateToReady()
        }

        UiState.Loading -> {
            Dialog(
                onDismissRequest = { },
                DialogProperties(
                    dismissOnBackPress = false,
                    dismissOnClickOutside = false
                )
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Transparent)
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        UiState.Ready -> {}
        is UiState.Success -> {
            mainViewModel.setStateToReady()
            navController.navigate(ScreenRoute.Home.route)
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                itemsList = navItems,
                currentRoute = currentRoute
            ) { currentNavigationItem ->
                navController.navigate(currentNavigationItem.route) {
                    navController.graph.startDestinationRoute?.let { startDestinationRoute ->
                        popUpTo(startDestinationRoute) {
                            saveState = false
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.profile),
                color = TextGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Divider(color = DSpacer, thickness = 1.dp)
            Spacer(modifier = Modifier.height(10.dp))
            if (userRole.value != ""){
                Button(
                    onClick = { mainViewModel.logOut() },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White

                    ),
                    border = BorderStroke(1.dp, color = MainGreen),
                    shape = RoundedCornerShape(7.dp)
                ) {
                    Text(text = stringResource(R.string.logout), color = TextGray)
                }
            } else{
                Button(
                    onClick = { navController.navigate(ScreenRoute.LogIn.route) },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White

                    ),
                    border = BorderStroke(1.dp, color = MainGreen),
                    shape = RoundedCornerShape(7.dp)
                ) {
                    Text(text = stringResource(R.string.log_in), color = TextGray)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Divider(color = DSpacer, thickness = 1.dp)
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = { navController.navigate(ScreenRoute.AboutUs.route) },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White

                ),
                border = BorderStroke(1.dp, color = MainGreen),
                shape = RoundedCornerShape(7.dp)
            ) {
                Text(text = stringResource(R.string.about_us), color = TextGray)
            }
            if(userRole.value == "user"){
                Divider(color = DSpacer, thickness = 1.dp)
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = { navController.navigate(ScreenRoute.userProfile.route) },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White

                    ),
                    border = BorderStroke(1.dp, color = MainGreen),
                    shape = RoundedCornerShape(7.dp)
                ) {
                    Text(text = stringResource(R.string.mi_perfil), color = TextGray)
                }
            }

        }


    }
}






