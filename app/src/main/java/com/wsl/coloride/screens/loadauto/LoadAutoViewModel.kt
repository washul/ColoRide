package com.wsl.coloride.screens.loadauto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsl.coloride.ui.main.MainStateViewType
import com.wsl.coloride.util.CommonScreenEvent
import com.wsl.domain.auto.usecases.SetAutoAsSelectedUseCase
import com.wsl.domain.auto.usecases.SetAutoUseCase
import com.wsl.domain.model.Auto
import com.wsl.utils.onFailure
import com.wsl.utils.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoadAutoViewModel(
    private val setAutoUseCase: SetAutoUseCase,
    private val setAutoAsSelectedUseCase: SetAutoAsSelectedUseCase
) : ViewModel() {

    var onEvent: MutableStateFlow<CommonScreenEvent> = MutableStateFlow(CommonScreenEvent.Success)
        private set

    val localAuto: MutableStateFlow<Auto> = MutableStateFlow(Auto())
    fun saveAuto() {
        viewModelScope.launch(Dispatchers.IO) {

            emitEvent(
                CommonScreenEvent.StateViewDialog(
                    type = MainStateViewType.ProgressView
                )
            )

            /**This delay will remove on release*/
            delay(2000)

            setAutoUseCase(SetAutoUseCase.Params(auto = localAuto.value))
                .onSuccess {
                    emitEvent(
                        CommonScreenEvent.StateViewDialog(
                            type = MainStateViewType.Success(labelText = "Your car was add correctly")
                        )
                    )
                    delay(2000)
                    emitEvent(
                        CommonScreenEvent.OnCloseRequest
                    )
                }
                .onFailure {
                    emitEvent(
                        CommonScreenEvent.StateViewDialog(
                            MainStateViewType.Failure("Something went wrong, please try again")
                        )
                    )
                    delay(2000)
                }
        }
    }

    private fun emitEvent(event: CommonScreenEvent) {
        viewModelScope.launch { onEvent.emit(event) }
    }

}