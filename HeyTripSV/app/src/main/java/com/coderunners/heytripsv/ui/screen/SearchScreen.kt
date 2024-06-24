package com.coderunners.heytripsv.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.coderunners.heytripsv.MainViewModel
import com.coderunners.heytripsv.R
import com.coderunners.heytripsv.model.PostList
import com.coderunners.heytripsv.ui.components.PostCard
import com.coderunners.heytripsv.ui.components.PostCardHorizontal
import com.coderunners.heytripsv.ui.navigation.ScreenRoute
import com.coderunners.heytripsv.ui.theme.DSpacer
import com.coderunners.heytripsv.ui.theme.MainGreen
import com.coderunners.heytripsv.ui.theme.TextGray
import com.coderunners.heytripsv.ui.theme.White
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import com.coderunners.heytripsv.ui.navigation.BottomNavigationBar
import com.coderunners.heytripsv.ui.navigation.navBarItemList


@Composable
fun SliderAdvancedExample() {
    var sliderPosition by remember { mutableStateOf(0f) }
    Column {
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.secondary,
                activeTrackColor = MaterialTheme.colorScheme.secondary,
                inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            steps = 3,
            valueRange = 0f..50f
        )
        Text(text = sliderPosition.toString())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(currentRoute: String?,mainViewModel: MainViewModel, navController: NavController){
    val search = remember { mutableStateOf("") }
    var expanded by remember {
        mutableStateOf(false)
    }
    val filtros = arrayOf(stringResource(id = R.string.closest), stringResource(id = R.string.recent))
    var selectedText by remember { mutableStateOf(filtros[0]) }
    val savedList = mainViewModel.savedPostList.collectAsState()
    val navItems = navBarItemList(mainViewModel)
    Scaffold(
        bottomBar = {
            BottomNavigationBar(itemsList = navItems, currentRoute = currentRoute) {
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
        Column(
            modifier = androidx.compose.ui.Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = stringResource(R.string.search),
                color = TextGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 25.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                OutlinedTextField(
                    value = search.value,
                    onValueChange = { search.value = it },
                    placeholder = { Text("Search...") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search Icon"
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Row(modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 20.dp)) {
                Text(
                    text = stringResource(id = R.string.sort_by) + ":",
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(5.dp)
                )
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    }
                ) {
                    TextField(
                        value = selectedText,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        filtros.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(text = item) },
                                onClick = {
                                    selectedText = item
                                    expanded = false
                                }
                            )
                        }
                    }
                }

            }
            Row(modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 20.dp)) {
                Text(
                    text = stringResource(id = R.string.price_range) + ":",
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(10.dp)
                )

                SliderAdvancedExample()
            }
            LazyColumn(modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 20.dp)) {
                items(savedList.value) {
                    PostCardHorizontal(
                        post = it,
                        onClick = {
                            mainViewModel.saveSelectedPost(it)
                            navController.navigate(ScreenRoute.PostView.route)
                        }, mainViewModel = mainViewModel,
                    )
                }
            }

        }
    }


    }


