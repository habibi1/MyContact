package com.habibi.mycontact

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.habibi.mycontact.ui.components.FormInput

@Composable
fun DetailProfile(
    id: String,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val item = viewModel.getDetailContact(id)
    Column(modifier = modifier) {
        AsyncImage(
            placeholder = ColorPainter(Color.Gray),
            model = item?.photoUrl,
            error = rememberVectorPainter(image = Icons.Rounded.Person),
            contentDescription = stringResource(id = R.string.photo_profile),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
                .size(100.dp)
                .clip(CircleShape)
        )
        FormInput(
            hint = stringResource(id = R.string.name),
            value = item?.name.orEmpty(),
            readOnly = true,
            currentText = {}
        )
        FormInput(
            hint = stringResource(id = R.string.phone),
            value = item?.phone.orEmpty(),
            readOnly = true,
            currentText = {}
        )
        FormInput(
            hint = stringResource(id = R.string.email),
            value = item?.email.orEmpty(),
            readOnly = true,
            currentText = {}
        )
    }
}