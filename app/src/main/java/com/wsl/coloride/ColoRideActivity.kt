package com.wsl.coloride

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.wsl.coloride.screens.detail.ui.DetailNavRoute
import com.wsl.coloride.screens.detail.ui.DetailScreen
import com.wsl.coloride.screens.routes.ui.RouteNavRoute
import com.wsl.coloride.screens.routes.ui.RoutesScreen
import com.wsl.coloride.ui.theme.ColoRideTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColoRideTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = RouteNavRoute.route) {
                        composable(RouteNavRoute.route) { RoutesScreen(navController = navController) }
                        composable(
                            DetailNavRoute.route,
                            arguments = listOf(navArgument(DetailNavRoute.ROUTE_ID_PARAM) { type = NavType.StringType })
                        ) { backStack ->
                            DetailScreen(routeId = backStack.arguments?.getString(DetailNavRoute.ROUTE_ID_PARAM))
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