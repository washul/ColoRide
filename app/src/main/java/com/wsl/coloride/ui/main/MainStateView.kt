package com.wsl.coloride.ui.main

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.maxkeppeker.sheets.core.models.base.SheetState
import com.maxkeppeler.sheets.state.StateDialog
import com.maxkeppeler.sheets.state.models.ProgressIndicator
import com.maxkeppeler.sheets.state.models.State
import com.maxkeppeler.sheets.state.models.StateConfig
import kotlinx.coroutines.delay

sealed class MainStateViewType {

    object ProgressView: MainStateViewType()

    data class Failure(val labelText: String): MainStateViewType()

    data class Success(val labelText: String): MainStateViewType()

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainStateView(type: MainStateViewType) {
    var visible by remember { mutableStateOf(true) }
    val progress = remember { mutableStateOf(0f) }
    val progressAnimated = animateFloatAsState(targetValue = progress.value, tween(1000)).value
    LaunchedEffect(Unit) {
        progress.value = 1f
        delay(1000)
    }

    LaunchedEffect(Unit) {
        delay(4000)
        visible = false
    }

    val state = when(type){
        is MainStateViewType.ProgressView -> {
            State.Loading(
                "Wait a moment",
                ProgressIndicator.Circular(progressAnimated)
            )
        }
        is MainStateViewType.Failure -> {
            State.Failure(
                labelText = type.labelText
            )
        }
        is MainStateViewType.Success -> {
            State.Success(
                labelText = type.labelText
            )
        }
    }

    StateDialog(
        state = SheetState(visible = visible, onCloseRequest = {}),
        config = StateConfig(state)
    )
}