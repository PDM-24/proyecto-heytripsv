package com.coderunners.heytripsv.ui.screen

import android.app.TimePickerDialog
import android.net.Uri
import android.util.Log
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
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
import com.coderunners.heytripsv.ui.components.DropDown
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

fun isoDateFormat(dateToFormat: String): String{
    try {
        var _dateToFormat = dateToFormat.replace(' ', 'T')
        val dateTime = LocalDateTime.parse(_dateToFormat)

        val formatter = DateTimeFormatter.ofPattern("hh:mm a")
        return dateTime.format(formatter)
    }catch(e: Exception){
        Log.i("MainViewModel", e.toString())
        return "00:00 AM"
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddPostScreen(
    mainViewModel: MainViewModel,
    innerPadding: PaddingValues
    ) {

    val timeindex = remember{ mutableStateOf(-1)}
    val timeState = remember {mutableStateListOf<TimePickerState>(TimePickerState(0,0,true))}
    val categoryOptions = listOf(stringResource(R.string.mountains), stringResource(R.string.beaches), stringResource(R.string.towns), stringResource(R.string.routes), stringResource(R.string.report_other))
    val context = LocalContext.current
    val selectedImage= remember { mutableStateOf<Uri?>(null) }
    val count = remember { mutableStateOf(1) }
    val showTimeDialog = remember { mutableStateOf(false)}
    val itiCount = remember { mutableStateOf(1) }
    val selectedDate = remember { mutableStateOf(LocalDate.now())}
    val priceText = remember { mutableStateOf("") }
    val post = remember { mutableStateOf(PostDataModel(
        position = Position(13.699217, -89.1921333),
        itinerary = mutableListOf(Itinerary("00:00", "")),
        includes = mutableListOf(""))) }
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

    if(showTimeDialog.value){
        TimeDialog(onConfirm = {
                               var itinerary = post.value.itinerary
                var time = ""
            if (timeState[timeindex.value].minute<10){
                if (timeState[timeindex.value].hour<10){
                   time = timeToIso("0"+timeState[timeindex.value].hour.toString()+":"+"0"+timeState[timeindex.value].minute.toString())
                }
                else time = timeToIso(timeState[timeindex.value].hour.toString()+":"+"0"+timeState[timeindex.value].minute.toString())
            }

            else {
                if (timeState[timeindex.value].hour<10){
                    time = timeToIso("0"+timeState[timeindex.value].hour.toString()+":"+timeState[timeindex.value].minute.toString())
                }
                else time = timeToIso(timeState[timeindex.value].hour.toString()+":"+timeState[timeindex.value].minute.toString())
            }
            Log.i("time post", time)
                                itinerary[timeindex.value].time = time
            post.value = post.value.copy(itinerary = itinerary)
        }, state = timeState[timeindex.value] ) {
            showTimeDialog.value = false
        }
    }else{
        showTimeDialog.value = false
    }

        LazyColumn(
            modifier = androidx.compose.ui.Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            userScrollEnabled = columnScrollingEnabled.value,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(75.dp))
                Text(
                    text = stringResource(R.string.add_tour),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 35.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.title),
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
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(horizontal = 10.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = priceText.value,
                    onValueChange = {
                        priceText.value = it},
                    placeholder = { Text("") },
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.categories),
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(horizontal = 10.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp),
                    horizontalArrangement = Arrangement.Center
                ){
                    DropDown(
                        options = categoryOptions ,
                        onValueChange = {
                            var content = ""

                            content = when (it) {
                                categoryOptions[0] -> "Montañas"
                                categoryOptions[1] -> "Playas"
                                categoryOptions[2] -> "Pueblos"
                                categoryOptions[3] -> "Rutas"
                                categoryOptions[4] -> "Otros"

                                else -> it
                            }
                            post.value = post.value.copy(category = content)

                        },
                        initialValue = categoryOptions[0]
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.meeting_place),
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
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(horizontal = 10.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
                items(count.value){ index->
                OutlinedTextField(
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth(0.8f),
                    value = post.value.includes[index],
                    onValueChange = {
                        val newList = post.value.includes.toMutableList().apply {
                            this[index] = it
                        }
                        post.value = post.value.copy(includes = newList)},
                    placeholder = { Text(stringResource(id = R.string.includes_placeholder)) },
                )
                }
                item {
                    Button(onClick = {
                        post.value.includes.add("")
                        count.value =count.value+1}) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    }
                }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.description),
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(horizontal = 10.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth(0.8f),
                    value = post.value.description,
                    onValueChange = {post.value = post.value.copy(description = it)},
                    placeholder = { Text(stringResource(id = R.string.description_placeholder)) },
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.publication_image),
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(horizontal = 10.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                PhotoSelectorView(selectImage = {selectedImage.value= it
                Log.i("Selected image", selectedImage.value.toString())})
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.itinerary),
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(horizontal = 10.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
                items(itiCount.value){ index->
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                    ){
                        TextButton(onClick = {
                            timeindex.value = index
                            showTimeDialog.value = true
                        }) {
                            Text(text = isoDateFormat(post.value.itinerary[index].time))
                        }
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth(1f),
                            value = post.value.itinerary[index].event,
                            onValueChange = {
                                val newList = post.value.itinerary.toMutableList().apply {
                                    this[index] = this[index].copy(event = it)
                                }
                                post.value = post.value.copy(itinerary = newList)
                                            },
                            placeholder = { Text("") },
                        )

                    }

                }
                item {
                    Button(onClick = {
                        post.value.itinerary.add(Itinerary("00:00", ""))
                        timeState.add(TimePickerState(0,0,true))
                        itiCount.value +=1}) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    }
                }
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Column(
                    modifier = Modifier
                        .padding(innerPadding),
                ){
                    //Componente del mapa
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
                                post.value = post.value.copy(
                                    position = Position(ubicacion.latitude, ubicacion.longitude),
                                    price = priceText.value.toDouble()
                                )
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
                            val times = timeState.map {
                                if (it.minute<10){
                                    if (it.hour<10){
                                        timeToIso("0"+it.hour.toString()+":"+"0"+it.minute.toString())
                                    }
                                    else timeToIso(it.hour.toString()+":"+"0"+it.minute.toString())
                                }

                                else {
                                    if (it.hour<10){
                                        timeToIso("0"+it.hour.toString()+":"+it.minute.toString())
                                    }
                                    else timeToIso(it.hour.toString()+":"+it.minute.toString())
                                }
                            }

                            Log.i("MainViewModel", times.toString())
//                            mainViewModel.addPost(
//                                context,
//                                post.value.copy(itinerary = post.value.itinerary.map { it-> Itinerary(
//                                timeToIso(it.time), it.event) }.toMutableList()),
//                                selectedImage.value)
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


        }}

fun timeToIso (time: String): String{

    //Desired format
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    // Get today's date as LocalDate
    val today = LocalDate.now()

    Log.i("view", time)
    if (time.trim() == ""){
        val hour = LocalTime.parse("00:00", DateTimeFormatter.ofPattern("HH:mm"))
        val dateTime = LocalDateTime.of(today, hour)
       return dateTime.format(formatter)
    }

    // Parse the time string to LocalTime
    val hour = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"))

    // Combine today's date and parsed time to LocalDateTime
    val dateTime = LocalDateTime.of(today, hour)

    // Format the LocalDateTime to the desired format
    return dateTime.format(formatter)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeDialog(onConfirm: ()->Unit, state: TimePickerState, onDismissRequest: ()->Unit){
    Dialog(onDismissRequest = {  }) {
            Card(modifier = Modifier.size(250.dp, 200.dp),) {
                Column (
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                TimeInput(
                    modifier = Modifier
                        .padding(10.dp),

                    state = state)
                Row (){
                    TextButton(onClick = { onDismissRequest() }) {
                        Text(text = "Cancelar")
                    }
                    TextButton(onClick = {
                        onConfirm()
                        onDismissRequest()}) {
                        Text(text = "Confirmar")
                    }
                }
                }

        }

    }
}