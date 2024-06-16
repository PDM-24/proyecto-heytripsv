package com.coderunners.heytripsv.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.coderunners.heytripsv.R
import com.coderunners.heytripsv.ui.navigation.ScreenRoute
import com.coderunners.heytripsv.ui.theme.DSpacer
import com.coderunners.heytripsv.ui.theme.MainGreen
import com.coderunners.heytripsv.ui.theme.TextGray

@Composable
fun UserProfile(innerPadding : PaddingValues, navController: NavController){

    val newuserName = remember { mutableStateOf("") }
    val newuserEmail = remember { mutableStateOf("") }

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
            placeholder = { Text("") },
            label = { Text(stringResource(R.string.new_username))}
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = newuserEmail.value,
            onValueChange = {
                newuserEmail.value = it
            },
            placeholder = { Text("") },
            label = { Text(stringResource(R.string.new_email))}
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(onClick = {
            //TODO Update User
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
            modifier = Modifier.fillMaxWidth(0.8f)
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