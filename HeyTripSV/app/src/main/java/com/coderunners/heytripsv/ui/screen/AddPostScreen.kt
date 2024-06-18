package com.coderunners.heytripsv.ui.screen

import android.net.Uri
import android.view.MotionEvent
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.coderunners.heytripsv.MainViewModel
import com.coderunners.heytripsv.R
import com.coderunners.heytripsv.model.Itinerary
import com.coderunners.heytripsv.model.Position
import com.coderunners.heytripsv.model.PostDataModel
import com.coderunners.heytripsv.ui.components.PhotoSelectorView
import com.coderunners.heytripsv.ui.theme.MainGreen
import com.coderunners.heytripsv.ui.theme.TextGray
import com.coderunners.heytripsv.utils.UiState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddPostScreen(
    mainViewModel: MainViewModel,
    innerPadding: PaddingValues
    ) {

    val selectedImage= remember { mutableStateOf<Uri?>(null) }
    val count = remember { mutableStateOf(1) }
    val itiCount = remember { mutableStateOf(1) }
    val selectedDate = remember { mutableStateOf(LocalDate.now())}
    val post = remember { mutableStateOf(PostDataModel(position = Position(13.699217, -89.1921333))) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(post.value.position.lat, post.value.position.long), 10f)
    }
    val marcador = remember { mutableStateOf(MarkerState(LatLng(post.value.position.lat, post.value.position.long)))}
    val columnScrollingEnabled = remember { mutableStateOf(true) }

    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            columnScrollingEnabled.value = true
        }
    }
    val addScreenState = mainViewModel.uiState.collectAsState()
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

        Column(
            modifier = androidx.compose.ui.Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(75.dp))
            Text(
                text = stringResource(R.string.add_tour),
                color = TextGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 35.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.title),
                color = TextGray,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 10.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = post.value.title,
                onValueChange = {post.value = post.value.copy(title = it)},
                placeholder = { Text("") },
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.date),
                color = TextGray,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 10.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            CustomDatePicker(value = selectedDate.value ) {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                selectedDate.value = it
                post.value = post.value.copy(date = selectedDate.value.format(formatter))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.price),
                color = TextGray,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 10.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = post.value.price.toString(),
                onValueChange = {post.value = post.value.copy(price = it.toDouble())},
                placeholder = { Text("") },
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.meeting_place),
                color = TextGray,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 10.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = post.value.meeting,
                onValueChange = {post.value = post.value.copy(meeting = it)},
                placeholder = { Text("") },
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.includes),
                color = TextGray,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 10.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(count.value){ index->
                OutlinedTextField(
                    modifier = Modifier
                        .height(100.dp),
                    value = post.value.includes[index-1],
                    onValueChange = {post.value.includes[index-1]= it},
                    placeholder = { Text(stringResource(id = R.string.includes_placeholder)) },
                )
                }
                item {
                    Button(onClick = { count.value =count.value+1}) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    }
                }
            }
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
                    .height(200.dp),
                value = post.value.description,
                onValueChange = {post.value = post.value.copy(description = it)},
                placeholder = { Text(stringResource(id = R.string.description_placeholder)) },
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.publication_image),
                color = TextGray,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 10.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            PhotoSelectorView(selectImage = {selectedImage.value= it})
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.itinerary),
                color = TextGray,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 10.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(itiCount.value){ index->
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                    ){

                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth(0.25f),
                            value = post.value.itinerary[index-1].time,
                            onValueChange = {post.value.itinerary[index-1].time= it},
                            placeholder = { Text(stringResource(id = R.string.includes_placeholder)) },
                        )
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth(1f),
                            value = post.value.itinerary[index-1].event,
                            onValueChange = {post.value.itinerary[index-1].event= it},
                            placeholder = { Text(stringResource(id = R.string.includes_placeholder)) },
                        )

                    }

                }
                item {
                    Button(onClick = { itiCount.value +=1}) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState(), columnScrollingEnabled.value),
            ){
                //Componente del mapa (Lo tengo en un row para centrarlo)
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ){
                    GoogleMap(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .aspectRatio(1.25f)
                            .pointerInteropFilter(
                                onTouchEvent = {
                                    when (it.action) {
                                        MotionEvent.ACTION_DOWN -> {
                                            columnScrollingEnabled.value = false
                                            false
                                        }

                                        else -> {
                                            true
                                        }
                                    }
                                }),
                        onMapClick = {
                                ubicacion ->
                            post.value = post.value.copy(position = Position(ubicacion.latitude, ubicacion.longitude))
                            marcador.value = MarkerState(LatLng(ubicacion.latitude, ubicacion.longitude))
                        },
                        cameraPositionState = cameraPositionState
                    ) {
                        Marker(
                            state = marcador.value,
                            title = post.value.title
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                              mainViewModel.addPost(post.value.copy(itinerary = post.value.itinerary.map {
                                  it-> Itinerary(
                                  timeToIso(it.time), it.event) }),
                                  selectedImage.value)
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
        }}

fun timeToIso (time: String): String{
    // Parse the time string to LocalTime
    val time = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"))

    // Get today's date as LocalDate
    val today = LocalDate.now()

    // Combine today's date and parsed time to LocalDateTime
    val dateTime = LocalDateTime.of(today, time)

    // Format the LocalDateTime to the desired format
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    val formattedDateTime = dateTime.format(formatter)

    return formattedDateTime
}