@file:OptIn(ExperimentalObjCRefinement::class)

package nl.acidcats.imageviewer.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import nl.acidcats.imageviewer.data.model.Asset
import nl.acidcats.imageviewer.data.network.ApiResult
import nl.acidcats.imageviewer.data.network.assets.AssetResponse
import nl.acidcats.imageviewer.data.network.assets.AssetService
import nl.acidcats.imageviewer.extensions.mapUsing
import kotlin.experimental.ExperimentalObjCRefinement
import kotlin.native.HiddenFromObjC

interface AssetRepository {
    val assets: StateFlow<List<Asset>>

    @HiddenFromObjC
    suspend fun loadAssets()

    @HiddenFromObjC
    suspend fun fetchAssets(): ApiResult<Unit>
}

internal class AssetRepositoryImpl(
    private val service: AssetService,
    private val assetResponseMapper: Mapper<AssetResponse, Asset>
) : AssetRepository {
    private val _assets = MutableStateFlow<List<Asset>>(listOf())
    override val assets:StateFlow<List<Asset>> = _assets

    override suspend fun loadAssets() = Unit    // Nothing to do here until we have database storage

    override suspend fun fetchAssets(): ApiResult<Unit> {
        return getResult {
            _assets.value = service
                .getAssets()
                .mapUsing(assetResponseMapper)
        }
    }

    private inline fun <R> getResult(method: () -> R): ApiResult<R> {
        return try {
            val data = method()

            ApiResult.Success(data)
        } catch (t: Throwable) {
            ApiResult.Error(t)
        }
    }
}
