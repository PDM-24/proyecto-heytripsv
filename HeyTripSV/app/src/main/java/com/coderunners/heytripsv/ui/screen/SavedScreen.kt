package com.coderunners.heytripsv.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.coderunners.heytripsv.MainViewModel
import com.coderunners.heytripsv.R
import com.coderunners.heytripsv.ui.components.PostCardHorizontal
import com.coderunners.heytripsv.ui.navigation.ScreenRoute
import com.coderunners.heytripsv.ui.theme.MainGreen

@Composable
fun SavedScreen(mainViewModel: MainViewModel, innerPadding: PaddingValues, navController: NavHostController){
    //TODO: Verificar si el usuario está loggeado
    val dialogOpen = remember {
        mutableStateOf(true)
    }

    if(false){
        when(dialogOpen.value){
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
                            horizontalAlignment = Alignment.CenterHorizontally)
                        {
                            Text(text = stringResource(id = R.string.save_logged))
                            Spacer(modifier = Modifier.padding(10.dp))
                            Button(onClick = {
                                dialogOpen.value = false
                                navController.navigate(ScreenRoute.LogIn.route) },
                                modifier = Modifier
                                    .fillMaxWidth(0.8f)
                                    .padding(horizontal = 16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MainGreen
                                ),
                                border = BorderStroke(1.dp, color = Color.White),
                                shape = RoundedCornerShape(7.dp)) {
                                Text(text = stringResource(id = R.string.log_in), color = Color.White)
                            }
                            Spacer(modifier = Modifier.padding(5.dp))
                            Button(onClick = {
                                dialogOpen.value = false
                                navController.navigate(ScreenRoute.Home.route) },
                                modifier = Modifier
                                    .fillMaxWidth(0.8f)
                                    .padding(horizontal = 16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.White

                                ),
                                border = BorderStroke(1.dp, color = MainGreen),
                                shape = RoundedCornerShape(7.dp))
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

    }else{
        val savedList = mainViewModel.savedPostList.collectAsState()
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            item{
                Text(text = stringResource(R.string.navbar_saved),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp)
            }
            items(savedList.value){
                PostCardHorizontal(post = it, onClick = {
                    mainViewModel.saveSelectedPost(it)
                    navController.navigate(ScreenRoute.PostView.route)
                }, save = true)
            }
        }

    }
}
