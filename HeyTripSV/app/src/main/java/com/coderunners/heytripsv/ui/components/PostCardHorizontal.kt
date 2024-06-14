package com.coderunners.heytripsv.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.coderunners.heytripsv.R
import com.coderunners.heytripsv.model.PostDataModel
import com.coderunners.heytripsv.ui.navigation.ScreenRoute
import com.coderunners.heytripsv.ui.theme.MainGreen
import com.coderunners.heytripsv.ui.theme.NavGray
import com.coderunners.heytripsv.ui.theme.TextGray
import com.coderunners.heytripsv.ui.theme.White

@Composable
fun PostCardHorizontal(post: PostDataModel, onClick: () -> Unit, save: Boolean = false, edit: Boolean = false){

    val notificationEnabled = remember {
        mutableStateOf(false)
    }

    val dialogNotificationOpen = remember {
        mutableStateOf(false)
    }

    val radioOptions = listOf(
        stringResource(R.string.one_day_before), stringResource(R.string.two_day_before), stringResource(
            R.string.three_day_before)
    )
    val selectedOption = remember {
        mutableStateOf(radioOptions[0])
    }

    when(dialogNotificationOpen.value){
        true -> {
            Dialog(onDismissRequest = { dialogNotificationOpen.value = false}) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(10.dp),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    radioOptions.forEach { text ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = (text == selectedOption.value),
                                    onClick = {
                                        selectedOption.value = text
                                    }
                                )
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (text == selectedOption.value),
                                onClick = { selectedOption.value = text }
                            )
                            Text(
                                text = text,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    Row {
                        Button(
                            onClick =
                            {
                                dialogNotificationOpen.value = false
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .padding(horizontal = 16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White

                            ),
                            border = BorderStroke(1.dp, color = MainGreen),
                            shape = RoundedCornerShape(7.dp))
                        {
                            Text(text = stringResource(id = R.string.cancel))
                        }
                        Button(
                            onClick = {
                                //TODO: Crear la notificación
                                notificationEnabled.value = !notificationEnabled.value
                                dialogNotificationOpen.value = false
                                         },
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .padding(horizontal = 16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MainGreen

                            ),
                            shape = RoundedCornerShape(7.dp))
                        {
                            Text(text = stringResource(id = R.string.confirm), color = White)
                        }
                    }
                }
            }
        }
        false -> dialogNotificationOpen.value = false
    }

    Card(
        modifier = Modifier
            .clickable { onClick() }
            .height(200.dp)
            .fillMaxWidth()
            .padding(5.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp)),
                painter = painterResource(id = post.image),
                contentDescription = "Photo",
                contentScale = ContentScale.Crop
            )
            if (save){
                Column(
                    modifier = Modifier
                        .padding(0.dp, 5.dp, 5.dp, 5.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.End
                ) {
                    Box(modifier = Modifier.fillMaxWidth()){
                        Column {
                            Text(text = post.title, fontWeight = FontWeight.Bold, modifier = Modifier.padding(10.dp), fontSize = 15.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            Text(text = (post.date + " - $" + "%.2f".format(post.price)), modifier = Modifier.padding(10.dp, 0.dp), fontSize = 12.sp, color = NavGray)
                        }
                    }
                    Icon(imageVector = if (notificationEnabled.value) Icons.Default.Notifications else Icons.Outlined.Notifications, contentDescription = "Notification", modifier = Modifier.clickable {
                        //TODO: Manejar las notificaciones
                        dialogNotificationOpen.value = true
                    })
                }
            } else {
                Column(
                    modifier = Modifier.padding(0.dp, 5.dp, 5.dp, 0.dp)
                ) {
                    Text(text = post.title, fontWeight = FontWeight.Bold, modifier = Modifier.padding(10.dp), fontSize = 15.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Text(text = (post.date + " - $" + "%.2f".format(post.price)), modifier = Modifier.padding(10.dp, 0.dp), fontSize = 12.sp, color = NavGray)
                }
            }

        }

    }
}