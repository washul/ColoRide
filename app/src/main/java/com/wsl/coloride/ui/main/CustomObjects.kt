package com.wsl.coloride.ui.main

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

@Composable
fun AutoResizedText(
    text: String,
    style: TextStyle = MaterialTheme.typography.body2,
    modifier: Modifier
) {
    var resizedTextStyle by remember {
        mutableStateOf(style)
    }

    var shouldDrawContent by remember {
        mutableStateOf(false)
    }

    Text(
        text = text,
        textAlign = TextAlign.Center,
        modifier = modifier.drawWithContent {
            if (shouldDrawContent) {
                drawContent()
            }
        },
        softWrap = false,
        style = resizedTextStyle,
        onTextLayout = { text ->
            if (text.didOverflowWidth) {
                resizedTextStyle = resizedTextStyle.copy(
                    fontSize = resizedTextStyle.fontSize * 0.95
                )
            } else {
                shouldDrawContent = true
            }
        }
    )
}