package com.wsl.coloride.screens.create_route.ui

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.maxkeppeker.sheets.core.models.base.SheetState
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import com.maxkeppeler.sheets.info.InfoDialog
import com.maxkeppeler.sheets.info.models.InfoBody
import com.maxkeppeler.sheets.info.models.InfoSelection
import com.wsl.coloride.R
import com.wsl.coloride.screens.autolist.ui.AutoListNavRoute
import com.wsl.coloride.screens.create_route.CreateRouteEvent
import com.wsl.coloride.screens.create_route.CreateRouteViewModel
import com.wsl.coloride.screens.loadauto.ui.LoadAutoNavRoute
import com.wsl.coloride.screens.searchCity.ui.SearchCityNavRoute
import com.wsl.coloride.ui.main.AutoResizedText
import com.wsl.coloride.util.observeAsState
import com.wsl.domain.model.Auto
import com.wsl.domain.model.City
import com.wsl.domain.model.PlaceOfTheRoute
import com.wsl.utils.NavigationRoute
import com.wsl.utils.extensions.appendTodayTomorrow
import com.wsl.utils.extensions.show12HoursFormatter
import com.wsl.utils.extensions.showAsTitle
import org.koin.androidx.compose.koinViewModel
import org.koin.dsl.module
import java.time.LocalDate
import java.time.LocalTime

object CreateRouteNavRoute : NavigationRoute {
    override val route: String = "create_routes_screen"
}

@Composable
fun CreateRouteScreen(
    viewModel: CreateRouteViewModel = koinViewModel(), navController: NavController
) {
    EventsUI(viewModel = viewModel, navController = navController)


    val isSaveButtonAvailable by viewModel.isSaveButtonAvailable.collectAsState()
    CreateRouteView(
        viewModel = viewModel,
        onSaveRoute = { viewModel.createRoute() },
        isSaveButtonEnable = isSaveButtonAvailable,
        modifier = Modifier.fillMaxSize()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateRouteView(
    viewModel: CreateRouteViewModel,
    onSaveRoute: () -> Unit,
    isSaveButtonEnable: Boolean = false,
    modifier: Modifier
) {

    val arrivalCity by viewModel.arrival.collectAsState()
    val departureCity by viewModel.departure.collectAsState()
    val auto by viewModel.auto.collectAsState()
    val todayDate by viewModel.date.collectAsState()
    val todayTime by viewModel.time.collectAsState()
    val descriptionTextState by viewModel.description.collectAsState()
    val preferenceAcceptPassengerState by viewModel.doesOwnerNeedsApprove.collectAsState()

    viewModel.setCreateButtonEnable(
        enableCreateButton(
            arrival = arrivalCity.name,
            departure = departureCity.name,
            auto = auto.name
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Creating route",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onTertiary,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.tertiary)
                        .padding(5.dp)
                        .fillMaxWidth()
                )
            }
        }, modifier = modifier.systemBarsPadding()
    ) {
        Column(
            modifier = modifier
                .padding(it)
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            val spacerSize = 4.dp

            //Date and Time
            DateAndTime(
                todayDate = todayDate,
                todayTime = todayTime,
                viewModel = viewModel,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(spacerSize))

            //Departure and Arrival cities
            DepartureArrivalCities(
                departureCity = departureCity,
                arrivalCity = arrivalCity,
                viewModel = viewModel,
                modifier = Modifier
            )

            Spacer(Modifier.height(spacerSize))

            //Description
            Description(
                descriptionTextState = descriptionTextState,
                viewModel = viewModel,
                modifier = Modifier
            )

            Spacer(Modifier.height(spacerSize))

            //Preferences
            RadioButtonsPreferences(
                preferenceAcceptPassengerState = preferenceAcceptPassengerState,
                viewModel = viewModel,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(spacerSize))

            //Auto
            Auto(auto = auto, viewModel = viewModel)

            Spacer(Modifier.height(spacerSize))

            //Save button
            SaveButton(
                isSaveButtonEnable = isSaveButtonEnable,
                onSaveRoute = onSaveRoute
            )

        }
    }
}

