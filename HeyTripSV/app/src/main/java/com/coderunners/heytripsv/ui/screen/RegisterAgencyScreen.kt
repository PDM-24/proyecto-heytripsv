package com.coderunners.heytripsv.ui.screen

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController
import com.coderunners.heytripsv.MainViewModel
import com.coderunners.heytripsv.R
import com.coderunners.heytripsv.data.remote.api.AgencyApi
import com.coderunners.heytripsv.model.AgencyDataModel
import com.coderunners.heytripsv.ui.components.PhotoSelectorView
import com.coderunners.heytripsv.ui.theme.MainGreen
import com.coderunners.heytripsv.ui.theme.NavGray
import com.coderunners.heytripsv.ui.theme.TextGray
import com.coderunners.heytripsv.utils.UiState

@Composable
fun RegisterAgency(
    navController: NavController, mainViewModel: MainViewModel){

    val context = LocalContext.current
    val selectedImage= remember { mutableStateOf<Uri?>(null) }

    val addScreenState = mainViewModel.uiState.collectAsState()
    val agency = remember { mutableStateOf(AgencyApi()) }
    val numberValue = remember { mutableStateOf(agency.value.number.toString()) }

        when(addScreenState.value){
        is UiState.Error -> {
            val message = (addScreenState.value as UiState.Error).msg
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
            val message = (addScreenState.value as UiState.Success).msg
            Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show()
            mainViewModel.setStateToReady()

        }
    }

    Scaffold(
        topBar = {
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)){
                Icon(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .fillMaxHeight()
                        .clickable {
                            navController.popBackStack()
                        },
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Back",
                    tint = NavGray
                )
            }

        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(25.dp))
            Text(
                text = "Register Agency",
                color = TextGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 35.sp
            )
            Text(
                text = stringResource(R.string.name),
                color = TextGray,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 10.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = agency.value.name,
                onValueChange = { agency.value = agency.value.copy(name = it) },
                placeholder = { Text("") },
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.email),
                color = TextGray,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 10.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = agency.value.email,
                onValueChange = { agency.value = agency.value.copy(email = it)  },
                placeholder = { Text("") },
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.dui),
                color = TextGray,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 10.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = agency.value.dui,
                onValueChange = { agency.value = agency.value.copy(dui = it)  },
                placeholder = { Text("") },
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.description),
                color = TextGray,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 10.dp),
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = agency.value.description,
                onValueChange = { agency.value = agency.value.copy(description = it)  },
                placeholder = { Text("") },
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.contact_num),
                color = TextGray,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 10.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = numberValue.value.takeIf { it.isNotBlank() } ?: "",
                onValueChange = {
                    if (it.isEmpty() || it.isDigitsOnly()) {
                        numberValue.value = it
                    }
                },
                placeholder = { Text("") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.password),
                color = TextGray,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 10.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = agency.value.password,
                onValueChange = { agency.value = agency.value.copy(password = it)  },
                placeholder = { Text("") },
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.instagram),
                color = TextGray,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 10.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = agency.value.instagram,
                onValueChange = { agency.value = agency.value.copy(instagram = it) },
                placeholder = { Text("") },
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.facebook),
                color = TextGray,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 10.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = agency.value.facebook,
                onValueChange = { agency.value = agency.value.copy(facebook = it) },
                placeholder = { Text("") },
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.profile_pic),
                color = TextGray,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 10.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            PhotoSelectorView(selectImage = {
                agency.value = agency.value.copy(image = it)
                Log.i("Selected image", selectedImage.value.toString())})
            Spacer(modifier = Modifier.height(15.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                              mainViewModel.registerAgency(
                                  context,
                                  agency.value.copy(number = numberValue.value.toInt())
                              )
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MainGreen),
                    border = BorderStroke(1.dp, color = Color.White),
                    shape = RoundedCornerShape(7.dp)
                ) {
                    Text(text = stringResource(id = R.string.btn_accept), color = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(15.dp))


        }
    }

}