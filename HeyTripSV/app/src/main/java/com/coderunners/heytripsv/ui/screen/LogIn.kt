package com.coderunners.heytripsv.ui.screen

import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.coderunners.heytripsv.MainViewModel
import com.coderunners.heytripsv.R
import com.coderunners.heytripsv.model.LogInData
import com.coderunners.heytripsv.ui.navigation.BottomNavigationBar
import com.coderunners.heytripsv.ui.navigation.ScreenRoute
import com.coderunners.heytripsv.ui.theme.MainGreen
import com.coderunners.heytripsv.ui.theme.NavGray
import com.coderunners.heytripsv.ui.theme.TextGray
import com.coderunners.heytripsv.ui.theme.White
import com.coderunners.heytripsv.utils.UiState
import com.google.android.gms.common.api.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun LogIn( navController: NavController, mainViewModel: MainViewModel) {
    val logInData = remember {
        mutableStateOf(LogInData())
    }
    val userRole = mainViewModel.userRole.collectAsState()

    val logInViewState = mainViewModel.uiState.collectAsState()

    when(logInViewState.value) {
        is UiState.Error -> {
            val message = (logInViewState.value as UiState.Error).msg
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

            if (userRole.value == "agency"){
                //TODO: PANTALLA INICIAL AGENCY
                navController.navigate(ScreenRoute.addPost.route)
            }else if(userRole.value == "admin"){
                navController.navigate(ScreenRoute.Home.route)
            } else {
                navController.navigate(ScreenRoute.Home.route)
            }
        }
        else->{

        }
    }

    val annotatedStringPass = AnnotatedString.Builder().apply {

        append(stringResource(id = R.string.fg_password))
        addStyle(
            style = SpanStyle(color = MainGreen),

            start = 0,
            end = length
        )
    }.toAnnotatedString()

    val annotatedStringSignUp = AnnotatedString.Builder().apply {

        append(stringResource(id = R.string.sign_up))
        addStyle(
            style = SpanStyle(color = MainGreen),

            start = 0,
            end = length
        )
    }.toAnnotatedString()

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
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(75.dp))
            Text(
                text = stringResource(R.string.log_in),
                color = if (isSystemInDarkTheme()) White else TextGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 35.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(R.string.email),
                color = if (isSystemInDarkTheme()) White else TextGray,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = logInData.value.email,
                onValueChange = {
                    logInData.value = logInData.value.copy(email = it)
                },
                placeholder = { Text("") },
            )
            Spacer(modifier = Modifier.height(25.dp))
            Text(
                text = stringResource(R.string.password),
                color = if (isSystemInDarkTheme()) White else TextGray,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = logInData.value.password,
                onValueChange = { logInData.value = logInData.value.copy(password = it) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Spacer(modifier = Modifier.height(15.dp))
            ClickableText(
                text = annotatedStringPass,

                onClick = {
                    navController.navigate(ScreenRoute.ForgotPassword.route)
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 16.dp)

            )
            Spacer(modifier = Modifier.height(15.dp))
            Button(
                onClick = {
                    mainViewModel.LogIn(logInData.value)
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MainGreen

                ),
                border = BorderStroke(1.dp, color = Color.White),
                shape = RoundedCornerShape(7.dp)
            ) {
                Text(text = stringResource(id = R.string.register), color = Color.White)
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.dont_re),
                    color = if (isSystemInDarkTheme()) White else TextGray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(end= 5.dp)
                )
                ClickableText(
                    text = annotatedStringSignUp,
                    onClick = {
                        navController.navigate(ScreenRoute.CreateAccount.route)
                    }
                )
            }

        }
    }

}


