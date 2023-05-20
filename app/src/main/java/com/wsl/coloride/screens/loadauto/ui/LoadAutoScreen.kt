package com.wsl.coloride.screens.loadauto.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wsl.coloride.screens.loadauto.LoadAutoViewModel
import com.wsl.utils.NavigationRoute
import org.koin.androidx.compose.koinViewModel

object LoadAutoNavRoute : NavigationRoute {
    const val USER_PARAM = "user_ID"
    override val route: String = "load_auto_screen/{$USER_PARAM}"
    fun createRoute(user: String): String {
        return route.replace("{$USER_PARAM}", user)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoadAutoScreen(user: String?, viewModel: LoadAutoViewModel = koinViewModel()) {
    if (user == null ) return

    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column {
            Text(text = "Creating auto for user...", style = MaterialTheme.typography.h6)
        }
    }
}