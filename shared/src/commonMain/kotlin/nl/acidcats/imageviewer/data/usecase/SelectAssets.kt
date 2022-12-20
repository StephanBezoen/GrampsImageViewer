package nl.acidcats.imageviewer.data.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import nl.acidcats.imageviewer.data.AssetRepository
import nl.acidcats.imageviewer.data.FilterBy
import nl.acidcats.imageviewer.data.SelectionCriteria
import nl.acidcats.imageviewer.data.SortOrder
import nl.acidcats.imageviewer.data.model.Asset

class SelectAssets(
    private val repository: AssetRepository
) : UseCase<StateFlow<SelectionCriteria>, Flow<List<Asset>>> {
    override fun invoke(param: StateFlow<SelectionCriteria>): Flow<List<Asset>> {
        return param.combine(repository.assets) { criteria, assets ->
            val filteredAssets = assets.filter { asset ->
                when (criteria.filterby) {
                    FilterBy.None -> true
                    FilterBy.Type -> {
                        if (criteria.type == null) {
                            throw IllegalArgumentException("type property should not be null when specifying FilterBy.Type filter")
                        }
                        asset.type == criteria.type
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
