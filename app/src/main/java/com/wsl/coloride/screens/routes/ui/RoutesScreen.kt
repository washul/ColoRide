package com.wsl.coloride.screens.routes.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.wsl.coloride.R
import com.wsl.coloride.screens.create_route.ui.CreateRouteNavRoute
import com.wsl.coloride.screens.detail.ui.DetailNavRoute
import com.wsl.coloride.screens.routes.RoutesViewModel
import com.wsl.utils.NavigationRoute
import com.wsl.utils.extensions.showAsTitle
import org.koin.androidx.compose.koinViewModel

object RouteNavRoute : NavigationRoute {
    override val route: String = "routes_screen"
}

//TODO hacer la logica de marcar en favoritos y enviar por whatsapp
//login
//register
//sistema de calificacion al crear [check]
//sistema de aceptaci on de pasagero
//crear ruta
// manejar si no se tiene usuario mandar a registrar


@Composable
fun RoutesScreen(
    viewModel: RoutesViewModel = koinViewModel(),
    navController: NavController
) {

    RoutesView(
        viewModel = viewModel,
        onAddRouteClick = { navController.navigate(CreateRouteNavRoute.route) },
        onDetailRoute = { navController.navigate(DetailNavRoute.createRoute(it)) }
    )

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RoutesView(
    viewModel: RoutesViewModel,
    onAddRouteClick: () -> Unit,
    onDetailRoute: (String) -> Unit
) {

    val routeGroupsList by viewModel.routesList.observeAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButtonCreateRoute {
                onAddRouteClick()
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            routeGroupsList?.forEach { (dayOfMonth, routeList) ->

                stickyHeader {
                    Text(text = routeList.first().date.showAsTitle(),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onTertiary,
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier
                            .background(color = MaterialTheme.colorScheme.tertiary)
                            .padding(5.dp)
                            .fillMaxWidth()

                    )
                }

                items(routeList) {
                    RouteCard(it) { routeSelected ->
                        Log.e("Support", "ItemSelected: ${routeSelected.description}")
                        onDetailRoute(routeSelected.uuid)
                    }
                }
            }
        }
    }
}

@Composable
fun FloatingActionButtonCreateRoute(onCreateRouteClick: () -> Unit) {
    ExtendedFloatingActionButton(
        text = { Text(text = "add") },
        onClick = { onCreateRouteClick() },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.add_icon),
                contentDescription = "add route"
            )
        })
}
@Preview
@Composable
fun RoutePreview() {
    RoutesView(viewModel = koinViewModel(), onAddRouteClick = { }, onDetailRoute = {})
}



