package nl.acidcats.imageviewer.data.network.assets

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import nl.acidcats.imageviewer.data.network.ServiceDef
import nl.acidcats.imageviewer.data.network.urlFromDef

internal interface AssetService {
    suspend fun getAssets(): List<AssetResponse>
}

internal class AssetServiceImpl(
    private val client: HttpClient,
    private val serviceDef: ServiceDef,
) : AssetService {
    override suspend fun getAssets() =
        client
            .get { urlFromDef(serviceDef) }
            .body<List<AssetResponse>>()
}
