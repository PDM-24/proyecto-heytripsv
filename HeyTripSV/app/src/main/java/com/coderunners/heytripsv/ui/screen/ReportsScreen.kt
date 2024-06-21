package com.coderunners.heytripsv.ui.screen

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.coderunners.heytripsv.MainViewModel
import com.coderunners.heytripsv.R
import com.coderunners.heytripsv.data.remote.model.ReportApiModel
import com.coderunners.heytripsv.ui.theme.MainGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportedScreen(
    mainViewModel: MainViewModel,
    navController: NavController
) {
    val (isPublicationsSelected, setIsPublicationsSelected) = remember { mutableStateOf(true) }

    SideEffect {
        mainViewModel.getReportedAgency()
        mainViewModel.getReportedPosts()
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
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(Color.White)
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        "Ordenar por: A-Z",
                        color = Color.Gray,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable {
                            mainViewModel.sortReportedPostsAlphabetically()
                        }
                    )
                }

                LazyColumn(
                    modifier = Modifier.padding(16.dp)
                ) {
                    if (isPublicationsSelected) {
                        items(mainViewModel.reportedPosts.value) { apiModel ->
                            val reportedItem = ReportedPost(
                                title = apiModel.title ?: "",
                                description = apiModel.description ?: "",
                                imageRes = R.drawable.default_image
                            )
                            ReportedPost(
                                item = reportedItem,
                                onDelete = { mainViewModel.deleteReportedPost(apiModel.id ?: "") },
                                mainViewModel = mainViewModel,
                                apiModel = apiModel
                            )
                        }
                    } else {
                        items(mainViewModel.reportedAgency.value) { apiModel ->
                            val agency = apiModel.agency.firstOrNull()
                            if (agency != null) {
                                ReportedAgency(
                                    account = ReportedAgencyData(
                                        imageRes = R.drawable.default_image,
                                        username = agency.name,
                                        reason = apiModel.content
                                    ),
                                    onDelete = { mainViewModel.deleteReportedAgency(apiModel.id) },
                                    apiModel = apiModel,
                                    mainViewModel = mainViewModel
                                )
                            }
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
    mainViewModel: MainViewModel,
    apiModel: ReportApiModel
) {
    var showDialog by remember { mutableStateOf(false) }

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

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = item.imageRes),
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
                    Text(item.description, color = Color.Gray)
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
                            onClick = { showDialog = true },
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
}

@Composable
fun ReportedAgency(
    account: ReportedAgencyData,
    onDelete: () -> Unit,
    apiModel: ReportApiModel,
    mainViewModel: MainViewModel
) {
    var showDialog by remember { mutableStateOf(false) }

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

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = account.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(account.username, fontWeight = FontWeight.Bold)
                Text(account.reason, color = Color.Gray)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = { showDialog = true },
                    modifier = Modifier
                        .background(MainGreen, shape = RoundedCornerShape(4.dp))
                        .size(32.dp)
                ) {
                    Icon(Icons.Filled.Check, contentDescription = "Approve", tint = Color.White)
                }
                IconButton(
                    onClick = { showDialog = true },
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

data class ReportedPost(val imageRes: Int, val title: String, val description: String)
data class ReportedAgencyData(val imageRes: Int, val username: String, val reason: String)
