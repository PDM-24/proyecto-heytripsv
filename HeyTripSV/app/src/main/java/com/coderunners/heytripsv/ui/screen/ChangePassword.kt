package com.coderunners.heytripsv.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.coderunners.heytripsv.R
import com.coderunners.heytripsv.ui.navigation.ScreenRoute
import com.coderunners.heytripsv.ui.theme.MainGreen
import com.coderunners.heytripsv.ui.theme.NavGray
import com.coderunners.heytripsv.ui.theme.TextGray

@Composable
fun ChangePass(navController: NavController){

    val newPassword = remember { mutableStateOf("") }
    val newPasswordConf = remember { mutableStateOf("") }
    val similarityStatus: MutableState<Boolean> = remember { mutableStateOf(false) }
    val dialogStatus: MutableState<Boolean> = remember { mutableStateOf(false) }

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
                text = stringResource(R.string.change_passw),
                color = TextGray,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                style = TextStyle(letterSpacing = 0.5.sp),
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(40.dp))
            Image(
                painter = painterResource(id = R.drawable.change_pass),
                contentDescription = "email",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(40.dp))
            OutlinedTextField(
                value = newPassword.value,
                onValueChange = {
                    newPassword.value = it
                },
                placeholder = { Text("") },
                label = { Text(stringResource(R.string.new_pass)) }
            )
            Spacer(modifier = Modifier.height(40.dp))
            OutlinedTextField(
                value = newPasswordConf.value,
                onValueChange = {
                    newPasswordConf.value = it
                },
                placeholder = { Text("") },
                label = { Text(stringResource(R.string.confirm_pass)) }
            )
            Spacer(modifier = Modifier.height(40.dp))
            Button(
                onClick = {
                    if (newPassword.value != newPasswordConf.value) {
                        similarityStatus.value = true
                        dialogStatus.value = true
                    } else {
                        navController.navigate(ScreenRoute.RegisterAgency.route)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MainGreen
                ),
                border = BorderStroke(1.dp, color = Color.White),
                shape = RoundedCornerShape(7.dp),
                enabled = newPassword.value.isNotBlank() && newPasswordConf.value.isNotBlank()
            ) {
                Text(text = stringResource(R.string.confirm_pass), color = Color.White)
            }

            if (similarityStatus.value && dialogStatus.value) {
                AlertDialog(
                    title = {
                        Text(
                            text = stringResource(R.string.pass_missmatch_title),
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                        )
                    },
                    text = {
                        Text(
                            text = stringResource(R.string.pass_missmatch),
                            color = Color.Black,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                        )
                    },
                    onDismissRequest = { dialogStatus.value = false },
                    confirmButton = {
                        Button(
                            onClick = { dialogStatus.value = false },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                        )
                        {
                            Text(text = stringResource(id = R.string.conf_ac), color = Color.Black)
                        }
                    },
                    containerColor = MainGreen
                )
            }

        }
    }

}