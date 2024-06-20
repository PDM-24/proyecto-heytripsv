package com.coderunners.heytripsv.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.coderunners.heytripsv.MainViewModel
import com.coderunners.heytripsv.R
import com.coderunners.heytripsv.model.PostList
import com.coderunners.heytripsv.ui.components.PostCard
import com.coderunners.heytripsv.ui.navigation.ScreenRoute
import com.coderunners.heytripsv.ui.theme.MainGreen
import com.coderunners.heytripsv.ui.theme.TextGray
import com.coderunners.heytripsv.ui.theme.White
import com.coderunners.heytripsv.utils.UiState

@Composable
fun MainScreen(innerPadding: PaddingValues, mainViewModel: MainViewModel, navController: NavHostController) {
    val context = LocalContext.current
    val userRole = mainViewModel.userRole.collectAsState()
    val updateScreenState = mainViewModel.uiState.collectAsState()
    val loading = remember {
        mutableStateOf(false)
    }
    when (updateScreenState.value) {
        is UiState.Error -> {
            val message = (updateScreenState.value as UiState.Error).msg
            Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show()
            mainViewModel.setStateToReady()
        }
        UiState.Loading -> {
            loading.value = true
        }
        UiState.Ready -> {
            loading.value = false
        }
        is UiState.Success -> {
            mainViewModel.setStateToReady()
        }
    }

    val upcomingList = mainViewModel.upcomingPosts.collectAsState()
    val recentList = mainViewModel.recentPosts.collectAsState()
    val isAdmin = mainViewModel.isAdmin.collectAsState()

    LaunchedEffect(Unit) {
        mainViewModel.getHomePostList()
    }

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        //Categories
        Row(
            modifier = Modifier
                .background(MainGreen)
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Column {
                Text(
                    text = stringResource(R.string.categories),
                    color = TextGray,
                    modifier = Modifier.padding(5.dp, 0.dp, 5.dp, 0.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = stringResource(R.string.categories_desc),
                    color = White,
                    modifier = Modifier.padding(5.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(5.dp)
                    ) {
                        Button(onClick = {
                            mainViewModel.saveSelectedCategory(context.resources.getString(R.string.beaches))
                            navController.navigate(ScreenRoute.Category.route)
                        },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = White
                            )) {
                            Text(text = stringResource(R.string.beaches), color = TextGray)
                        }
                        Button(onClick = {
                            mainViewModel.saveSelectedCategory(context.resources.getString(R.string.towns))
                            navController.navigate(ScreenRoute.Category.route)
                        },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = White
                            )) {
                            Text(text = stringResource(R.string.towns), color = TextGray)
                        }
                        Button(onClick = {
                            mainViewModel.saveSelectedCategory(context.resources.getString(R.string.others))
                            navController.navigate(ScreenRoute.Category.route)
                        },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = White
                            )) {
                            Text(text = stringResource(R.string.others), color = TextGray)
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .padding(5.dp)
                    ) {
                        Button(onClick = {
                            mainViewModel.saveSelectedCategory(context.resources.getString(R.string.mountains))
                            navController.navigate(ScreenRoute.Category.route)
                        },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = White
                            )) {
                            Text(text = stringResource(R.string.mountains), color = TextGray)
                        }
                        Button(onClick = {
                            mainViewModel.saveSelectedCategory(context.resources.getString(R.string.routes))
                            navController.navigate(ScreenRoute.Category.route)
                        },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = White
                            )) {
                            Text(text = stringResource(R.string.routes), color = TextGray)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.padding(5.dp))
        //PostCards
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.upcoming),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(0.dp, 10.dp)
                )
                if (loading.value) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(30.dp, 30.dp))
                    }
                } else {
                    if (upcomingList.value.size == 0) {
                        Text(
                            text = stringResource(id = R.string.no_posts),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                    LazyRow {
                        items(upcomingList.value) { postItem ->
                            PostCard(post = postItem, isAdmin = (userRole.value == "admin"), onClick =  {
                                mainViewModel.saveSelectedPost(postItem)
                                navController.navigate(ScreenRoute.PostView.route)
                            }, onDelete = {
                                mainViewModel.deletePost(it)
                            })
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.padding(5.dp))
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.recent),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(0.dp, 10.dp)
                )
                if (loading.value) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        CircularProgressIndicator(modifier = Modifier.size(30.dp, 30.dp))
                    }
                } else {
                    if (recentList.value.size == 0) {
                        Text(
                            text = stringResource(id = R.string.no_posts),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                    LazyRow {
                        items(recentList.value) { postItem ->
                                PostCard(post = postItem, isAdmin = (userRole.value == "admin"), onClick =  {
                                    mainViewModel.saveSelectedPost(postItem)
                                    navController.navigate(ScreenRoute.PostView.route)
                                }, onDelete = {
                                    mainViewModel.deletePost(it)
                                })
                            }
                        }
                    }
                }
            }
        }
    }
}