@Composable
fun Auto(auto: Auto, viewModel: CreateRouteViewModel) {


    Card {
        Column(
            modifier = Modifier
                .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Car: ",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(4.dp)
            )

            Card(
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.onSecondary
                ),
                shape = MaterialTheme.shapes.small
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = auto.showAutoTitle())
                    TextButton(onClick = { viewModel.postEvent(CreateRouteEvent.LoadAuto) }) {
                        Text(text = "change", style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Description(descriptionTextState: String, viewModel: CreateRouteViewModel, modifier: Modifier) {
    Card {
        Column(modifier = modifier.padding(8.dp)) {


            Text(text = "Description: ", style = MaterialTheme.typography.headlineSmall)

            Spacer(Modifier.height(8.dp))

            TextField(
                singleLine = false,
                value = descriptionTextState,
                onValueChange = { viewModel.setDescription(it) },
                maxLines = 6,
                placeholder = {
                    Text(
                        text = "Write a description to say something or annunce somthing to yours passengers",
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun DepartureArrivalCities(
    departureCity: City,
    arrivalCity: City,
    viewModel: CreateRouteViewModel, modifier: Modifier
) {


    Card {
        Column(modifier = modifier.padding(8.dp)) {
            //Departure and Arrival City
            Text(text = "Departure and Arrival:", style = MaterialTheme.typography.headlineSmall)

            Spacer(modifier = Modifier.height(8.dp))

            CityBoxView(
                name = departureCity.name,
                subtext = "From: \t\t",
                onChangeDepartureCity = { viewModel.postEvent(CreateRouteEvent.LookingForDepartureCity) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            CityBoxView(
                name = arrivalCity.name,
                subtext = "To: \t\t",
                onChangeDepartureCity = { viewModel.postEvent(CreateRouteEvent.LookingForArrivalCity) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun SaveButton(isSaveButtonEnable: Boolean = false, onSaveRoute: () -> Unit) {
    Button(
        onClick = { onSaveRoute() },
        enabled = isSaveButtonEnable,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Create")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RadioButtonsPreferences(
    preferenceAcceptPassengerState: Boolean,
    viewModel: CreateRouteViewModel,
    modifier: Modifier
) {
    Card(modifier = modifier) {

        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Preferences: ",
                style = MaterialTheme.typography.headlineSmall
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                //Dialog info setUp
                val infoDialogState = rememberSheetState()
                InfoDialog(
                    state = infoDialogState,
                    body = InfoBody.Default(
                        bodyText = "Active this option if you want us to ask you about accept a passenger.",
                    ),
                    selection = InfoSelection(onPositiveClick = {},
                        onNegativeClick = { infoDialogState.hide() })
                )

                Row {
                    IconButton(
                        onClick = { infoDialogState.show() },
                        Modifier
                            .size(20.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.info),
                            contentDescription = "info button",
                            tint = Color.LightGray
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    AutoResizedText(
                        text = "Ask me to approve passenger",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                    )
                }



                Switch(
                    checked = preferenceAcceptPassengerState,
                    modifier = Modifier,
                    onCheckedChange = {
                        viewModel.doesOwnerNeedsApprove(!preferenceAcceptPassengerState)
                    })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateAndTime(
    todayDate: LocalDate,
    todayTime: LocalTime,
    viewModel: CreateRouteViewModel,
    modifier: Modifier
) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(8.dp)) {

            Text(text = "Date and Time:", style = MaterialTheme.typography.headlineSmall)

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
            ) {

                val calendarState = rememberSheetState()
                val clockState = rememberSheetState()
                ClockDialog(state = clockState,
                    config = ClockConfig(is24HourFormat = true),
                    selection = ClockSelection.HoursMinutes { hours, minutes ->
                        viewModel.setTime(LocalTime.now().withHour(hours).withMinute(minutes))
                    })
                CalendarDialog(state = calendarState, selection = CalendarSelection.Date {
                    viewModel.setDate(it)
                })

                //Date
                OutlinedTextField(value = todayDate.appendTodayTomorrow(),
                    textStyle = MaterialTheme.typography.titleLarge,
                    onValueChange = {},
                    readOnly = true,
                    maxLines = 1,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.weight(1f),
                    trailingIcon = {
                        IconButton(onClick = { calendarState.show() }) {
                            Icon(
                                tint = MaterialTheme.colorScheme.primary,
                                painter = painterResource(id = R.drawable.calendar_today),
                                contentDescription = "change date"
                            )
                        }
                    })

                Spacer(modifier = Modifier.width(4.dp))

                //Time
                OutlinedTextField(value = todayTime.show12HoursFormatter(),
                    textStyle = MaterialTheme.typography.titleLarge,
                    onValueChange = {},
                    readOnly = true,
                    maxLines = 1,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.weight(1f),
                    trailingIcon = {
                        IconButton(onClick = { clockState.show() }) {
                            Icon(
                                tint = MaterialTheme.colorScheme.primary,
                                painter = painterResource(id = R.drawable.access_time),
                                contentDescription = "change date"
                            )
                        }
                    })
            }
        }
    }
}

@Composable
fun CityBoxView(
    name: String,
    subtext: String = "From: \t\t",
    onChangeDepartureCity: () -> Unit,
    modifier: Modifier
) {
    Card(
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSecondary),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)

        ) {
            val annotateText = buildAnnotatedString {
                append(subtext)
                withStyle(style = SpanStyle(MaterialTheme.colorScheme.secondary)) {
                    append(name)
                }
            }
            Text(
                text = annotateText,
                modifier = Modifier,
                style = MaterialTheme.typography.titleLarge
            )
            TextButton(onClick = { onChangeDepartureCity() }) {
                Text(text = "change", style = MaterialTheme.typography.titleMedium)
            }
        }
    }

}

fun enableCreateButton(arrival: String, departure: String, auto: String): Boolean =
    arrival.isNotEmpty() && departure.isNotEmpty() && auto.isNotEmpty()

@Composable
fun EventsUI(viewModel: CreateRouteViewModel, navController: NavController) {
    val event by viewModel.onEvent.collectAsState()
    when (event) {
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

        CreateRouteEvent.LoadAuto -> {
            navController.navigate(AutoListNavRoute.createRoute(viewModel.user.UUID))
            viewModel.postEvent(CreateRouteEvent.Success)
        }

        CreateRouteEvent.NoUserFind -> {
            // we need to show to the user why we're going back
            Log.e("support", navController.backQueue.toString())
            navController.navigateUp()
        }

        else -> {}
    }

    val state = LocalLifecycleOwner.current.lifecycle.observeAsState().value
    if (state == Lifecycle.Event.ON_RESUME) {
        viewModel.getDepartureFromPreferences()
        viewModel.getArrivalFromPreferences()
    }
}

@Preview
@Composable
fun CreateRoutePreview() {
    CreateRouteView(
        viewModel = koinViewModel(),
        onSaveRoute = {},
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
    )
}

