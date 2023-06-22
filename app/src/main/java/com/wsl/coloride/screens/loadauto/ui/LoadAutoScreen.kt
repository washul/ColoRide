package com.wsl.coloride.screens.loadauto.ui

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.ImageButton
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.wsl.coloride.R
import com.wsl.coloride.screens.loadauto.LoadAutoViewModel
import com.wsl.domain.model.User
import com.wsl.domain.model.UserRating
import com.wsl.domain.model.UserType
import com.wsl.utils.NavigationRoute
import org.koin.androidx.compose.koinViewModel

object LoadAutoNavRoute : NavigationRoute {
    const val USER_PARAM = "user_ID"
    override val route: String = "load_auto_screen/{$USER_PARAM}"
    fun createRoute(user: String): String {
        return route.replace("{$USER_PARAM}", user)
    }
}


@Composable
fun LoadAutoScreen(
    user: String?,
    navController: NavHostController,
    viewModel: LoadAutoViewModel = koinViewModel()
) {
    if (user == null) return
    LoadAutoView(
        onBackPressed = {navController.popBackStack()}
    )
}

@Composable
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
fun LoadAutoView(onBackPressed: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(modifier = Modifier.fillMaxWidth()) {
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    IconButton(onClick = {onBackPressed()}) {
                        Icon(
                            painter = painterResource(id = R.drawable.go_back_icon),
                            contentDescription = "go back"
                        )
                    }
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Text(text = "Creating auto for user...", style = MaterialTheme.typography.h6)
        }
    }
}

@Preview
@Composable
private fun LoadAutoScreenPreview() {
    LoadAutoView(onBackPressed = {})
}