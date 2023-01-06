package nl.acidcats.imageviewer.data

import nl.acidcats.imageviewer.data.model.AssetType

/**
 * Available options for sorting data
 */
enum class SortOrder {
    None, Random, DateDesc, DateAsc
}

/**
 * Available options for filtering data
 */
enum class FilterBy {
    None, Type
}

/**
 * Set of criteria for sorting & filtering data
 */
data class SelectionCriteria(
    val sortOrder: SortOrder = SortOrder.None,
    val filterby: FilterBy = FilterBy.None,
    val types: List<AssetType>? = null
)

/**
 * Preset criteria for sorting & filtering data
 */
@Suppress("unused")
enum class CustomCriteria(val criteria: SelectionCriteria) {
    RANDOM_IMAGES(
        SelectionCriteria(
            sortOrder = SortOrder.Random,
            filterby = FilterBy.Type,
            types = listOf(AssetType.Image, AssetType.Gif)
        )
    ),
    RANDOM_VIDEOS(
        SelectionCriteria(
            sortOrder = SortOrder.Random,
            filterby = FilterBy.Type,
            types = listOf(AssetType.Video)
        )
    ),
    RANDOM(
        SelectionCriteria(
            sortOrder = SortOrder.Random,
            filterby = FilterBy.None,
        )
    ),
    LATEST(
        SelectionCriteria(
            sortOrder = SortOrder.DateDesc,
            filterby = FilterBy.None,
        )
    ),
}
