package com.coderunners.heytripsv.ui.screen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.coderunners.heytripsv.MainViewModel
import com.coderunners.heytripsv.R
import com.coderunners.heytripsv.data.remote.model.AgencyReports
import com.coderunners.heytripsv.data.remote.model.Report
import com.coderunners.heytripsv.data.remote.model.ReportApiModel
import com.coderunners.heytripsv.data.remote.model.ReportedAgency
import com.coderunners.heytripsv.ui.navigation.BottomNavigationBar
import com.coderunners.heytripsv.ui.navigation.navBarItemList
import com.coderunners.heytripsv.ui.theme.MainGreen
import com.coderunners.heytripsv.utils.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportedScreen(
    currentRoute: String?,
    mainViewModel: MainViewModel,
    navController: NavController
) {
    val navItems = navBarItemList(mainViewModel)
    val (isPublicationsSelected, setIsPublicationsSelected) = remember { mutableStateOf(true) }
    val reportedAgencies by mainViewModel.reportedAgencies.collectAsState()
    val reportedPosts by mainViewModel.reportedPosts.collectAsState()
    val reportedPostsState by mainViewModel.stateSaved.collectAsState()
    val reportedAgenciesState by mainViewModel.stateSaved.collectAsState()

    LaunchedEffect(Unit) {
            mainViewModel.getReportedPosts()
            mainViewModel.getReportedAgency()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reportados") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(itemsList = navItems, currentRoute = currentRoute) { currentNavigationItem ->
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
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    ToggleButton("Publicaciones", isPublicationsSelected) { setIsPublicationsSelected(true) }
                    Spacer(modifier = Modifier.width(8.dp))
                    ToggleButton("Cuentas", !isPublicationsSelected) { setIsPublicationsSelected(false) }
                }

                when {
                    isPublicationsSelected -> {
                        when (reportedPostsState) {
                            is UiState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                            is UiState.Error -> Text((reportedPostsState as UiState.Error).msg, color = Color.Red)
                            is UiState.Success -> LazyColumn(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                if (reportedPosts.isEmpty()){
                                    item{
                                        Text(text = stringResource(id = R.string.no_posts))
                                    }
                                }else{
                                    items(reportedPosts) { apiModel ->
                                        val reportedItem = ReportedPost(
                                            title = apiModel.title,
                                            content = apiModel.reports,
                                            imageRes = apiModel.image
                                        )
                                        ReportedPost(
                                            item = reportedItem,
                                            onDelete = { mainViewModel.patchReportedPost(apiModel.id ?: "") },
                                            onDeletePost = {mainViewModel.deletePost(apiModel.id ?: "")},
                                            navController = navController,
                                            mainViewModel = mainViewModel
                                        )
                                    }
                                }
                            }
                            else -> Unit
                        }
                    }
                    !isPublicationsSelected -> {
                        when (reportedAgenciesState) {
                            is UiState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                            is UiState.Error -> Text((reportedAgenciesState as UiState.Error).msg, color = Color.Red)
                            is UiState.Success -> LazyColumn(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                if (reportedAgencies.isEmpty()){
                                    item{
                                        Text(text = stringResource(id = R.string.no_posts))
                                    }
                                }else{
                                    items(reportedAgencies) { reportedAgency ->
                                        val agency = ReportedAgencyData(
                                            username = reportedAgency.name,
                                            report = reportedAgency.reports,
                                            imageRes = reportedAgency.image
                                        )
                                        ReportedAgency(
                                            account = agency,
                                            onDelete = { mainViewModel.patchReportedAgency(reportedAgency.id ?: "") },
                                            onDeleteAgency = {mainViewModel.deleteAgency(reportedAgency.id ?: "")},
                                            navController = navController,
                                            mainViewModel = mainViewModel
                                        )
                                    }
                                }
                            }
                            else -> Unit
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun ToggleButton(
    text: String,
    isSelected: Boolean,
    onClickListener: () -> Unit
) {
    Button(
        onClick = onClickListener,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) MainGreen else Color.White,
            contentColor = if (isSelected) Color.White else Color.Black
        ),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, Color.Gray),
        modifier = Modifier.width(150.dp)
    ) {
        Text(text)
    }
}

@Composable
fun ReportedPost(
    item: ReportedPost,
    onDelete: () -> Unit,
    onDeletePost: () -> Unit,
    navController: NavController,
    mainViewModel: MainViewModel
) {
    var showDialog by remember { mutableStateOf(false) }
    var showDialogDelete by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                mainViewModel.selectedPost
                navController.navigate("PostViewScreen")
            }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.imageRes,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(item.title, fontWeight = FontWeight.Bold)
                Column {
                    Text(item.content.joinToString(", ") { it.content }, color = Color.Gray)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(
                            onClick = { showDialog = true },
                            modifier = Modifier
                                .background(MainGreen, shape = RoundedCornerShape(4.dp))
                                .size(32.dp)
                        ) {
                            Icon(Icons.Filled.Check, contentDescription = "Approve", tint = Color.White)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(
                            onClick = { showDialogDelete = true },
                            modifier = Modifier
                                .background(Color(0xFFCC0000), shape = RoundedCornerShape(4.dp))
                                .size(32.dp)
                        ) {
                            Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Color.White)
                        }
                    }
                }
            }
        }
    }

    if (showDialogDelete) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Eliminar publicación") },
            text = { Text("¿Estás seguro de que deseas eliminar esta publicación?") },
            confirmButton = {
                Button(
                    onClick = {
                        onDeletePost()
                        showDialog = false
                    }
                ) {
                    Text("Sí")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false }
                ) {
                    Text("No")
                }
            }
        )
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Eliminar publicación") },
            text = { Text("¿Estás seguro de que deseas eliminar esta publicación?") },
            confirmButton = {
                Button(
                    onClick = {
                        onDelete()
                        showDialog = false
                    }
                ) {
                    Text("Sí")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false }
                ) {
                    Text("No")
                }
            }
        )
    }
}

