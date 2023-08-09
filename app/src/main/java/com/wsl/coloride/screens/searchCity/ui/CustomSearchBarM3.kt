package com.wsl.coloride.screens.searchCity.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wsl.coloride.screens.searchCity.CitySearchModelState

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CustomSearchBarM3(
    searchText: String = "",
    onSearchTextChanged: (String) -> Unit,
    placeholder: String = "Search",
    onNavigateBack: () -> Unit = {},
    historyItems: List<String> = emptyList(),
    results: @Composable () -> Unit = {}
) {
    var active by remember { mutableStateOf(false) }


    Scaffold {
        SearchBar(
            query = searchText,
            onQueryChange = { onSearchTextChanged(it) },
            onSearch = { active = false },
            active = active,
            modifier = Modifier.fillMaxWidth(),
            onActiveChange = {
                active = it
            },
            placeholder = {
                Text(text = placeholder)
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search icon")
            },
            trailingIcon = {
                if (active) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close icon",
                        modifier = Modifier.clickable {
                            if(searchText.isNotEmpty()){
                                onSearchTextChanged("")
                            } else {
                                active = false
                                onNavigateBack()
                            }
                        })
                }
            }
        ) {
            historyItems.forEach {
                Row(modifier = Modifier.padding(all = 14.dp)) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "History icon")
                    Text(text = it)
                }
            }
        }
    }
}
@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun CustomSearchBarM3Preview() {
    CustomSearchBarM3(onSearchTextChanged = {})
}