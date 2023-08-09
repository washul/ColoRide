package com.wsl.coloride.screens.searchCity.ui

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.wsl.coloride.screens.searchCity.CitySearchModelState
import com.wsl.coloride.screens.searchCity.SearchCityViewModel
import com.wsl.coloride.ui.main.CityItemRow
import com.wsl.coloride.ui.main.CustomSearchBar
import com.wsl.domain.model.City
import com.wsl.domain.model.PlaceOfTheRoute
import com.wsl.domain.utils.toCustomString
import com.wsl.utils.NavigationRoute
import org.koin.androidx.compose.koinViewModel

object SearchCityNavRoute : NavigationRoute {
    const val SEARCH_PLACE_PARAM = "search_city_param"
    override val route: String = "search_city_screen/{$SEARCH_PLACE_PARAM}"
    fun createRoute(place: PlaceOfTheRoute): String {
        return route.replace("{${SEARCH_PLACE_PARAM}}", place.toCustomString())
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchCityScreen(
    place: PlaceOfTheRoute?,
    navController: NavController,
    viewModel: SearchCityViewModel = koinViewModel()
) {

    viewModel.setPlaceOfTheRoute(place ?: return)

    val citySearchModel by viewModel.citySearchModelState.collectAsState(
        initial = CitySearchModelState.Empty
    )

    var items = remember { mutableStateListOf(
        "Colotlan",
        "Guadalajara"
    ) }

    SearchCityView(
        citySearchModel = citySearchModel,
        onDismiss = { navController.popBackStack() },
        onSearchTextChanged = { viewModel.onSearchTextChanged(it) },
        onClearClick = { viewModel.onClearClick() },
        place = place,
        historyItems = items,
        onItemClick = {
            viewModel.onCitySelected(it)
            navController.popBackStack()
        }
    )
}

@Composable
fun SearchCityView(
    citySearchModel: CitySearchModelState,
    onSearchTextChanged: (String) -> Unit = {},
    onDismiss: () -> Unit,
    onClearClick: () -> Unit = {},
    onItemClick: (City) -> Unit,
    historyItems: List<String> = emptyList(),
    place: PlaceOfTheRoute
) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                CustomSearchBarM3(
                    searchText = citySearchModel.searchText,
                    placeholder = if (place is PlaceOfTheRoute.Arrival) "Where we go?" else "Where did we come from?",
                    onSearchTextChanged = onSearchTextChanged,
                    onNavigateBack = { onDismiss() },
                    historyItems = historyItems
                )
                /*CustomSearchBar(
                    searchText = citySearchModel.searchText,
                    placeholderText = if (place == PlaceOfTheRoute.Arrival) "Where we go?" else "Where did we come from?",
                    onSearchTextChanged = { onSearchTextChanged(it) },
                    onClearClick = { onClearClick() },
                    onNavigateBack = { onDismiss() },
                    matchesFound = citySearchModel.cities.isNotEmpty()
                )*/
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .background(Color.White)
                .fillMaxSize()
        ) {
            items(citySearchModel.cities) {
                CityItemRow(city = it) {
                    onItemClick(it)
                }
            }
        }
    }
}

@Preview
@Composable
fun SearchCityScreenPreview() {
    SearchCityView(
        citySearchModel = CitySearchModelState(),
        onSearchTextChanged = {},
        onDismiss = {},
        onClearClick = {},
        onItemClick = {},
        historyItems = remember {
            mutableStateListOf(
                "Colotlan",
                "Guadalajara"
            )
        },
        place = PlaceOfTheRoute.Departure)
}
