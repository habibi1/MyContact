package com.habibi.mycontact.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.habibi.mycontact.R
import com.habibi.mycontact.ui.theme.MyContactTheme
import com.habibi.mycontact.model.Contact
import com.habibi.mycontact.model.ContactsData

@Composable
fun ContactItem(
    item: Contact,
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit,
    onRemoveClicked: (Contact) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable {onItemClick(item.id)}
    ) {
        AsyncImage(
            placeholder = ColorPainter(Color.Gray),
            model = item.photoUrl,
            error = rememberVectorPainter(image =Icons.Rounded.Person),
            contentDescription = stringResource(id = R.string.photo_profile),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .size(60.dp)
                .clip(CircleShape)
        )
        Text(
            text = item.name,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 16.dp)
        )
        IconButton(onClick = { onRemoveClicked(item) }) {
            Icon(
                imageVector = Icons.Filled.Clear,
                contentDescription = stringResource(id = R.string.button_delete),
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContactListItemPreview() {
    MyContactTheme() {
        ContactItem(
            ContactsData.contacts.first(),
            onRemoveClicked = {},
            onItemClick = {}
        )
    }
}
