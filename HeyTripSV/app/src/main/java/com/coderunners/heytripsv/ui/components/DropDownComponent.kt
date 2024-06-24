package com.coderunners.heytripsv.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDown(
    options: List<String>,
    onValueChange : (String)-> Unit,
    initialValue: String
) {

    var selectedText by remember { mutableStateOf(initialValue) }
    var isExpanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = isExpanded ,
        onExpandedChange = { isExpanded= !isExpanded},
        modifier = Modifier
            .padding(horizontal = 16.dp),

    ){

        TextField(
            modifier = Modifier.menuAnchor(),
            value = selectedText,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) }
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ){
            options.forEachIndexed{index, text ->
                DropdownMenuItem(
                    text = { Text(text = text)},
                    onClick = {
                        selectedText =options[index]
                        onValueChange(selectedText)
                        isExpanded = false
                    }
                )
            }
        }

    }
}