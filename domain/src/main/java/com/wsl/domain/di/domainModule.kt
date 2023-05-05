package com.wsl.domain.di

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
}