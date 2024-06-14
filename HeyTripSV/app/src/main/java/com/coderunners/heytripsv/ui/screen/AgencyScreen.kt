package com.coderunners.heytripsv.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coderunners.heytripsv.MainViewModel
import com.coderunners.heytripsv.R
import com.coderunners.heytripsv.ui.components.PostCardHorizontal
import com.coderunners.heytripsv.ui.theme.NavGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgencyScreen(mainViewModel: MainViewModel, innerPadding: PaddingValues, onClick: () -> Unit){
    val agency = mainViewModel.selectedAgency.collectAsState()
    var expanded by remember {
        mutableStateOf(false)
    }
    var filtros = arrayOf(stringResource(id = R.string.closest), stringResource(id = R.string.recent))
    var selectedText by remember { mutableStateOf(filtros[0]) }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(innerPadding),

    ) {
        item {
            Row (
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            ){
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()) {
                    Image(painter = painterResource(id = agency.value.image), contentDescription = "Photo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.clip(CircleShape))
                    Text(text = agency.value.name, fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(10.dp))
                }
            }
            Text(text = agency.value.desc)
            Spacer(modifier = Modifier.height(10.dp))
            Box(modifier = Modifier
                .wrapContentSize()
                .clip(CircleShape)
                .background(NavGray)){
                Row (modifier = Modifier.padding(2.dp), verticalAlignment = Alignment.CenterVertically){
                    Icon(imageVector = Icons.Default.Call, contentDescription = "Phone")
                    Text(text = agency.value.number)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Box(modifier = Modifier
                .wrapContentSize()
                .clip(CircleShape)
                .background(NavGray)){
                Row (modifier = Modifier.padding(2.dp), verticalAlignment = Alignment.CenterVertically){
                    Icon(painter = painterResource(id = R.drawable.instagram), contentDescription = "Instagram")
                    Text(text = agency.value.instagram, modifier = Modifier.padding(start = 2.dp))
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Box(modifier = Modifier
                .wrapContentSize()
                .clip(CircleShape)
                .background(NavGray)){
                Row (modifier = Modifier.padding(2.dp), verticalAlignment = Alignment.CenterVertically){
                    Icon(painter = painterResource(id = R.drawable.facebook), contentDescription = "Facebook")
                    Text(text = agency.value.facebook, modifier = Modifier.padding(start = 2.dp))
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(thickness = 2.dp)
            Row(modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 20.dp)) {
                Text(text = stringResource(id = R.string.sort_by) + ":",
                    modifier = Modifier.wrapContentHeight().padding(10.dp))
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    }
                ) {
                    TextField(
                        value = selectedText,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        filtros.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(text = item) },
                                onClick = {
                                    selectedText = item
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        }
        items(agency.value.postList){
            PostCardHorizontal(post = it, onClick = {
                mainViewModel.saveSelectedPost(it)
                onClick() })
        }


    }
}