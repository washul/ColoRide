package com.wsl.domain.di

import com.wsl.domain.auto.usecases.GetAutoListUseCase
import com.wsl.domain.auto.usecases.GetAutoSelectedUseCase
import com.wsl.domain.auto.usecases.SetAutoAsSelectedUseCase
import com.wsl.domain.auto.usecases.SetAutoUseCase
import com.wsl.domain.city.usecases.GetCityPreferenceUseCase
import com.wsl.domain.city.usecases.SearchCityByNameUseCase
import com.wsl.domain.city.usecases.SetCityPreferenceUseCase
import com.wsl.domain.route.usecase.GetRoutesUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetRoutesUseCase(get()) }
    factory { GetCityPreferenceUseCase(get()) }
    factory { SetCityPreferenceUseCase(get()) }
    factory { SearchCityByNameUseCase(get()) }

    //Auto
    factory { GetAutoListUseCase(get()) }
    factory { SetAutoUseCase(get(), get()) }
    factory { GetAutoSelectedUseCase(get()) }
    factory { SetAutoAsSelectedUseCase(get()) }
}