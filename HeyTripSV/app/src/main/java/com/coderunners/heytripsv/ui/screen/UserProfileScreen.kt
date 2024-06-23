package com.coderunners.heytripsv.ui.screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.coderunners.heytripsv.MainViewModel
import com.coderunners.heytripsv.R
import com.coderunners.heytripsv.ui.navigation.ScreenRoute
import com.coderunners.heytripsv.ui.theme.DSpacer
import com.coderunners.heytripsv.ui.theme.MainGreen
import com.coderunners.heytripsv.ui.theme.TextGray
import com.coderunners.heytripsv.utils.UiState

@Composable
fun UserProfile(
    innerPadding : PaddingValues,
    navController: NavController,
    mainViewModel : MainViewModel
){

    LaunchedEffect (Unit){
        mainViewModel.getOwnUser()
    }

    val newuserName = remember { mutableStateOf("") }
    val newuserEmail = remember { mutableStateOf("") }
    val screenviewState = mainViewModel.uiState.collectAsState()
    val user = mainViewModel.ownUser.collectAsState()
    var success = remember { mutableStateOf(false) }

    LaunchedEffect (user.value) {
        newuserName.value = user.value.name
        newuserEmail.value = user.value.email
    }

    when(screenviewState.value) {
        is UiState.Error -> {
            val message = (screenviewState.value as UiState.Error).msg
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
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        UiState.Ready -> {}
        is UiState.Success -> {
            mainViewModel.setStateToReady()
            if(success.value){
                val messagesuccess = "Profile updated successfully!"
                Toast.makeText(LocalContext.current, messagesuccess, Toast.LENGTH_SHORT).show()
            }else{
                navController.navigate(ScreenRoute.Home.route)
            }

        }

    }

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = stringResource(R.string.profile),
            color = TextGray,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp
        )
        HorizontalDivider(color = DSpacer, thickness = 1.dp)
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            mainViewModel.logOut()
        },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White

            ),
            border = BorderStroke(1.dp, color = Color.Red),
            shape = RoundedCornerShape(7.dp)
        ) {
            Text(text = stringResource(R.string.sign_out), color = Color.Black)
        }
        Spacer(modifier = Modifier.height(20.dp))
        HorizontalDivider(color = DSpacer, thickness = 1.dp)
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.edit_profile),
            color = TextGray,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = newuserName.value,
            onValueChange = {
                newuserName.value = it
            },
            placeholder = { Text(user.value.name) },
            label = { Text(stringResource(R.string.new_username))}
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = newuserEmail.value,
            onValueChange = {
                newuserEmail.value = it
            },
            placeholder = { Text(user.value.email) },
            label = { Text(stringResource(R.string.new_email))}
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(onClick = {
            mainViewModel.editOwnUser(user.value.id,newuserEmail.value,newuserName.value)
            success.value = true
        },
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MainGreen
            ),
            border = BorderStroke(1.dp, color = Color.White),
            shape = RoundedCornerShape(7.dp)
        ) {
            Text(text = stringResource(R.string.save_changes), color = Color.White)
        }
        Spacer(modifier = Modifier.height(20.dp))
        HorizontalDivider(color = DSpacer, thickness = 1.dp)
        Spacer(modifier = Modifier.height(50.dp))
        Button(onClick = { navController.navigate(ScreenRoute.AboutUs.route)  },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White

            ),
            border = BorderStroke(1.dp, color = MainGreen),
            shape = RoundedCornerShape(7.dp)
        ) {
            Text(text = stringResource(R.string.about_us), color = TextGray)
        }

    }

}