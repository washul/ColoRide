package com.wsl.coloride

import android.app.Application
import com.wsl.coloride.di.appModule
import com.wsl.data.di.dataModule
import com.wsl.domain.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ColoRideApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ColoRideApplication)
            modules(
                appModule,
                dataModule,
                domainModule
            )
        }
    }
}