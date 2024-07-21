package dev.vanilson.jamma

import android.app.Application
import dev.vanilson.jamma.di.databaseModule
import dev.vanilson.jamma.di.repositoryModule
import dev.vanilson.jamma.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(
                databaseModule,
                repositoryModule,
                viewModelModule,
            )
        }
        Timber.plant(Timber.DebugTree())
    }

}