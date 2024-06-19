package com.coderunners.heytripsv.ui.screen

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.coderunners.heytripsv.MainViewModel
import com.coderunners.heytripsv.R
import com.coderunners.heytripsv.model.AgencyDataModel
import com.coderunners.heytripsv.ui.components.PhotoSelectorView
import com.coderunners.heytripsv.ui.theme.MainGreen
import com.coderunners.heytripsv.ui.theme.TextGray

@Composable
fun RegisterAgency(
    innerPadding : PaddingValues,
    navController: NavController
){

    var name = remember { mutableStateOf("") }
    var email = remember { mutableStateOf("") }
    var dui = remember { mutableStateOf("") }
    var description = remember { mutableStateOf("") }
    var contactnumber = remember { mutableStateOf("") }
    var instagram = remember { mutableStateOf("") }
    var facebook = remember { mutableStateOf("") }
    val selectedImage= remember { mutableStateOf<Uri?>(null) }

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(75.dp))
        Text(
            text = stringResource(R.string.edit_agency),
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
            value = name.value,
            onValueChange = { name.value = it },
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
            value = email.value,
            onValueChange = { email.value = it },
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
            value = dui.value,
            onValueChange = {dui.value = it},
            placeholder = { Text("") },
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.description),
            color = TextGray,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(horizontal = 10.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = description.value,
            onValueChange = { description.value = it},
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
            value = contactnumber.value,
            onValueChange = {contactnumber.value = it},
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
            value = instagram.value,
            onValueChange = {instagram.value = it},
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
            value = facebook.value,
            onValueChange = {facebook.value = it},
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
        PhotoSelectorView(selectImage = {selectedImage.value= it})
        Spacer(modifier = Modifier.height(15.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {},
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