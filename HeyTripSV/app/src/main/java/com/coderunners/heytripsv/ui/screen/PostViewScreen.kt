package com.coderunners.heytripsv.ui.screen

import android.view.MotionEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coderunners.heytripsv.MainViewModel
import com.coderunners.heytripsv.R
import com.coderunners.heytripsv.model.PostDataModel
import com.coderunners.heytripsv.ui.theme.AddGreen
import com.coderunners.heytripsv.ui.theme.NavGray
import com.coderunners.heytripsv.ui.theme.TextGray
import com.coderunners.heytripsv.ui.theme.White
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PostViewScreen(
    innerPadding: PaddingValues,
    viewModel: MainViewModel
){
    val post = viewModel.selectedPost.collectAsState()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(post.value.position.lat, post.value.position.long), 10f)
    }
    val columnScrollingEnabled = remember { mutableStateOf(true) }

    // Use a LaunchedEffect keyed on the camera moving state to enable column scrolling when the camera stops moving
    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            columnScrollingEnabled.value = true
        }
    }

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .verticalScroll(rememberScrollState(), columnScrollingEnabled.value),
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.5f),
            painter = painterResource(id = post.value.image),
            contentDescription = "Photo",
            contentScale = ContentScale.Crop
        )
        Text(text = post.value.title, fontWeight = FontWeight.Bold, modifier = Modifier.padding(10.dp), fontSize = 18.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(text = (post.value.date + " - $" + "%.2f".format(post.value.price)), modifier = Modifier.padding(10.dp, 0.dp), color = NavGray)
        Text(text = (stringResource(R.string.provided_by) + " " + post.value.agency), modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 10.dp), color = NavGray)
        Button(onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(
                containerColor = AddGreen
            ),
            modifier = Modifier.padding(10.dp)) {
            Text(text = stringResource(R.string.contact_wha), color = White)
        }
        Spacer(modifier = Modifier.padding(10.dp))

        Text(text = stringResource(R.string.description) + ":", fontWeight = FontWeight.Bold, modifier = Modifier.padding(10.dp, 0.dp))
        Text(text = post.value.description, fontWeight = FontWeight.Normal, modifier = Modifier.padding(10.dp))
        Spacer(modifier = Modifier.padding(10.dp))
        Text(text = stringResource(R.string.meeting) + ":", fontWeight = FontWeight.Bold, modifier = Modifier.padding(10.dp, 0.dp))
        Text(text = post.value.meeting, fontWeight = FontWeight.Normal, modifier = Modifier.padding(10.dp))
        Spacer(modifier = Modifier.padding(10.dp))
        Text(text = stringResource(R.string.itinerary) + ":", fontWeight = FontWeight.Bold, modifier = Modifier.padding(10.dp, 0.dp))
        Text(text = makeBulletedList(items = (post.value.itinerary.map { it.time + ": " + it.event })), modifier = Modifier.padding(10.dp))
        Spacer(modifier = Modifier.padding(10.dp))
        Text(text = stringResource(R.string.includes) + ":", fontWeight = FontWeight.Bold, modifier = Modifier.padding(10.dp, 0.dp))
        Text(text = makeBulletedList(items = post.value.includes), modifier = Modifier.padding(10.dp))
        Spacer(modifier = Modifier.padding(10.dp))
        Text(text = stringResource(R.string.destination) + ":", fontWeight = FontWeight.Bold, modifier = Modifier.padding(10.dp))
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
                cameraPositionState = cameraPositionState
            ) {
                Marker(
                    state = MarkerState(position = LatLng(post.value.position.lat, post.value.position.long)),
                    title = post.value.title
                )
            }
        }
    }
}

@Composable
fun makeBulletedList(items: List<String>): AnnotatedString {
    val bulletString = "\u2022\t\t"
    val textStyle = LocalTextStyle.current
    val textMeasurer = rememberTextMeasurer()
    val bulletStringWidth = remember(textStyle, textMeasurer) {
        textMeasurer.measure(text = bulletString, style = textStyle).size.width
    }
    val restLine = with(LocalDensity.current) { bulletStringWidth.toSp() }
    val paragraphStyle = ParagraphStyle(textIndent = TextIndent(restLine = restLine))

    return buildAnnotatedString {
        items.forEach { text ->
            withStyle(style = paragraphStyle) {
                append(bulletString)
                append(text)
            }
        }
    }
}