package com.wsl.data.di

import com.wsl.data.city.CityLocalDataSource
import com.wsl.data.city.CityRemoteDataSource
import com.wsl.data.city.CityRepository
import com.wsl.data.city.mappers.CityMappersFace
import com.wsl.data.city.mappers.makeCityMapper
import com.wsl.data.preferences.MainUserPreferences
import com.wsl.data.provideRetrofit
import com.wsl.data.provideCityApiService
import com.wsl.data.provideCookieJar
import com.wsl.data.providePlainOkHttpClient
import com.wsl.data.route.RouteLocalDataSource
import com.wsl.data.route.RouteRemoteDataSource
import com.wsl.data.route.RouteRepository
import com.wsl.domain.city.datasource.CityDataSource
import com.wsl.domain.route.datasource.RouteDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val RETROFIT_CLIENT = "RetrofitClient"
const val CITY_MAPPER_LIST = "CityMapperList"


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


    /**Retrofit*/
    factory { provideCityApiService(get(named(RETROFIT_CLIENT))) }
    single(named(RETROFIT_CLIENT)) { provideRetrofit( get(named("plainOkHttpClient"))) }

    factory(named("plainOkHttpClient")) { providePlainOkHttpClient() }
    factory(named("sessionCookieJar")) { provideCookieJar() }



    single { MainUserPreferences(androidContext()) }
}