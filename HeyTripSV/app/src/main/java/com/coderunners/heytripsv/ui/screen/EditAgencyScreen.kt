package com.coderunners.heytripsv.ui.screen

import android.net.Uri
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.coderunners.heytripsv.MainViewModel
import com.coderunners.heytripsv.R
import com.coderunners.heytripsv.data.remote.api.AgencyApi
import com.coderunners.heytripsv.ui.components.PhotoSelectorView
import com.coderunners.heytripsv.ui.navigation.ScreenRoute
import com.coderunners.heytripsv.ui.theme.AddGreen
import com.coderunners.heytripsv.ui.theme.MainGreen
import com.coderunners.heytripsv.ui.theme.TextGray
import com.coderunners.heytripsv.utils.createFilePart

@Composable
fun EditAgencyScreen(
    mainViewModel: MainViewModel,
    innerPadding: PaddingValues,
    navController: NavController
) {

    val context = LocalContext.current
    val selectedImage= remember { mutableStateOf<Uri?>(null) }
    val agency = mainViewModel.selectedAgency.collectAsState()
    val numberValue = remember { mutableStateOf(agency.value.number.toString()) }

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
            value = agency.value.name,
            onValueChange = {mainViewModel.setSelectedAgency(agency.value.copy(name = it))},
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
            onValueChange = {mainViewModel.setSelectedAgency(agency.value.copy(email = it))},
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
            onValueChange = {mainViewModel.setSelectedAgency(agency.value.copy(dui = it))},
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
            modifier = Modifier
                .fillMaxWidth(0.8f),
            value = agency.value.desc,
            onValueChange = {mainViewModel.setSelectedAgency(agency.value.copy(desc = it))},
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
            value = agency.value.number,
            onValueChange = {mainViewModel.setSelectedAgency(agency.value.copy(number = it))},
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
            onValueChange = {mainViewModel.setSelectedAgency(agency.value.copy(instagram = it))},
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
            value = agency.value.instagram,
            onValueChange = {mainViewModel.setSelectedAgency(agency.value.copy(facebook = it))},
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
        PhotoSelectorView(selectImage = {selectedImage.value=it})
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
                          mainViewModel.editOwnAgency(
                              context ,
                              AgencyApi(
                                  name = agency.value.name,
                                  email = agency.value.email,
                                  dui = agency.value.dui,
                                  description = agency.value.desc,
                                  number=numberValue.value,
                                  instagram = agency.value.instagram,
                                  facebook = agency.value.facebook,
                                  image = selectedImage.value
                              )
                          )
                    navController.navigate(ScreenRoute.Agency.route)
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