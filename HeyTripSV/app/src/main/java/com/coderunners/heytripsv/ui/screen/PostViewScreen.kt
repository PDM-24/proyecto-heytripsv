package com.coderunners.heytripsv.ui.screen

import android.view.MotionEvent
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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.coderunners.heytripsv.MainViewModel
import com.coderunners.heytripsv.R
import com.coderunners.heytripsv.model.PostDataModel
import com.coderunners.heytripsv.ui.components.ReportDialog
import com.coderunners.heytripsv.ui.navigation.BottomNavigationBar
import com.coderunners.heytripsv.ui.navigation.ScreenRoute
import com.coderunners.heytripsv.ui.navigation.navBarItemList
import com.coderunners.heytripsv.ui.theme.AddGreen
import com.coderunners.heytripsv.ui.theme.MainGreen
import com.coderunners.heytripsv.ui.theme.NavGray
import com.coderunners.heytripsv.ui.theme.TextGray
import com.coderunners.heytripsv.ui.theme.White
import com.coderunners.heytripsv.utils.UiState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PostViewScreen(
    currentRoute: String?,
    viewModel: MainViewModel,
    navController: NavHostController
){
    val radioOptions = listOf(
        stringResource(R.string.report_post1), stringResource(R.string.report_post2), stringResource(R.string.report_post3), stringResource(R.string.report_post4), stringResource(R.string.report_other)
    )

    val savedIds = viewModel.savedIDs.collectAsState()
    val userRole = viewModel.userRole.collectAsState()
    val post = viewModel.selectedPost.collectAsState()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(post.value.position.lat, post.value.position.long), 10f)
    }
    val columnScrollingEnabled = remember { mutableStateOf(true) }

    val reportDialog = remember {
        mutableStateOf(false)
    }
    val dialogOpen = remember { mutableStateOf(false)}

    when (dialogOpen.value) {
        true -> {
            Dialog(onDismissRequest = {}) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(10.dp),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    )
                    {
                        Text(text = stringResource(id = R.string.save_logged))
                        Spacer(modifier = Modifier.padding(10.dp))
                        Button(
                            onClick = {
                                dialogOpen.value = false
                                navController.navigate(ScreenRoute.LogIn.route)
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
                            Text(
                                text = stringResource(id = R.string.log_in),
                                color = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.padding(5.dp))
                        Button(
                            onClick = {
                                dialogOpen.value = false
                                navController.navigate(ScreenRoute.Home.route)
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .padding(horizontal = 16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White

                            ),
                            border = BorderStroke(1.dp, color = MainGreen),
                            shape = RoundedCornerShape(7.dp)
                        )
                        {
                            Text(text = stringResource(id = R.string.navbar_home))
                        }
                    }
                }
            }
        }

        false -> {
            dialogOpen.value = false
        }
    }

    when(reportDialog.value){
        true -> {
            if (userRole.value == "user"){
                ReportDialog(radioOptions = radioOptions, onDismissRequest = { reportDialog.value = false }, onConfirm = {
                    var content = ""

                    content = when(it){
                        radioOptions[0] -> "La publicación no contiene un tour"
                        radioOptions[1] -> "Imagen inapropiada"
                        radioOptions[2] -> "Descripción inapropiada"
                        radioOptions[3] -> "Spam"
                        else -> it
                    }
                    viewModel.reportContent(post.value.id, content)
                })
            }else{
                Dialog(onDismissRequest = {reportDialog.value = false}) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .padding(10.dp),
                        shape = RoundedCornerShape(16.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Text(text = stringResource(id = R.string.report_logged))
                            Spacer(modifier = Modifier.padding(10.dp))
                            Button(
                                onClick = {
                                    reportDialog.value = false
                                    navController.navigate(ScreenRoute.LogIn.route)
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
                                Text(
                                    text = stringResource(id = R.string.log_in),
                                    color = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.padding(5.dp))
                            Button(
                                onClick = {
                                    reportDialog.value = false
                                },
                                modifier = Modifier
                                    .fillMaxWidth(0.8f)
                                    .padding(horizontal = 16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.White
                                ),
                                border = BorderStroke(1.dp, color = MainGreen),
                                shape = RoundedCornerShape(7.dp)
                            )
                            {
                                Text(text = stringResource(id = R.string.back))
                            }
                        }
                    }
                }
            }
        }
        false -> { reportDialog.value = false }
    }
    // Use a LaunchedEffect keyed on the camera moving state to enable column scrolling when the camera stops moving
    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            columnScrollingEnabled.value = true
        }
    }
    // Estado para controlar el estado de la interfaz desde viewModel
    val postViewState = viewModel.uiState.collectAsState()

    when(postViewState.value){
        is UiState.Error -> {
            reportDialog.value = false
            val message = (postViewState.value as UiState.Error).msg
            Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show()
            viewModel.setStateToReady()
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
            if((postViewState.value as UiState.Success).msg == "Post saved"){
                viewModel.setStateToReady()
            }else{
                viewModel.setStateToReady()
                if (reportDialog.value){
                    reportDialog.value = false
                    Toast.makeText(LocalContext.current, stringResource(id = R.string.post_reported), Toast.LENGTH_SHORT).show()
                }else{
                    if (navController.currentBackStackEntry?.destination?.route == ScreenRoute.PostView.route){
                        navController.navigate(ScreenRoute.Agency.route)
                    }
                }
            }
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(itemsList = navBarItemList(), currentRoute = currentRoute) {
                    currentNavigationItem ->
                navController.navigate(currentNavigationItem.route){
                    navController.graph.startDestinationRoute?.let{startDestinationRoute ->
                        popUpTo(startDestinationRoute){
                            saveState = false
                        }
                    }
                    launchSingleTop=true
                    restoreState = true
                }
            }
        },
        topBar = {
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically){
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
                Icon(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(30.dp)
                            .fillMaxHeight()
                            .clickable {
                                if (userRole.value == "user"){
                                    viewModel.savePost(post.value.id)
                                }
                            },
                imageVector = ImageVector.vectorResource(
                    if (userRole.value == "user" && savedIds.value.contains(post.value.id)) R.drawable.bookmark else R.drawable.bookmark_outlined),
                contentDescription = "Back",
                tint = NavGray
                )

            }

        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState(), columnScrollingEnabled.value),
        ) {

            AsyncImage(
                model = post.value.image, contentDescription = post.value.title, modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = post.value.title,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(10.dp),
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Icon(painter = painterResource(R.drawable.flag),
                    contentDescription = "Flag",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            reportDialog.value = true
                        })
            }
            Text(
                text = (post.value.date + " - $" + "%.2f".format(post.value.price)),
                modifier = Modifier.padding(10.dp, 0.dp),
                color = NavGray
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = (stringResource(R.string.provided_by) + " "),
                    modifier = Modifier.wrapContentHeight(),
                    color = NavGray
                )
                ClickableText(
                    text = AnnotatedString(post.value.agency),
                    modifier = Modifier.wrapContentHeight(),
                    style = TextStyle(color = Color(0xFF3366BB))
                ) {
                    viewModel.getAgencyData(post.value.agencyId)
                }
            }
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AddGreen
                ),
                modifier = Modifier.padding(10.dp)
            ) {
                Text(text = stringResource(R.string.contact_wha), color = White)
            }
            Spacer(modifier = Modifier.padding(10.dp))

            Text(
                text = stringResource(R.string.description) + ":",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp, 0.dp)
            )
            Text(
                text = post.value.description,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(10.dp)
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = stringResource(R.string.meeting) + ":",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp, 0.dp)
            )
            Text(
                text = post.value.meeting,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(10.dp)
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = stringResource(R.string.itinerary) + ":",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp, 0.dp)
            )
            Text(
                text = makeBulletedList(items = (post.value.itinerary.map { it.time + ": " + it.event })),
                modifier = Modifier.padding(10.dp)
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = stringResource(R.string.includes) + ":",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp, 0.dp)
            )
            Text(
                text = makeBulletedList(items = post.value.includes),
                modifier = Modifier.padding(10.dp)
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = stringResource(R.string.destination) + ":",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp)
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
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
                        state = MarkerState(
                            position = LatLng(
                                post.value.position.lat,
                                post.value.position.long
                            )
                        ),
                        title = post.value.title
                    )
                }
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