package com.coderunners.heytripsv.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coderunners.heytripsv.R
import com.coderunners.heytripsv.model.PostList
import com.coderunners.heytripsv.ui.components.PostCard
import com.coderunners.heytripsv.ui.theme.MainGreen
import com.coderunners.heytripsv.ui.theme.TextGray
import com.coderunners.heytripsv.ui.theme.White

@Composable
fun MainScreen( innerPadding: PaddingValues){

    Column (
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ){
        //Categories
        Row(
            modifier = Modifier
                .background(MainGreen)
                .fillMaxWidth()
                .padding(15.dp)
        ){
            Column(

            ) {
                Text(text = stringResource(R.string.categories), color = TextGray, modifier = Modifier.padding(5.dp, 0.dp, 5.dp, 0.dp), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(text = stringResource(R.string.categories_desc), color = White, modifier = Modifier.padding(5.dp))
                Row(
                    modifier = Modifier.fillMaxWidth()
                ){
                    Column (
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(5.dp)
                    ) {
                        Button(onClick = { /*TODO*/ },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = White
                            )) {
                            Text(text = stringResource(R.string.beaches), color = TextGray)
                        }
                        Button(onClick = { /*TODO*/ },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = White
                            )) {
                            Text(text = stringResource(R.string.towns), color = TextGray)
                        }
                        Button(onClick = { /*TODO*/ },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = White
                            )) {
                            Text(text = stringResource(R.string.others), color = TextGray)
                        }
                    }
                    Column (
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .padding(5.dp)
                    ) {
                        Button(onClick = { /*TODO*/ },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = White
                            )) {
                            Text(text = stringResource(R.string.mountains), color = TextGray)
                        }
                        Button(onClick = { /*TODO*/ },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = White
                            )) {
                            Text(text = stringResource(R.string.routes), color = TextGray)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.padding(5.dp))
        //PostCards
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            Column {
                Text(text = stringResource(id = R.string.upcoming), fontSize = 20.sp, fontWeight = FontWeight.Medium, modifier = Modifier.padding(0.dp, 10.dp))
                LazyRow {
                    items(PostList){
                            postItem ->
                        PostCard(post = postItem) {
                            //Mover a vista post
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.padding(5.dp))
        //PostCards
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            Column {
                Text(text = stringResource(id = R.string.recent), fontSize = 20.sp, fontWeight = FontWeight.Medium, modifier = Modifier.padding(0.dp, 10.dp))
                LazyRow {
                    items(PostList){
                            postItem ->
                        PostCard(post = postItem) {
                            //Mover a vista post
                        }
                    }
                }
            }
        }
    }
}
