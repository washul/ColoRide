package com.wsl.coloride.screens.autolist.ui

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wsl.coloride.R
import com.wsl.domain.model.Auto


@Composable
fun AutoListCardView(auto: Auto, onClick: (Auto) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick(auto) }) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {

            if (auto.isSelected)
                Icon(
                    painter = painterResource(id = R.drawable.check_circle),
                    contentDescription = "Check image",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(end = 8.dp)
                )

            Text(
                textAlign = TextAlign.Center,
                text = auto.showAutoTitle(),
                style = MaterialTheme.typography.titleSmall
                )

        }

    }
}

@Preview
@Composable
fun AutoListCardPreview() {
    AutoListCardView(auto = Auto(uuid = "", "Honda Civic Black", Uri.EMPTY, 5, isSelected = true), {})
}