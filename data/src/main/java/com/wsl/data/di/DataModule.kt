package com.wsl.data.di

import com.wsl.data.auto.AutoLocalDataSource
import com.wsl.data.auto.AutoRemoteDataSource
import com.wsl.data.auto.AutoRepository
import com.wsl.data.city.CityLocalDataSource
import com.wsl.data.city.CityRemoteDataSource
import com.wsl.data.city.CityRepository
import com.wsl.data.city.mappers.CityMappersFace
import com.wsl.data.city.mappers.makeCityMapper
import com.wsl.data.db.DB
import com.wsl.data.preferences.MainUserPreferences
import com.wsl.data.provideRetrofit
import com.wsl.data.provideCityApiService
import com.wsl.data.provideCookieJar
import com.wsl.data.providePlainOkHttpClient
import com.wsl.data.route.RouteLocalDataSource
import com.wsl.data.route.RouteRemoteDataSource
import com.wsl.data.route.RouteRepository
import com.wsl.domain.auto.datasource.AutoDataSource
import com.wsl.domain.city.datasource.CityDataSource
import com.wsl.domain.route.datasource.RouteDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import kotlin.math.sin

const val RETROFIT_CLIENT = "RetrofitClient"
const val CITY_MAPPER_LIST = "CityMapperList"
const val DATA_BASE = "DataBase"


val dataModule = module {
    /**Route*/
    single { RouteLocalDataSource() }
    single { RouteRemoteDataSource() }
    single<RouteDataSource> { RouteRepository(get(), get()) }


    /**City*/
    single { CityLocalDataSource(get()) }
    single { CityRemoteDataSource(get(), get()) }
    single<CityDataSource> { CityRepository(get(), get()) }
    factory { CityMappersFace(get(named(CITY_MAPPER_LIST))) }
    factory(named(CITY_MAPPER_LIST)) { makeCityMapper() }

    /**Auto*/
    single { AutoRemoteDataSource() }
    single { AutoLocalDataSource(
        get<DB>(named(DATA_BASE)).autoDao(),
        get<DB>(named(DATA_BASE)).autoSelectedDao()
    )}
    single<AutoDataSource> { AutoRepository(get(), get()) }

    /**Retrofit*/
    factory { provideCityApiService(get(named(RETROFIT_CLIENT))) }
    single(named(RETROFIT_CLIENT)) { provideRetrofit( get(named("plainOkHttpClient"))) }

    factory(named("plainOkHttpClient")) { providePlainOkHttpClient() }
    factory(named("sessionCookieJar")) { provideCookieJar() }


    /**Data Base*/
    single<DB>(named(DATA_BASE)) { DB.getDatabase(androidContext()) }

    single { MainUserPreferences(androidContext()) }
}