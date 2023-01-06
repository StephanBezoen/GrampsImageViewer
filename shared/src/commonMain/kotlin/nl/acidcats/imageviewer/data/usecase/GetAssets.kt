package nl.acidcats.imageviewer.data.usecase

import nl.acidcats.imageviewer.data.AssetRepository
import nl.acidcats.imageviewer.data.network.ApiResult

/**
 * Use case to retrieve assets.
 * @param `Boolean` indicating whether the data should be force-refreshed.
 * When `true` this will update from the network. When `false` this will retrieve
 * from the network if necessary, otherwise return available results.
 * @return [ApiResult] with [ApiResult.Error] when an error occurs, or [ApiResult.Success]
 * if all goes well. When retrieving data without forcing a fetch, will always return [ApiResult.Success]
 */
class GetAssets(
    private val repository: AssetRepository
) : SuspendingUseCase<Boolean, ApiResult<Unit>> {
    override suspend fun invoke(param: Boolean): ApiResult<Unit> {
        return if (param) {
            repository.fetchAssets()
        } else {
            repository.loadAssets()
            ApiResult.Success(Unit)
        }
    }
}
