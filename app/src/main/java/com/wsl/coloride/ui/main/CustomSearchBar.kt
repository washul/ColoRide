package com.wsl.coloride.ui.main

/**Credits to MK from https://www.devbitsandbytes.com/configuring-searchview-in-jetpack-compose*/

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wsl.domain.model.City

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun CustomSearchBar(
    searchText: String,
    placeholderText: String = "",
    onSearchTextChanged: (String) -> Unit = {},
    onClearClick: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
    matchesFound: Boolean,
    results: @Composable () -> Unit = {}
) {

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            CustomSearchBarView(
                searchText,
                placeholderText,
                onSearchTextChanged,
                onClearClick,
                onNavigateBack
            )

            if (matchesFound) {
                Text("Results", modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Bold)
                results()
            } else {
                if (searchText.isNotEmpty()) {
                    NoSearchResults()
                }

            }
        }

    }
}

@Composable
fun CityItemListView(cities: List<City>?, onClick: (City) -> Unit) {
    cities?.forEach { city ->
        CityItemRow(city = city) {
            onClick(city)
        }
        Divider()
    }
}


@Composable
fun CityItemRow(city: City, onClick: () -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable { onClick() }) {
        Text(city.name, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(2.dp))
        Text(city.name)
        Spacer(modifier = Modifier.height(4.dp))
    }
}


@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun CustomSearchBarView(
    searchText: String,
    placeholderText: String = "",
    onSearchTextChanged: (String) -> Unit = {},
    onClearClick: () -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    var showClearButton by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }



    TopAppBar(title = { Text("") }, navigationIcon = {
        IconButton(onClick = { onNavigateBack() }) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                modifier = Modifier,
                contentDescription = "search"
            )
        }
    }, actions = {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp)
                .onFocusChanged { focusState ->
                    showClearButton = (focusState.isFocused)
                }
                .focusRequester(focusRequester),
            value = searchText,
            onValueChange = onSearchTextChanged,
            placeholder = {
                Text(text = placeholderText)
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent,
                cursorColor = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
            ),
            trailingIcon = {
                AnimatedVisibility(
                    visible = showClearButton,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    IconButton(onClick = { onClearClick() }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close"
                        )
                    }

                }
            },
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
            }),
        )


    })


    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Composable
fun NoSearchResults() {

    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        Text("No matches found")
    }
}


@OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
@Preview
@Composable
fun CustomSearchBarPreview() {
    CustomSearchBarView("City",
        "Chose city", {}, {}, {})
}