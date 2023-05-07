package com.wsl.coloride.screens.create_route.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
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
import com.wsl.coloride.screens.create_route.CreateRouteEvent
import com.wsl.coloride.screens.create_route.CreateRouteViewModel
import com.wsl.coloride.screens.searchCity.ui.SearchCityNavRoute
import com.wsl.coloride.ui.main.AutoResizedText
import com.wsl.coloride.ui.theme.Secundary
import com.wsl.coloride.util.observeAsState
import com.wsl.domain.model.City
import com.wsl.domain.model.PlaceOfTheRoute
import com.wsl.utils.NavigationRoute
import com.wsl.utils.extensions.appendTodayTomorrow
import com.wsl.utils.extensions.show12HoursFormatter
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.LocalTime

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
    val arrival by viewModel.arrival.collectAsState()

    CreateRouteView(
        departureCity = departure,
        arrivalCity = arrival,
        onChangeDepartureCity = { viewModel.postEvent(CreateRouteEvent.LookingForDepartureCity) },
        onChangeArrivalCity = { viewModel.postEvent(CreateRouteEvent.LookingForArrivalCity) },
        onSaveRoute = { viewModel.createRoute() },
        isSaveButtonEnable = false,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
    )


}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun CreateRouteView(
    departureCity: City,
    arrivalCity: City,
    onChangeDepartureCity: () -> Unit,
    onChangeArrivalCity: () -> Unit,
    onSaveRoute: () -> Unit,
    isSaveButtonEnable: Boolean = false,
    modifier: Modifier
) {
    Scaffold(modifier = modifier) {
        Column(modifier = modifier) {

            //Date and Time
            DateAndTime(modifier = Modifier.fillMaxWidth())

            Spacer(Modifier.height(32.dp))

            //Departure and Arrival City
            Text(text = "Departure and Arrival")

            Spacer(Modifier.height(8.dp))

            CityBoxView(
                name = departureCity.name,
                subtext = "From: \t\t",
                onChangeDepartureCity = onChangeDepartureCity,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            CityBoxView(
                name = arrivalCity.name,
                subtext = "To: \t\t",
                onChangeDepartureCity = onChangeArrivalCity,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(32.dp))

            //Description

            var descriptionTextState by remember {
                mutableStateOf("")
            }
            Text(text = "Description")
            Spacer(Modifier.height(8.dp))
            TextField(
                singleLine = false,
                value = descriptionTextState,
                onValueChange = { descriptionTextState = it },
                maxLines = 6,
                placeholder = { Text(text = "Write a description to say something or annunce somthing to yours passengers") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(40.dp))

            RadioButtonsPreferences(Modifier.fillMaxWidth())

            SaveButton(isSaveButtonEnable, onSaveRoute)

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
        Text(text = "Save")
    }
}


@Composable
fun RadioButtonsPreferences(modifier: Modifier) {
    Card(modifier = modifier) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            //Constraints
            val (preferencesTitleText, infoButton, preferenceText, preferenceSwitch) = createRefs()

            Text(
                "Preferences",
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(preferencesTitleText) {
                        start.linkTo(parent.start, margin = 8.dp)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    })

            var preferenceAcceptPassengerState by remember {
                mutableStateOf(true)
            }

            //Dialog info setUp
            val infoDialogState = rememberSheetState()
            InfoDialog(
                state = infoDialogState,
                body = InfoBody.Default(
                    bodyText = "Active this option if you want us to ask you about accept a passenger.",
                ),
                selection = InfoSelection(
                    onPositiveClick = {},
                    onNegativeClick = { infoDialogState.hide() })
            )

            IconButton(
                onClick = { infoDialogState.show() },
                Modifier
                    .size(20.dp)
                    .constrainAs(infoButton) {
                        start.linkTo(parent.start)
                        top.linkTo(preferencesTitleText.top, margin = 8.dp)
                        bottom.linkTo(parent.bottom)
                    }) {
                Icon(
                    painter = painterResource(id = R.drawable.info),
                    contentDescription = "info button",
                    tint = Color.LightGray
                )
            }
            AutoResizedText(
                text = "Will be necessary ask you to accept passenger?",
                modifier = Modifier.constrainAs(preferenceText) {
                    start.linkTo(infoButton.end, margin = 4.dp)
                    top.linkTo(preferencesTitleText.top, margin = 8.dp)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(preferenceSwitch.start, margin = 8.dp)
                }
            )
            Switch(
                checked = preferenceAcceptPassengerState,
                onCheckedChange = {
                    preferenceAcceptPassengerState = !preferenceAcceptPassengerState
                },
                Modifier.constrainAs(preferenceSwitch) {
                    top.linkTo(preferencesTitleText.top, margin = 8.dp)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
            )
        }
    }


}

@Composable
fun DateAndTime(modifier: Modifier) {


    Column(modifier = modifier) {
        Text(text = "Date and Time:")

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            var todayDate by remember { mutableStateOf(LocalDate.now()) }
            var todayTime by remember { mutableStateOf(LocalTime.now()) }

            val calendarState = rememberSheetState()
            val clockState = rememberSheetState()
            ClockDialog(
                state = clockState,
                config = ClockConfig(is24HourFormat = true),
                selection = ClockSelection.HoursMinutes { hours, minutes ->
                    todayTime = LocalTime.now().withHour(hours).withMinute(minutes)
                }
            )
            CalendarDialog(
                state = calendarState,
                selection = CalendarSelection.Date {
                    todayDate = it
                }
            )

            //Date
            OutlinedTextField(
                value = todayDate.appendTodayTomorrow(),
                onValueChange = {},
                readOnly = true,
                maxLines = 1,
                modifier = Modifier.weight(1f),
                trailingIcon = {
                    IconButton(onClick = { calendarState.show() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.calendar_today),
                            contentDescription = "change date"
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.width(4.dp))

            //Time
            OutlinedTextField(
                value = todayTime.show12HoursFormatter(),
                onValueChange = {},
                readOnly = true,
                maxLines = 1,
                modifier = Modifier.weight(1f),
                trailingIcon = {
                    IconButton(onClick = { clockState.show() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.access_time),
                            contentDescription = "change date"
                        )
                    }
                }
            )
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
        elevation = 4.dp,
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
                withStyle(style = SpanStyle(Secundary)) {
                    append(name)
                }
            }
            Text(text = annotateText, modifier = Modifier)
            TextButton(
                onClick = { onChangeDepartureCity() }
            ) {
                Text(text = "change")
            }
        }
    }

}

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
        departureCity = City(name = "Guadalajara, Jalisco, Mexico"),
        arrivalCity = City(name = "Colotlan, Jalisco, Mexico"),
        onChangeDepartureCity = {},
        onChangeArrivalCity = {},
        onSaveRoute = {},
        modifier = Modifier.padding(8.dp).fillMaxSize()
    )
}

