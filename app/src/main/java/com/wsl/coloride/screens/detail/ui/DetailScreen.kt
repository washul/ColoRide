package com.wsl.coloride.screens.detail.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.wsl.coloride.screens.detail.DetailViewModel
import com.wsl.utils.NavigationRoute
import org.koin.androidx.compose.koinViewModel

object DetailNavRoute : NavigationRoute {
    const val ROUTE_ID_PARAM = "route_id"
    override val route: String = "detail_screen/{$ROUTE_ID_PARAM}"
    fun createRoute(routeID: String): String {
        return route.replace("{$ROUTE_ID_PARAM}", routeID)
    }
}

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = koinViewModel(),
    routeId: String?
) {
    if (routeId == null) {
        return
    }

    viewModel.routeId = routeId
    Text(text = "Esto es el detalle con el routeID: $routeId")
}