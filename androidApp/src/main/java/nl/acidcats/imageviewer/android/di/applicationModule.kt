package nl.acidcats.imageviewer.android.di

import kotlinx.datetime.Instant
import nl.acidcats.imageviewer.android.BuildConfig
import nl.acidcats.imageviewer.android.MainViewModel
import nl.acidcats.imageviewer.data.Mapper
import nl.acidcats.imageviewer.data.network.ServiceDef
import nl.acidcats.imageviewer.data.network.di.ServiceDefs
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.text.SimpleDateFormat
import java.util.*

val applicationModule = module {
    single(named<Mapper<String, Instant>>()) { dateMapper }

    viewModel {
        MainViewModel(
            getAssets = get(),
            selectAssets = get()
        )
    }
}

val apiConfigModule = module {
    single(named(ServiceDefs.AssetJson.name)) {
        ServiceDef(
            apiScheme = BuildConfig.API_SCHEME,
            apiHost = BuildConfig.API_HOST,
            apiPath = BuildConfig.API_PATH_JSON
        )
    }

    single(named(ServiceDefs.AssetPath.name)) {
        ServiceDef(
            apiScheme = BuildConfig.API_SCHEME,
            apiHost = BuildConfig.API_HOST,
            apiPath = BuildConfig.API_PATH_ASSETS
        )
    }
}

private val dateMapper = Mapper<String, Instant> { dateString ->
    val format = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US)
    val date: Date = format.parse(dateString) ?: return@Mapper Instant.DISTANT_PAST
    Instant.fromEpochMilliseconds(date.time)
}
