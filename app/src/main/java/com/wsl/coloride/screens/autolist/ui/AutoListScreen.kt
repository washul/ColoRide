package com.wsl.coloride.screens.autolist.ui

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.wsl.coloride.R
import com.wsl.coloride.screens.autolist.AutoListViewModel
import com.wsl.coloride.screens.autolist.ui.AutoListNavRoute.AUTO_ID_BACK_ENTRY
import com.wsl.coloride.screens.create_route.CreateRouteEvent
import com.wsl.coloride.screens.loadauto.LoadAutoViewModel
import com.wsl.coloride.screens.loadauto.ui.LoadAutoNavRoute
import com.wsl.coloride.ui.main.MainStateView
import com.wsl.coloride.util.CommonScreenEvent
import com.wsl.domain.model.Auto
import com.wsl.domain.model.User
import com.wsl.domain.model.UserRating
import com.wsl.domain.model.UserType
import com.wsl.utils.NavigationRoute
import org.koin.androidx.compose.koinViewModel

object AutoListNavRoute : NavigationRoute {
    const val AUTO_ID_BACK_ENTRY = "AUTO_ID"
    const val USER_PARAM = "user_ID"
    override val route: String = "auto_list_screen/{$USER_PARAM}"
    fun createRoute(user: String): String {
        return route.replace("{$USER_PARAM}", user)
    }
}

@Composable
fun AutoListScreen(
    user: String?,
    navController: NavHostController,
    viewModel: AutoListViewModel = koinViewModel()
) {

    viewModel.user = User(
        "123",
        "WSL",
        "wsl@wsl.com",
        "1234567890",
        Uri.EMPTY,
        UserRating(3f, 3f, 3f),
        UserType.OWNER
    )

    EventsObserver(viewModel = viewModel, navController = navController)

    val autoList: List<Auto> by viewModel.autoList.collectAsState()
    AutoListScreenView(
        autoList = autoList,
        onCloseIconClick = { navController.popBackStack() },
        onAddAutoClick = {
            navController.navigate(LoadAutoNavRoute.createRoute(viewModel.user.UUID))
        },
        onAutoSelected = {
            viewModel.setAutoAsSelected(it)
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoListScreenView(
    autoList: List<Auto>,
    onCloseIconClick: () -> Unit,
    onAddAutoClick: () -> Unit,
    onAutoSelected: (Auto) -> Unit
) {
    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(text = "Yours cars")
                },
                modifier = Modifier.fillMaxWidth(),
                navigationIcon = {
                    IconButton(onClick = { onCloseIconClick() }) {
                        Icon(
                            tint = MaterialTheme.colorScheme.primary,
                            painter = painterResource(id = R.drawable.close_icon),
                            contentDescription = "Close window"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { onAddAutoClick() }) {
                        Icon(
                            tint = MaterialTheme.colorScheme.inversePrimary,
                            painter = painterResource(id = R.drawable.add_icon),
                            contentDescription = "add car"
                        )
                    }
                }
            )
        },
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize()
    ) { paddingValues ->
        val isEmptyList = autoList.isEmpty()
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(autoList) {
                AutoListCardView(auto = it) { autoSelected ->
                    onAutoSelected(autoSelected)
                }
            }
        }
    }
}

@Composable
fun EventsObserver(viewModel: AutoListViewModel, navController: NavHostController) {
    val event: CommonScreenEvent by viewModel.onEvent.collectAsState()

    when (event) {
        is CommonScreenEvent.ErrorMessage -> {
            Toast.makeText(
                LocalContext.current,
                (event as CommonScreenEvent.ErrorMessage).message,
                Toast.LENGTH_LONG
            ).show()
        }
        is CommonScreenEvent.StateViewDialog -> {
            val stateViewDialog = (event as CommonScreenEvent.StateViewDialog)
            val type = stateViewDialog.type

            MainStateView(type = type)
        }
        is CommonScreenEvent.OnCloseRequest -> navController.navigateUp()
        is CommonScreenEvent.Success -> {}
        /**Do Nothing*/
    }
}

@Preview
@Composable
fun AutoListScreenPreview() {
    AutoListScreenView(listOf(Auto(uuid = "", "Honda Civic Black", Uri.EMPTY, 5)), {}, {}, {})
}