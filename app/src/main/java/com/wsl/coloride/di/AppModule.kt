package com.wsl.coloride.di

import com.wsl.coloride.screens.detail.DetailViewModel
import com.wsl.coloride.screens.routes.RoutesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel  {RoutesViewModel(get())}
    viewModel  { DetailViewModel() }
}