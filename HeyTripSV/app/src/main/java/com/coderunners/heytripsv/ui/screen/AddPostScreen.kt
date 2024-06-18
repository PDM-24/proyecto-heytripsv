package com.coderunners.heytripsv.ui.screen

import android.view.MotionEvent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coderunners.heytripsv.MainViewModel
import com.coderunners.heytripsv.R
import com.coderunners.heytripsv.model.Position
import com.coderunners.heytripsv.model.PostDataModel
import com.coderunners.heytripsv.ui.components.PhotoSelectorView
import com.coderunners.heytripsv.ui.theme.TextGray
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddPostScreen(
    //mainViewModel: MainViewModel,
    innerPadding: PaddingValues
    ) {

    val _post = remember { mutableStateOf(PostDataModel(position = Position(13.699217, -89.1921333))) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(_post.value.position.lat, _post.value.position.long), 10f)
    }
    val marcador = remember { mutableStateOf(MarkerState(LatLng(_post.value.position.lat, _post.value.position.long)))}
    val columnScrollingEnabled = remember { mutableStateOf(true) }

    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            columnScrollingEnabled.value = true
        }
    }

    //val post = mainViewModel.postAgency.collectAsState()
        Column(
            modifier = androidx.compose.ui.Modifier
                //.padding(innerPadding)
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
                value = "",
                onValueChange = {},
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
            //CustomDatePicker(value = ) {}
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
                value = "",
                onValueChange = {},
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
                value = "",
                onValueChange = {},
                placeholder = { Text(stringResource(id = R.string.meeting_place_placeholder)) },
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
            OutlinedTextField(
                modifier = Modifier
                    .height(100.dp),
                value = "",
                onValueChange = {},
                placeholder = { Text(stringResource(id = R.string.includes_placeholder)) },
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
                    .height(200.dp),
                value = "",
                onValueChange = {},
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
            PhotoSelectorView(maxSelectionCount = 1)
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
                            _post.value = _post.value.copy(position = Position(ubicacion.latitude, ubicacion.longitude))
                            marcador.value = MarkerState(LatLng(ubicacion.latitude, ubicacion.longitude))
                        },
                        cameraPositionState = cameraPositionState
                    ) {
                        Marker(
                            state = marcador.value,
                            title = _post.value.title
                        )
                    }
                }
            }


        }}