package com.coderunners.heytripsv.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coderunners.heytripsv.R
import com.coderunners.heytripsv.model.PostDataModel
import com.coderunners.heytripsv.ui.theme.NavGray
import com.coderunners.heytripsv.ui.theme.TextGray
import com.coderunners.heytripsv.ui.theme.White

@Composable
fun PostCard(post: PostDataModel, onClick: () -> Unit){
    Card(
        modifier = Modifier
            .clickable { onClick() }
            .size(200.dp, 275.dp).padding(5.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f).clip(RoundedCornerShape(12.dp)),
            painter = painterResource(id = post.image),
            contentDescription = "Photo",
            contentScale = ContentScale.Crop
        )
        Text(text = post.title, color = TextGray, fontWeight = FontWeight.Bold, modifier = Modifier.padding(10.dp), fontSize = 15.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(text = (post.date + " - $" + "%.2f".format(post.price)), modifier = Modifier.padding(10.dp, 0.dp), fontSize = 12.sp, color = NavGray)
    }
}

@Preview
@Composable
fun PrevCard(){
    PostCard(post = PostDataModel(id = 1, title = "Volcan de Santa Ana", image = R.drawable.default_image, date = "12/05/2024", price = 12.50f)) {

    }
}