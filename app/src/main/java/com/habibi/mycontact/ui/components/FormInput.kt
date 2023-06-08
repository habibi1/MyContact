package com.habibi.mycontact.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    modifier: Modifier = Modifier,
    hint: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    currentText: (String) -> Unit,
    readOnly: Boolean = false,
    value: String = "",
    leadingIcon: @Composable (() -> Unit)? = null
) {
    var name by remember { mutableStateOf(value) } //state
    OutlinedTextField(
        keyboardOptions = keyboardOptions,
        value = name, //display state
        onValueChange = { newName -> //event
            if (!readOnly) {
                name = newName //update state
                currentText(name)
            }
        },
        label = { Text(hint) },
        modifier = modifier.fillMaxWidth().padding(8.dp),
        singleLine = true,
        readOnly = readOnly,
        leadingIcon = leadingIcon
    )
}