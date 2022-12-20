package nl.acidcats.imageviewer.data.usecase

import nl.acidcats.imageviewer.data.AssetRepository
import nl.acidcats.imageviewer.data.network.ApiResult

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
