package com.wsl.coloride

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.wsl.coloride.screens.autolist.ui.AutoListNavRoute
import com.wsl.coloride.screens.autolist.ui.AutoListScreen
import com.wsl.coloride.screens.create_route.ui.CreateRouteNavRoute
import com.wsl.coloride.screens.create_route.ui.CreateRouteScreen
import com.wsl.coloride.screens.detail.ui.DetailNavRoute
import com.wsl.coloride.screens.detail.ui.DetailScreen
import com.wsl.coloride.screens.loadauto.ui.LoadAutoNavRoute
import com.wsl.coloride.screens.loadauto.ui.LoadAutoScreen
import com.wsl.coloride.screens.routes.ui.RouteNavRoute
import com.wsl.coloride.screens.routes.ui.RoutesScreen
import com.wsl.coloride.screens.searchCity.ui.SearchCityNavRoute
import com.wsl.coloride.screens.searchCity.ui.SearchCityScreen
import com.wsl.coloride.ui.main.MainScaffold
import com.wsl.coloride.ui.theme.ColoRideTheme
import com.wsl.domain.utils.toPlaceOfTheRoute

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val systemUIController = rememberSystemUiController()
            systemUIController.setStatusBarColor(color = MaterialTheme.colorScheme.tertiaryContainer)


            ColoRideTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    MainScaffold(
                        modifier = Modifier
                            .fillMaxSize()
                            .systemBarsPadding()
                    ) {

                        val navController: NavHostController = rememberNavController()
                        NavHost(
                            navController = navController,
                            startDestination = RouteNavRoute.route
                        ) {

                            composable(route = RouteNavRoute.route) {
                                RoutesScreen(navController = navController)
                            }

                            composable(CreateRouteNavRoute.route) {
                                CreateRouteScreen(navController = navController)
                            }

                            composable(
                                SearchCityNavRoute.route,
                                arguments = listOf(navArgument(SearchCityNavRoute.SEARCH_PLACE_PARAM) {
                                    type = NavType.StringType
                                })
                            ) { backStack ->
                                SearchCityScreen(
                                    place = backStack.arguments?.getString(
                                        SearchCityNavRoute.SEARCH_PLACE_PARAM
                                    ).toPlaceOfTheRoute(),
                                    navController = navController
                                )
                            }

                            composable(
                                AutoListNavRoute.route,
                                arguments = listOf(navArgument(AutoListNavRoute.USER_PARAM) {
                                    type = NavType.StringType
                                })
                            ) { backStack ->
                                AutoListScreen(
                                    user = backStack.arguments?.getString(
                                        AutoListNavRoute.USER_PARAM
                                    ), navController = navController
                                )
                            }

                            composable(
                                LoadAutoNavRoute.route,
                                arguments = listOf(navArgument(LoadAutoNavRoute.USER_PARAM) {
                                    type = NavType.StringType
                                })
                            ) { backStack ->
                                LoadAutoScreen(
                                    user = backStack.arguments?.getString(
                                        LoadAutoNavRoute.USER_PARAM
                                    ), navController = navController
                                )
                            }

                            composable(
                                DetailNavRoute.route,
                                arguments = listOf(navArgument(DetailNavRoute.ROUTE_ID_PARAM) {
                                    type = NavType.StringType
                                })
                            ) { backStack ->
                                DetailScreen(routeId = backStack.arguments?.getString(DetailNavRoute.ROUTE_ID_PARAM))
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ColoRideTheme {

    }
}