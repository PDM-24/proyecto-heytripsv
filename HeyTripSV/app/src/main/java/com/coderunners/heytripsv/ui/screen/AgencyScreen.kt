package com.coderunners.heytripsv.ui.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.coderunners.heytripsv.MainViewModel
import com.coderunners.heytripsv.R
import com.coderunners.heytripsv.ui.components.PostCardHorizontal
import com.coderunners.heytripsv.ui.components.ReportDialog
import com.coderunners.heytripsv.ui.navigation.BottomNavigationBar
import com.coderunners.heytripsv.ui.navigation.ScreenRoute
import com.coderunners.heytripsv.ui.navigation.navBarItemList
import com.coderunners.heytripsv.ui.theme.MainGreen
import com.coderunners.heytripsv.ui.theme.NavGray
import com.coderunners.heytripsv.ui.theme.White
import com.coderunners.heytripsv.utils.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgencyScreen(mainViewModel: MainViewModel, currentRoute: String?, navController: NavController, onClick: () -> Unit){
    val agency = mainViewModel.selectedAgency.collectAsState()
    var expanded by remember {
        mutableStateOf(false)
    }
    val filtros = arrayOf(stringResource(id = R.string.closest), stringResource(id = R.string.recent))
    var selectedText by remember { mutableStateOf(filtros[0]) }

    val radioOptions = listOf(
        stringResource(R.string.report_acc1), stringResource(R.string.report_acc2), stringResource(R.string.report_acc3), stringResource(R.string.report_acc4), stringResource(R.string.report_other)
    )


    val reportDialog = remember {
        mutableStateOf(false)
    }

    when(reportDialog.value){
        true -> {
            ReportDialog(radioOptions = radioOptions, onDismissRequest = {reportDialog.value = false}, onConfirm = {
                var content = ""

                content = when(it){
                    radioOptions[0] -> "Cuenta falsa"
                    radioOptions[1] -> "Publica contenido inadecuado"
                    radioOptions[2] -> "Descripción inapropiada"
                    radioOptions[3] -> "Spam"
                    else -> it
                }
                mainViewModel.reportContent(agency.value.id, content, false)
            })
        }
        false -> { reportDialog.value = false }
    }

    // Estado para controlar el estado de la interfaz desde viewModel
    val agencyState = mainViewModel.uiState.collectAsState()

    when(agencyState.value){
        is UiState.Error -> {
            reportDialog.value = false
            val message = (agencyState.value as UiState.Error).msg
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
                ){
                    CircularProgressIndicator()
                }
            }
        }
        UiState.Ready -> {}
        is UiState.Success -> {

            mainViewModel.setStateToReady()
                reportDialog.value = false
                Toast.makeText(LocalContext.current, stringResource(id = R.string.agency_reported), Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(itemsList = navBarItemList(), currentRoute = currentRoute) {
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
                .fillMaxWidth()
                .padding(innerPadding),

            ) {
            item {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        AsyncImage(
                            model = agency.value.image,
                            contentDescription = agency.value.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(200.dp)
                                .aspectRatio(1f)
                                .clip(CircleShape)
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = agency.value.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(10.dp)
                            )
                            Icon(painter = painterResource(R.drawable.flag),
                                contentDescription = "Flag",
                                modifier = Modifier
                                    .size(32.dp)
                                    .clickable {
                                        reportDialog.value = true
                                    })
                        }

                    }
                }
                Text(text = agency.value.desc)
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(CircleShape)
                        .background(NavGray)
                ) {
                    Row(
                        modifier = Modifier.padding(2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.Call, contentDescription = "Phone")
                        Text(text = agency.value.number)
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(CircleShape)
                        .background(NavGray)
                ) {
                    Row(
                        modifier = Modifier.padding(2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.instagram),
                            contentDescription = "Instagram"
                        )
                        Text(
                            text = agency.value.instagram,
                            modifier = Modifier.padding(start = 2.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(CircleShape)
                        .background(NavGray)
                ) {
                    Row(
                        modifier = Modifier.padding(2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.facebook),
                            contentDescription = "Facebook"
                        )
                        Text(
                            text = agency.value.facebook,
                            modifier = Modifier.padding(start = 2.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(thickness = 2.dp)
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
            items(agency.value.postList) {
                PostCardHorizontal(post = it, onClick = {
                    mainViewModel.saveSelectedPost(it)
                    onClick()
                })
            }


        }
    }
}