package com.coderunners.heytripsv.ui.screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.coderunners.heytripsv.MainViewModel
import com.coderunners.heytripsv.R
import com.coderunners.heytripsv.model.EmailAccount
import com.coderunners.heytripsv.ui.navigation.ScreenRoute
import com.coderunners.heytripsv.ui.theme.MainGreen
import com.coderunners.heytripsv.ui.theme.NavGray
import com.coderunners.heytripsv.ui.theme.TextGray
import com.coderunners.heytripsv.utils.UiState

@Composable
fun ConfirmCode(navController: NavController, mainViewModel: MainViewModel) {

    val confirmationCode = remember { mutableStateOf("") }
    val screenviewState = mainViewModel.uiState.collectAsState()
    val email = mainViewModel.email.collectAsState()
    val buttonState = remember { mutableStateOf(true) }

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
            if(buttonState.value){
                navController.navigate(ScreenRoute.ChangePassowrd.route)
            }else{
                navController.navigate(ScreenRoute.ConfirmationCode.route)
            }
        }
        else->{

        }

    }

    val annotatedStringConfCode = AnnotatedString.Builder().apply {

        append(stringResource(R.string.send_code_again))
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
                text = stringResource(R.string.conf_code),
                color = TextGray,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                style = TextStyle(letterSpacing = 0.5.sp),
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.email_opened),
                contentDescription = "email",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = stringResource(R.string.conf_cod_inst),
                color = TextGray,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Justify,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = confirmationCode.value,
                onValueChange = {
                    confirmationCode.value = it
                },
                placeholder = { Text("") },
            )
            Spacer(modifier = Modifier.height(40.dp))
            Button(
                onClick = {
                    mainViewModel.compareCode(confirmationCode.value)
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MainGreen

                ),
                border = BorderStroke(1.dp, color = Color.White),
                shape = RoundedCornerShape(7.dp),
                enabled = confirmationCode.value.isNotBlank()
            ) {
                Text(text = stringResource(R.string.conf_ac), color = Color.White)
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                ClickableText(
                    text = annotatedStringConfCode,
                    onClick = {
                        buttonState.value = false
                        mainViewModel.SendCode(EmailAccount(email = email.value))
                    }
                )

            }

        }
    }
}