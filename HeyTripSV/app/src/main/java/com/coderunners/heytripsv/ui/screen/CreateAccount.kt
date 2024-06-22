package com.coderunners.heytripsv.ui.screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.wear.compose.material.ContentAlpha
import com.coderunners.heytripsv.MainViewModel
import com.coderunners.heytripsv.R
import com.coderunners.heytripsv.model.CreateUserData
import com.coderunners.heytripsv.model.LogInData
import com.coderunners.heytripsv.ui.navigation.ScreenRoute
import com.coderunners.heytripsv.ui.theme.MainGreen
import com.coderunners.heytripsv.ui.theme.NavGray
import com.coderunners.heytripsv.ui.theme.TextGray
import com.coderunners.heytripsv.utils.UiState

@Composable
 fun CreateAccount(navController: NavController, mainViewModel: MainViewModel) {
    var nameError by remember { mutableStateOf(false) }
    val createUserData = remember {
        mutableStateOf(CreateUserData())
    }
    val createUserState = mainViewModel.uiState.collectAsState()

    val passwordPattern =
        """^(?=.[a-z])(?=.[A-Z])(?=.[0-9])(?=.[!¿#$&*+\-.:@\[\]^_{}~])(?=.{8,32})""".toRegex()

    val context = LocalContext.current

    fun isPasswordValid(password: String): Boolean {
        return passwordPattern.matches(password)
    }

    when (createUserState.value) {
        is UiState.Error -> {
            val message = (createUserState.value as UiState.Error).msg
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
            navController.navigate(ScreenRoute.LogIn.route)
        }

        else -> {
        }
    }

    val annotatedStringPass = AnnotatedString.Builder().apply {

        append(stringResource(id = R.string.password))
        addStyle(
            style = SpanStyle(color = MainGreen),

            start = 0,
            end = length
        )
    }.toAnnotatedString()

    val annotatedStringRegister = AnnotatedString.Builder().apply {

        append(stringResource(id = R.string.agency))
        addStyle(
            style = SpanStyle(color = MainGreen),

            start = 0,
            end = length
        )
    }.toAnnotatedString()

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
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
                text = stringResource(R.string.create_ac),
                color = TextGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 35.sp
            )
            Spacer(modifier = Modifier.height(15.dp))
            ClickableText(
                text = annotatedStringRegister,

                onClick = {

                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 16.dp)

            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = stringResource(R.string.name),
                color = TextGray,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = createUserData.value.name,
                onValueChange = {
                    createUserData.value = createUserData.value.copy(name = it)
                },
                placeholder = { Text("") },
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = stringResource(R.string.email),
                color = TextGray,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = createUserData.value.email,
                onValueChange = {
                    createUserData.value = createUserData.value.copy(email = it)
                },
                placeholder = { Text("") },
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = stringResource(R.string.password),
                color = TextGray,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = createUserData.value.password,
                onValueChange = { createUserData.value = createUserData.value.copy(password = it) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = stringResource(R.string.confirm_pass),
                color = TextGray,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 16.dp)
            )
            OutlinedTextField(
                value = createUserData.value.confirmPassword,
                onValueChange = {
                    createUserData.value = createUserData.value.copy(confirmPassword = it)
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Spacer(modifier = Modifier.height(15.dp))
            Button(
                onClick = {
                    if (createUserData.value.password != createUserData.value.confirmPassword){
                        Toast.makeText(context, R.string.password_confirmation, Toast.LENGTH_SHORT).show()
                    }else{
                        mainViewModel.CreateUser(createUserData.value)
                    }
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
                Text(text = stringResource(id = R.string.sign_up), color = Color.White)
            }
        }
    }
}

