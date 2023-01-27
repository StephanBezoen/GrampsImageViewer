package nl.acidcats.imageviewer.data.network.di

import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json
import nl.acidcats.imageviewer.data.AssetRepository
import nl.acidcats.imageviewer.data.AssetRepositoryImpl
import nl.acidcats.imageviewer.data.Mapper
import nl.acidcats.imageviewer.data.model.Asset
import nl.acidcats.imageviewer.data.network.assets.AssetResponse
import nl.acidcats.imageviewer.data.network.assets.AssetResponseMapper
import nl.acidcats.imageviewer.data.network.assets.AssetService
import nl.acidcats.imageviewer.data.network.assets.AssetServiceImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

enum class ServiceDefs {
    AssetJson, AssetPath
}

internal fun interface AssetResponseToAssetMapper : Mapper<AssetResponse, Asset>
fun interface StringToInstantMapper : Mapper<String, Instant>

val networkModule = module {

    single<Logger> {
        object : Logger {
            override fun log(message: String) {
                Napier.d { "Http: $message" }
            }
        }
    }

    single {
        HttpClient {
            install(Logging) {
                logger = get()
                level = LogLevel.HEADERS
            }
            install(ContentNegotiation) {
                json(
                    json = Json {
                        isLenient = true
                        ignoreUnknownKeys = true
                        useArrayPolymorphism = true
                    }
                )
            }
        }
    }

    single<AssetService> {
        AssetServiceImpl(
            client = get(),
            serviceDef = get(named(ServiceDefs.AssetJson.name))
        )
    }

    single<AssetResponseToAssetMapper> {
        AssetResponseMapper(
            stringToDateMapper = get(),
            serviceDef = get(named(ServiceDefs.AssetPath.name))
        )
    }

    singleOf(::AssetRepositoryImpl) { bind<AssetRepository>() }
}
