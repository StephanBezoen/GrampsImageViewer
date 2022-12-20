package nl.acidcats.imageviewer.data.network.di

import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
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
import org.koin.core.qualifier.named
import org.koin.dsl.module

enum class ServiceDefs {
    AssetJson, AssetPath
}

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

    single<Mapper<AssetResponse, Asset>>(named("AssetResponse")) {
        AssetResponseMapper(
            stringToDateMapper = get(named<Mapper<String, Instant>>()),
            serviceDef = get(named(ServiceDefs.AssetPath.name))
        )
    }

    single<AssetRepository> {
        AssetRepositoryImpl(
            service = get(),
            assetResponseMapper = get(named("AssetResponse"))
        )
    }
}
