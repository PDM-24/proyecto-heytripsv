package com.coderunners.heytripsv.ui.screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.coderunners.heytripsv.MainViewModel
import com.coderunners.heytripsv.R
import com.coderunners.heytripsv.ui.components.PostCardHorizontal
import com.coderunners.heytripsv.ui.navigation.BottomNavigationBar
import com.coderunners.heytripsv.ui.navigation.ScreenRoute
import com.coderunners.heytripsv.ui.navigation.navBarItemList
import com.coderunners.heytripsv.ui.theme.MainGreen
import com.coderunners.heytripsv.utils.UiState

@Composable

fun SavedScreen(mainViewModel: MainViewModel, currentRoute: String?, navController: NavHostController){
    val dialogOpen = remember {
        mutableStateOf(true)
    }

    mainViewModel.getNotifs()

    //Estado para obtener el rol de usuario
    val userRole = mainViewModel.userRole.collectAsState()

    // Estado para controlar el estado de la interfaz desde viewModel
    val postViewState = mainViewModel.stateSaved.collectAsState()

    when(postViewState.value){
        is UiState.Error -> {
            val message = (postViewState.value as UiState.Error).msg
            if (message != ""){
                Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show()
            }
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
                ){
                    CircularProgressIndicator()
                }
            }
        }
        UiState.Ready -> {}
        is UiState.Success -> {
            mainViewModel.setSavedStateToReady()
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(itemsList = navBarItemList(isAdmin = false), currentRoute = currentRoute) {
                    currentNavigationItem ->
                navController.navigate(currentNavigationItem.route){
                    navController.graph.startDestinationRoute?.let{startDestinationRoute ->
                        popUpTo(startDestinationRoute){
                            saveState = false
                        }
                    }
                    launchSingleTop=true
                    restoreState = true
                }
            }
        }
    ) { innerPadding ->

        //Verifica si el usuario está loggeado con el rol de usuario
        if (userRole.value != "user") {
            when (dialogOpen.value) {
                true -> {
                    Dialog(onDismissRequest = {}) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .padding(10.dp),
                            shape = RoundedCornerShape(16.dp),
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(15.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            )
                            {
                                Text(text = stringResource(id = R.string.save_logged))
                                Spacer(modifier = Modifier.padding(10.dp))
                                Button(
                                    onClick = {
                                        dialogOpen.value = false
                                        navController.navigate(ScreenRoute.LogIn.route)
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth(0.8f)
                                        .padding(horizontal = 16.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MainGreen
                                    ),
                                    border = BorderStroke(1.dp, color = Color.White),
                                    shape = RoundedCornerShape(7.dp)
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.log_in),
                                        color = Color.White
                                    )
                                }
                                Spacer(modifier = Modifier.padding(5.dp))
                                Button(
                                    onClick = {
                                        dialogOpen.value = false
                                        navController.navigate(ScreenRoute.Home.route)
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth(0.8f)
                                        .padding(horizontal = 16.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.White

                                    ),
                                    border = BorderStroke(1.dp, color = MainGreen),
                                    shape = RoundedCornerShape(7.dp)
                                )
                                {
                                    Text(text = stringResource(id = R.string.navbar_home))
                                }
                            }
                        }
                    }
                }

                false -> {
                    dialogOpen.value = false
                }
            }

        } else {
            val savedList = mainViewModel.savedPostList.collectAsState()

            LaunchedEffect(Unit) {
                mainViewModel.getSavedPosts()
            }

            LazyColumn(
                modifier = Modifier.padding(innerPadding)
            ) {
                item {
                    Text(
                        text = stringResource(R.string.navbar_saved),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )
                }
                items(savedList.value) {
                    PostCardHorizontal(post = it, onClick = {
                        mainViewModel.saveSelectedPost(it)
                        navController.navigate(ScreenRoute.PostView.route)
                    }, save = true, mainViewModel = mainViewModel)
                }
            }

        }
    }
}
