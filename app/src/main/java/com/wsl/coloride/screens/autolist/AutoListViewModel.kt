package com.wsl.coloride.screens.autolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsl.coloride.util.CommonScreenEvent
import com.wsl.domain.auto.usecases.GetAutoListUseCase
import com.wsl.domain.auto.usecases.SetAutoAsSelectedUseCase
import com.wsl.domain.model.Auto
import com.wsl.domain.model.User
import com.wsl.utils.onFailure
import com.wsl.utils.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AutoListViewModel(
    private val getAutoListUseCase: GetAutoListUseCase,
    private val setAutoAsSelectedUseCase: SetAutoAsSelectedUseCase
) : ViewModel() {

    lateinit var user: User

    var onEvent: MutableStateFlow<CommonScreenEvent> = MutableStateFlow(CommonScreenEvent.Success)
        private set

    var autoList: MutableStateFlow<List<Auto>> = MutableStateFlow(emptyList())
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAutoListUseCase()
                .onSuccess {
                    autoList.emit(it)
                }
                .onFailure {
                    autoList.emit(emptyList())
                }
        }
    }

    fun setAutoAsSelected(auto: Auto) {
        viewModelScope.launch(Dispatchers.IO) {
            setAutoAsSelectedUseCase(
                SetAutoAsSelectedUseCase.Params(
                    auto
                )
            )
                .onFailure {
                    /*handle failure*/
                    emitEvent(CommonScreenEvent.ErrorMessage("Something went wrong, please try again"))
                }
                .onSuccess {
                    emitEvent(CommonScreenEvent.OnCloseRequest)
                }
        }
    }

    private fun emitEvent(event: CommonScreenEvent) {
        viewModelScope.launch { onEvent.emit(event) }
    }

}