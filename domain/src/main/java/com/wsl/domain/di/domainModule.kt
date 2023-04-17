package com.wsl.domain.di

import com.wsl.domain.route.usecase.GetRoutesUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetRoutesUseCase(get()) }
}