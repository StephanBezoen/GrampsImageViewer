package nl.acidcats.imageviewer.data

import nl.acidcats.imageviewer.data.model.AssetType

enum class SortOrder {
    None, Random, DateDesc, DateAsc
}

enum class FilterBy {
    None, Type
}

data class SelectionCriteria(
    val sortOrder: SortOrder = SortOrder.None,
    val filterby: FilterBy = FilterBy.None,
    val types:List<AssetType>? = null
)

//enum class CustomCriteria(val criteria: SelectionCriteria(
//)