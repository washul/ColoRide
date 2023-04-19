package com.wsl.coloride.ui.main

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.wsl.utils.extentions.showAsTitle
import java.time.LocalDateTime

@Composable
fun MainAppBar(
    titleTextSize: TextUnit = 35.sp,
    topAppBarTitle: String = LocalDateTime.now().showAsTitle()
) {
    TopAppBar(
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = topAppBarTitle,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = titleTextSize
            )
        }
    )
}

@Composable
@Preview
fun MainAppBarPreview() {
    MainAppBar()
}

