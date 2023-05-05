package com.wsl.coloride.screens.create_route.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.wsl.coloride.screens.create_route.CreateRouteEvent
import com.wsl.coloride.screens.create_route.CreateRouteViewModel
import com.wsl.coloride.screens.searchCity.ui.SearchCityNavRoute
import com.wsl.domain.model.PlaceOfTheRoute
import com.wsl.utils.NavigationRoute
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.stateIn
import org.koin.androidx.compose.koinViewModel

object CreateRouteNavRoute : NavigationRoute {
    override val route: String = "create_routes_screen"
}

@Composable
fun CreateRouteScreen(
    viewModel: CreateRouteViewModel = koinViewModel(),
    navController: NavController
) {
    EventsUI(viewModel = viewModel, navController = navController)

    val departure by viewModel.departure.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Creando ruta... desde: ${departure.name}")
    }
}

@Composable
fun Lifecycle.observeAsState(): State<Lifecycle.Event> {
    val state = remember { mutableStateOf(Lifecycle.Event.ON_ANY) }
    DisposableEffect(this) {
        val observer = LifecycleEventObserver { _, event ->
            state.value = event
        }
        this@observeAsState.addObserver(observer)
        onDispose {
            this@observeAsState.removeObserver(observer)
        }
    }
    return state
}

@Composable
fun EventsUI(viewModel: CreateRouteViewModel, navController: NavController) {
    val event by viewModel.onEvent.collectAsState()
    when(event){
        CreateRouteEvent.LookingForDepartureCity -> {
            navController.navigate(SearchCityNavRoute.createRoute(PlaceOfTheRoute.Departure))
            //As compose recreate the ui, it reads again the same event
            //and launch the next screen multiple times
            viewModel.postEvent(CreateRouteEvent.Success)
        }
        CreateRouteEvent.LookingForArrivalCity -> {
            navController.navigate(SearchCityNavRoute.createRoute(PlaceOfTheRoute.Arrival))
            viewModel.postEvent(CreateRouteEvent.Success)
        }
        else -> {}
    }

    val state = LocalLifecycleOwner.current.lifecycle.observeAsState().value
    if (state == Lifecycle.Event.ON_RESUME){
        viewModel.getDepartureFromPreferences()
    }
}

