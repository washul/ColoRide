package com.wsl.coloride.screens.routes.ui

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.wsl.coloride.screens.detail.ui.DetailNavRoute
import com.wsl.coloride.screens.detail.ui.RouteCard
import com.wsl.coloride.screens.routes.RoutesViewModel
import com.wsl.coloride.ui.theme.Primary
import com.wsl.coloride.ui.theme.Secundary
import com.wsl.domain.model.Route
import com.wsl.domain.model.UserType
import com.wsl.utils.NavigationRoute
import com.wsl.utils.extentions.showAsTitle
import org.koin.androidx.compose.koinViewModel

object RouteNavRoute : NavigationRoute {
    override val route: String = "routes_screen"
}

//TODO hacer la logica de marcar en favoritos y enviar por whatsapp
//login
//register
//sistema de calificacion
//sistema de aceptacion de pasagero
//crear ruta



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RoutesScreen(
    viewModel: RoutesViewModel = koinViewModel(),
    navController: NavController
) {
    val routeGroupsList by viewModel.routesList.observeAsState()
    LazyColumn(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        routeGroupsList?.forEach { (dayOfMonth, routeList) ->

            stickyHeader {
                Text(
                    text = routeList.first().date.showAsTitle(),
                    textAlign = TextAlign.Center,
                    fontSize = 35.sp,
                    color = Color.White,
                    modifier = Modifier
                        .background(
                            Primary
                        )
                        .padding(5.dp)
                        .fillMaxWidth()
                )
            }

            items(routeList) {
                RouteCard(it) { routeSelected ->
                    Log.e("Support", "ItemSelected: ${routeSelected.description}")
                    navController.navigate(DetailNavRoute.createRoute(routeSelected.uuid))
                }
            }

        }

    }
}


