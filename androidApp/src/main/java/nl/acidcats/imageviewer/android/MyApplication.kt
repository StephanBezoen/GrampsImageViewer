package nl.acidcats.imageviewer.android

import android.app.Application
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import nl.acidcats.imageviewer.android.di.apiConfigModule
import nl.acidcats.imageviewer.android.di.applicationModule
import nl.acidcats.imageviewer.data.network.di.networkModule
import nl.acidcats.imageviewer.data.usecase.usecaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Napier.base(DebugAntilog())

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(
                applicationModule,
                apiConfigModule,
                networkModule,
                usecaseModule
            )
        }
    }
}