package com.habibi.mycontact.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import com.habibi.mycontact.R
import com.habibi.mycontact.ui.theme.MyContactTheme
import com.habibi.mycontact.model.Contact

@ExperimentalLifecycleComposeApi
@Composable
fun SettingsDialog(
    onDismiss: () -> Unit,
    onSubmit: (Contact) -> Unit
) {
    var item by remember { mutableStateOf(Contact()) }
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = stringResource(id = R.string.add_contact),
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column {
                FormInput(
                    hint = stringResource(id = R.string.name),
                    currentText = {
                        item = item.copy(name = it)
                    }
                )
                FormInput(
                    hint = stringResource(id = R.string.phone),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    currentText = {
                        item = item.copy(phone = it)
                    }
                )
                FormInput(
                    hint = stringResource(id = R.string.email),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    currentText = {
                        item = item.copy(email = it)
                    }
                )
                FormInput(
                    hint = stringResource(id = R.string.url_photo),
                    currentText = {
                        item = item.copy(photoUrl = it)
                    }
                )
            }
        },
        confirmButton = {
            Button(
                enabled = item.name.isNotEmpty() &&
                        item.phone.isNotEmpty() &&
                        item.email.isNotEmpty() &&
                        item.photoUrl.isNotEmpty(),
                onClick = { onSubmit(item)
                onDismiss() }) {
                Text(
                    text = stringResource(id = R.string.submit),
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                )
            }
        }
    )
}
