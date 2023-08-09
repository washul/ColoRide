package com.wsl.coloride

import android.app.Application
import android.text.DynamicLayout
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.wsl.coloride.di.appModule
import com.wsl.data.di.dataModule
import com.wsl.domain.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ColoRideApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        /** AppCenter init */
        AppCenter.start(this, "00542a98-6e15-4d8a-b877-9f870ecc620f", Analytics::class.java, Crashes::class.java)

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