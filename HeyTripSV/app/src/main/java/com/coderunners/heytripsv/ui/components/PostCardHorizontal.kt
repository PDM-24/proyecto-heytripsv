package com.coderunners.heytripsv.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coderunners.heytripsv.model.PostDataModel
import com.coderunners.heytripsv.ui.theme.NavGray
import com.coderunners.heytripsv.ui.theme.TextGray
import com.coderunners.heytripsv.ui.theme.White

@Composable
fun PostCardHorizontal(post: PostDataModel, onClick: () -> Unit, save: Boolean = false, edit: Boolean = false){
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
            Column(
                modifier = Modifier.padding(0.dp, 5.dp, 5.dp, 0.dp)
            ) {
                Text(text = post.title, fontWeight = FontWeight.Bold, modifier = Modifier.padding(10.dp), fontSize = 15.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(text = (post.date + " - $" + "%.2f".format(post.price)), modifier = Modifier.padding(10.dp, 0.dp), fontSize = 12.sp, color = NavGray)
            }

        }

    }
}