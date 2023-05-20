package com.wsl.coloride.di

import com.wsl.coloride.screens.create_route.CreateRouteViewModel
import com.wsl.coloride.screens.detail.DetailViewModel
import com.wsl.coloride.screens.loadauto.LoadAutoViewModel
import com.wsl.coloride.screens.routes.RoutesViewModel
import com.wsl.coloride.screens.searchCity.SearchCityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { RoutesViewModel(get()) }
    viewModel { DetailViewModel() }
    viewModel { CreateRouteViewModel(get()) }
    viewModel { SearchCityViewModel(get(), get()) }
    viewModel { LoadAutoViewModel() }
}