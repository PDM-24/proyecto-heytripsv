package com.coderunners.heytripsv.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.coderunners.heytripsv.R
import com.coderunners.heytripsv.ui.theme.MainGreen
import com.coderunners.heytripsv.ui.theme.White

@Composable
fun ReportDialog(radioOptions: List<String>, onDismissRequest: () -> Unit, onConfirm: () -> Unit){
    val selectedOption = remember {
        mutableStateOf(radioOptions[0])
    }
    val other = remember{ mutableStateOf("")}
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(425.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Text(text = stringResource(id = R.string.report), fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.fillMaxWidth().padding(10.dp), textAlign = TextAlign.Center)
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
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                OutlinedTextField(
                    value = other.value,
                    enabled = selectedOption.value == stringResource(id = R.string.report_other),
                    onValueChange = {
                        other.value = it
                    },
                    placeholder = { Text("") },
                )
            }

            Spacer(modifier = Modifier.padding(10.dp))
            Row {
                Button(
                    onClick =
                    {
                        onDismissRequest()
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White

                    ),
                    border = BorderStroke(1.dp, color = MainGreen),
                    shape = RoundedCornerShape(7.dp)
                )
                {
                    Text(text = stringResource(id = R.string.cancel))
                }
                Button(
                    onClick = {
                        onConfirm()
                        onDismissRequest()
                    },
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MainGreen

                    ),
                    shape = RoundedCornerShape(7.dp)
                )
                {
                    Text(text = stringResource(id = R.string.confirm), color = White)
                }
            }
        }
    }
}
