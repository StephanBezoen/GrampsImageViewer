package nl.acidcats.imageviewer.android

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.decode.ImageDecoderDecoder
import coil.disk.DiskCache
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import nl.acidcats.imageviewer.android.di.apiConfigModule
import nl.acidcats.imageviewer.android.di.applicationModule
import nl.acidcats.imageviewer.data.sharedAppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Napier.base(DebugAntilog())

        Coil.setImageLoader {
            ImageLoader.Builder(this@MyApplication)
                .components {
                    add(ImageDecoderDecoder.Factory())
                }
                .diskCache {
                    DiskCache.Builder()
                        .directory(this@MyApplication.cacheDir.resolve("image_cache"))
                        .maxSizeBytes(1000L * 1024 * 1024) // 1GB
                        .build()
                }
                .build()
        }

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(
                applicationModule
                        + apiConfigModule
                        + sharedAppModule()
            )
        }
    }
}