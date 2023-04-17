package com.wsl.data.di

import com.wsl.data.route.RouteLocalDataSource
import com.wsl.data.route.RouteRemoteDataSource
import com.wsl.data.route.RouteRepository
import com.wsl.domain.route.datasource.RouteDataSource
import org.koin.dsl.module

val dataModule = module {
    single { RouteLocalDataSource() }
    single { RouteRemoteDataSource() }
    single<RouteDataSource> { RouteRepository(get(), get()) }
}