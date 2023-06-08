package com.habibi.mycontact

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.habibi.mycontact.ui.theme.Typography

@Composable
fun AboutPage(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.img_profile),
            contentDescription = stringResource(id = R.string.photo_profile),
            modifier = Modifier
                .padding(16.dp)
                .size(200.dp)
                .clip(CircleShape)
        )
        Text(text = stringResource(id = R.string.name))
        Text(
            text = stringResource(id = R.string.profile_name),
            style = Typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = stringResource(id = R.string.email))
        Text(
            text = stringResource(id = R.string.profile_email),
            style = Typography.titleLarge
        )
    }
}