@Composable
fun ReportedAgency(
    account: ReportedAgencyData,
    onDelete: () -> Unit,
    onDeleteAgency: () -> Unit,
    mainViewModel: MainViewModel,
    navController: NavController
) {
    var showDialog by remember { mutableStateOf(false) }
    var showDialogDelete by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                mainViewModel.selectedAgency
                navController.navigate("agency")
            }

    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = account.imageRes,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(account.username, fontWeight = FontWeight.Bold)
                Text(account.report.joinToString(", ") { it.content}, color = Color.Gray)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = { showDialogDelete = true },
                        modifier = Modifier
                            .background(MainGreen, shape = RoundedCornerShape(4.dp))
                            .size(32.dp)
                    ) {
                        Icon(Icons.Filled.Check, contentDescription = "Approve", tint = Color.White)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = { showDialogDelete = true},
                        modifier = Modifier
                            .background(Color(0xFFCC0000), shape = RoundedCornerShape(4.dp))
                            .size(32.dp)
                    ) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Color.White)
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Eliminar agencia") },
            text = { Text("¿Estás seguro de que deseas eliminar esta agencia?") },
            confirmButton = {
                Button(
                    onClick = {
                        onDelete()
                        showDialog = false
                    }
                ) {
                    Text("Sí")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false }
                ) {
                    Text("No")
                }
            }
        )
    }
    if (showDialogDelete) {
        AlertDialog(
            onDismissRequest = { showDialogDelete = false },
            title = { Text("Eliminar agencia") },
            text = { Text("¿Estás seguro de que deseas eliminar esta agencia?") },
            confirmButton = {
                Button(
                    onClick = {
                        onDeleteAgency()
                        showDialogDelete = false
                    }
                ) {
                    Text("Sí")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialogDelete = false }
                ) {
                    Text("No")
                }
            }
        )
    }
}

data class ReportedPost(
    val title: String,
    val content: List<Report>,
    val imageRes: String
)

data class ReportedAgencyData(
    val imageRes: String,
    val username: String,
    val report: List<AgencyReports>
)
