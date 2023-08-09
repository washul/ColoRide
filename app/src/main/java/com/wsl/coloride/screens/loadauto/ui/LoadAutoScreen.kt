package com.wsl.coloride.screens.loadauto.ui

import android.annotation.SuppressLint
import android.graphics.Paint.Align
import android.net.Uri
import android.util.Log
import android.widget.ImageButton
import android.widget.NumberPicker
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Surface
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.chargemap.compose.numberpicker.NumberPicker
import com.maxkeppeler.sheets.state.models.ProgressIndicator
import com.maxkeppeler.sheets.state.models.State
import com.wsl.coloride.R
import com.wsl.coloride.screens.loadauto.LoadAutoViewModel
import com.wsl.coloride.ui.main.MainStateView
import com.wsl.coloride.util.CommonScreenEvent
import com.wsl.domain.model.Auto
import com.wsl.domain.model.User
import com.wsl.domain.model.UserRating
import com.wsl.domain.model.UserType
import com.wsl.utils.NavigationRoute
import kotlinx.coroutines.Delay
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.androidx.compose.koinViewModel

object LoadAutoNavRoute : NavigationRoute {
    const val USER_PARAM = "user_ID"
    override val route: String = "load_auto_screen/{$USER_PARAM}"
    fun createRoute(user: String): String {
        return route.replace("{$USER_PARAM}", user)
    }
}


@Composable
fun LoadAutoScreen(
    user: String?,
    navController: NavHostController
) {
    Log.e("support", navController.backQueue.toString())
    if (user == null) navController.popBackStack()
    LoadAutoView(navController = navController)
}

//mostrar un cardview especial para un alista vacia

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
fun LoadAutoView(
    viewModel: LoadAutoViewModel = koinViewModel(),
    navController: NavHostController
) {
    EventsObserver(viewModel = viewModel, navController = navController)
    val localAuto: Auto by viewModel.localAuto.collectAsState()
    var isReadyToSave by rememberSaveable { mutableStateOf(false) }

    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {

            Column {
                //Header
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.close_icon),
                            contentDescription = "go back"
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Creating auto",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge,
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    TextButton(
                        enabled = isReadyToSave,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.primary,
                            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        onClick = { viewModel.saveAuto() }) {
                        Text(
                            text = "Save",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                //body
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = """You shouldn't show any sensitive information about your car. \n 
                            The brand and the model or color can be enough for the passenger to identify it. """.trimMargin(),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    val descriptionValue = rememberSaveable {
                        mutableStateOf("")
                    }
                    OutlinedTextField(
                        value = descriptionValue.value,
                        onValueChange = {
                            descriptionValue.value = it
                            localAuto.name = it
                            isReadyToSave = localAuto.isReadyToSave()
                        },
                        label = { Text(text = "Description") },
                        supportingText = { Text(text = "Example: Honda Civic black") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.align(Alignment.End),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Capacity")

                        Spacer(modifier = Modifier.width(8.dp))

                        val number = rememberSaveable { mutableStateOf(2) }
                        NumberPicker(
                            value = number.value,
                            onValueChange = {
                                number.value = it
                                localAuto.seatNumber = it
                                isReadyToSave = localAuto.isReadyToSave()
                            },
                            range = 2..15
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EventsObserver(viewModel: LoadAutoViewModel, navController: NavHostController) {
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
        is CommonScreenEvent.OnCloseRequest -> {
            navController.popBackStack()
        }
        is CommonScreenEvent.Success -> {}
        /**Do Nothing*/
    }
}
