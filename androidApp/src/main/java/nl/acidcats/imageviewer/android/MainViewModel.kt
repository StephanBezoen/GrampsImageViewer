package nl.acidcats.imageviewer.android

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import nl.acidcats.imageviewer.data.FilterBy
import nl.acidcats.imageviewer.data.SelectionCriteria
import nl.acidcats.imageviewer.data.SortOrder
import nl.acidcats.imageviewer.data.model.Asset
import nl.acidcats.imageviewer.data.model.AssetType
import nl.acidcats.imageviewer.data.usecase.GetAssets
import nl.acidcats.imageviewer.data.usecase.SelectAssets

class MainViewModel(
    private val getAssets: GetAssets,
    selectAssets: SelectAssets
) : ViewModel() {

    private var criteria = MutableStateFlow(
        SelectionCriteria(
            sortOrder = SortOrder.Random,
            filterby = FilterBy.Type,
            type = AssetType.Image
        )
    )

    val assets: LiveData<List<Asset>> = selectAssets(criteria)
        .onEach { }
        .asLiveData()

    init {
        viewModelScope.launch {
            val result = getAssets(true)
            Napier.d { "result = $result" }
        }
    }
}
