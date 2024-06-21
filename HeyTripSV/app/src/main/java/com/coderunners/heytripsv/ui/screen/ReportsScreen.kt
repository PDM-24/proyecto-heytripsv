package com.coderunners.heytripsv.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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

                // Sorting option
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text("Ordenar por: A-Z", color = Color.Gray)
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
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text(text = "Agencia: ${apiModel.agency.firstOrNull()?.name ?: "Sin agencia"}")
                                Text(text = "Razón del reporte: ${apiModel.content}")
                                IconButton(
                                    onClick = { mainViewModel.deleteReportedAgency(apiModel.id) },
                                    modifier = Modifier.align(Alignment.End)
                                ) {
                                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                                }
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
                            onClick = onDelete,
                            modifier = Modifier
                                .background(MainGreen, shape = RoundedCornerShape(4.dp))
                                .size(32.dp)
                        ) {
                            Icon(Icons.Filled.Check, contentDescription = "Approve", tint = Color.White)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(
                            onClick = { mainViewModel.deleteReportedPost(apiModel.id ?: "") },
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
    account: ReportedAgency,
    onDelete: () -> Unit,
    apiModel: ReportApiModel
) {
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
                    onClick = { /* Handle approve */ },
                    modifier = Modifier
                        .background(MainGreen, shape = RoundedCornerShape(4.dp))
                        .size(32.dp)
                ) {
                    Icon(Icons.Filled.Check, contentDescription = "Approve", tint = Color.White)
                }
                IconButton(
                    onClick = onDelete,
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
data class ReportedAgency(val imageRes: Int, val username: String, val reason: String)
