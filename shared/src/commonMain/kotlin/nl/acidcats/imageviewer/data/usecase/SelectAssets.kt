package nl.acidcats.imageviewer.data.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import nl.acidcats.imageviewer.data.AssetRepository
import nl.acidcats.imageviewer.data.FilterBy
import nl.acidcats.imageviewer.data.SelectionCriteria
import nl.acidcats.imageviewer.data.SortOrder
import nl.acidcats.imageviewer.data.model.Asset

/**
 * Use case to select assets from the list of available assets based on selection criteria.
 * Both the criteria and the list of assets are expected as [Flow] instances. The result is also
 * exposed as a [Flow] instance, so changes in both the criteria and the list of available assets
 * will result in updates to the result flow.
 */
class SelectAssets(
    private val repository: AssetRepository
) : UseCase<StateFlow<SelectionCriteria>, Flow<List<Asset>>> {
    override fun invoke(param: StateFlow<SelectionCriteria>): Flow<List<Asset>> {
        return param.combine(repository.assets) { criteria, assets ->
            val filteredAssets = assets.filter { asset ->
                when (criteria.filterby) {
                    FilterBy.None -> true
                    FilterBy.Type -> {
                        if (criteria.types.isNullOrEmpty()) {
                            throw IllegalArgumentException("type property should not be null when specifying FilterBy.Type filter")
                        }
                        asset.type in criteria.types
                    }
                }
            }
            when (criteria.sortOrder) {
                SortOrder.Random -> filteredAssets.shuffled()
                SortOrder.DateAsc -> filteredAssets.sortedBy { it.addedTimestamp }
                SortOrder.DateDesc -> filteredAssets.sortedByDescending { it.addedTimestamp }
                else -> filteredAssets
            }
        }
    }
}
