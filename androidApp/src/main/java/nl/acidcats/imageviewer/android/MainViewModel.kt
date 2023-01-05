package nl.acidcats.imageviewer.android

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import nl.acidcats.imageviewer.data.CustomCriteria
import nl.acidcats.imageviewer.data.model.Asset
import nl.acidcats.imageviewer.data.usecase.GetAssets
import nl.acidcats.imageviewer.data.usecase.SelectAssets

class MainViewModel(
    private val getAssets: GetAssets,
    selectAssets: SelectAssets
) : ViewModel() {

    private val criteria = MutableStateFlow(CustomCriteria.RANDOM.criteria)

    private val _index = MutableStateFlow(0)
    val index = _index.asLiveData()

    val assets: LiveData<List<Asset>> = selectAssets(criteria)
        .onEach { _index.value = 0 }
        .asLiveData()

    init {
        viewModelScope.launch {
            getAssets(true)
        }
    }

    fun goNextImage() {
        _index.value = _index.value + 1 % (assets.value?.size ?: 0)
    }
}
