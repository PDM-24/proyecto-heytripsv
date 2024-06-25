package com.coderunners.heytripsv.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.coderunners.heytripsv.R
import com.coderunners.heytripsv.model.PostDataModel
import com.coderunners.heytripsv.ui.theme.NavGray
import com.coderunners.heytripsv.ui.theme.TextGray
import com.coderunners.heytripsv.ui.theme.White

@Composable
fun PostCard(post: PostDataModel, onClick: () -> Unit, isAdmin: Boolean = false, onDelete: (String) -> Unit){
    Card(
        modifier = Modifier
            .clickable { onClick() }
            .size(200.dp, 275.dp)
            .padding(5.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        if (!isAdmin){
            AsyncImage(model = post.image, contentDescription = post.title, modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop)
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = post.title, color = TextGray, fontWeight = FontWeight.Bold, modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 0.dp), fontSize = 15.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Text(text = (post.date + " - $" + "%.2f".format(post.price)), modifier = Modifier.padding(10.dp, 0.dp), fontSize = 12.sp, color = NavGray)
                }
            }

        }else{
            //Si es admin muestra el botón de eliminar
            AsyncImage(model = post.image, contentDescription = post.title, modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop)
            Row (modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically){
                Column(modifier = Modifier.fillMaxWidth(0.80f)) {
                    Text(text = post.title, color = TextGray, fontWeight = FontWeight.Bold, modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 0.dp), fontSize = 15.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Text(text = (post.date + " - $" + "%.2f".format(post.price)), modifier = Modifier.padding(10.dp, 0.dp), fontSize = 12.sp, color = NavGray)
                }
                Column(modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(2.dp, 0.dp)) {
                    IconButton(
                        onClick = { onDelete(post.id) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFCC0000), RoundedCornerShape(4.dp))
                            .aspectRatio(1f),
                    ) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Color.White)
                    }
                }
            }
        }

    }
}
