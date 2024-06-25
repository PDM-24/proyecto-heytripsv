package com.coderunners.heytripsv.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.coderunners.heytripsv.MainViewModel
import com.coderunners.heytripsv.R
import com.coderunners.heytripsv.ui.components.PostCardHorizontal
import com.coderunners.heytripsv.ui.navigation.BottomNavigationBar
import com.coderunners.heytripsv.ui.navigation.navBarItemList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    navController: NavController,
    currentRoute: String?,
    mainViewModel: MainViewModel,
    onClick : () -> Unit
) {
    val selectedCategory = mainViewModel.selectedCategory.collectAsState()
    val categoryList = mainViewModel.categoryList.collectAsState()
    val userRole = mainViewModel.userRole.collectAsState()
    var expanded by remember {
        mutableStateOf(false)
    }
    var localCategoryList by remember { mutableStateOf(categoryList.value) }

    val navItems = navBarItemList(mainViewModel)
    var filtros = arrayOf(stringResource(id = R.string.closest), stringResource(id = R.string.recent))
    var selectedText by remember { mutableStateOf(filtros[0]) }

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

        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = selectedCategory.value,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )
                    Row(modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 20.dp)) {
                        Text(
                            text = stringResource(id = R.string.sort_by) + ":",
                            modifier = Modifier
                                .wrapContentHeight()
                                .padding(10.dp)
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
                }
            }
            items(localCategoryList) { post ->
                PostCardHorizontal(
                    post = post,
                    onClick = {
                        mainViewModel.saveSelectedPost(post)
                        onClick()
                    },
                    mainViewModel = mainViewModel,
                    isAdmin = userRole.value == "admin",
                    onDelete = { postId ->
                        mainViewModel.deletePost(postId)
                        localCategoryList = localCategoryList.filter { it.id != postId }.toMutableList()
                    }
                )
            }
        }
    }
}
