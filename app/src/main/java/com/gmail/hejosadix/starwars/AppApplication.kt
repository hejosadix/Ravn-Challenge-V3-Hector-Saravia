package com.gmail.hejosadix.starwars

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import android.app.Application
import com.gmail.hejosadix.starwars.di.appModule
import com.gmail.hejosadix.starwars.di.peopleModule
import com.gmail.hejosadix.starwars.di.viewModelModel

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@AppApplication)
            modules(
                appModule,
                peopleModule,
                viewModelModel,
            )
        }
    }
}