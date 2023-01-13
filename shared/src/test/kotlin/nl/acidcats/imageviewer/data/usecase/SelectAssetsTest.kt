package nl.acidcats.imageviewer.data.usecase

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import nl.acidcats.imageviewer.data.AssetRepository
import nl.acidcats.imageviewer.data.FilterBy
import nl.acidcats.imageviewer.data.SelectionCriteria
import nl.acidcats.imageviewer.data.SortOrder
import nl.acidcats.imageviewer.data.model.Asset
import nl.acidcats.imageviewer.data.model.AssetId
import nl.acidcats.imageviewer.data.model.AssetType
import nl.acidcats.imageviewer.data.model.ids
import nl.acidcats.imageviewer.data.network.ApiResult
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class SelectAssetsTests {

    private val fakeRepository = FakeAssetRepository()
    private lateinit var selectAssets: SelectAssets

    @Before
    fun init() {
        selectAssets = SelectAssets(fakeRepository)
    }

    @Test
    fun `test that filter by none returns all assets`() {
        val criteria = MutableStateFlow(SelectionCriteria(filterby = FilterBy.None))

        runTest {
            selectAssets(criteria).test {
                val result = awaitItem()
                assertEquals(3, result.size)

                assertEquals(listOf("1", "2", "3"), result.ids())
            }
        }
    }

    @Test
    fun `test that filter by type throws an exception when type is not specified`() {
        val criteria = MutableStateFlow(SelectionCriteria(filterby = FilterBy.Type))
        runTest {
            selectAssets(criteria).test {
                val error = awaitError()
                assertEquals(IllegalArgumentException::class, error.cause!!::class)
            }
        }
    }

    @Test
    fun `test that items are filtered by type correctly`() {
        val criteria = MutableStateFlow(SelectionCriteria(filterby = FilterBy.Type, types = listOf(AssetType.Image)))

        runTest {
            selectAssets(criteria).test {
                val result = awaitItem()
                assertEquals(1, result.size)

                assertEquals(listOf("1"), result.ids())
            }
        }
    }

    @Test
    fun `test that items are filtered by multiple types correctly`() {
        val criteria = MutableStateFlow(SelectionCriteria(filterby = FilterBy.Type, types = listOf(AssetType.Image, AssetType.Gif)))

        runTest {
            selectAssets(criteria).test {
                val result = awaitItem()
                assertEquals(2, result.size)

                assertEquals(listOf("1", "2"), result.ids())
            }
        }
    }

    @Test
    fun `test that data updates when the criteria are changed`() {
        val criteria = MutableStateFlow(SelectionCriteria(filterby = FilterBy.Type, types = listOf(AssetType.Image)))

        runTest {
            selectAssets(criteria).test {
                val imageResult = awaitItem()
                assertEquals(listOf("1"), imageResult.ids())

                criteria.value = SelectionCriteria(filterby = FilterBy.Type, types = listOf(AssetType.Video))
                val videoResult = awaitItem()
                assertEquals(listOf("3"), videoResult.ids())
            }
        }
    }

    @Test
    fun `test that items are sorted by date ascending`() {
        val criteria = MutableStateFlow(SelectionCriteria(sortOrder = SortOrder.DateAsc))

        runTest {
            selectAssets(criteria).test {
                val result = awaitItem()
                assertEquals(listOf("3", "1", "2"), result.ids())
            }
        }
    }

    @Test
    fun `test that items are sorted by date descending`() {
        val criteria = MutableStateFlow(SelectionCriteria(sortOrder = SortOrder.DateDesc))

        runTest {
            selectAssets(criteria).test {
                val result = awaitItem()
                assertEquals(listOf("2", "1", "3"), result.ids())
            }
        }
    }

    @Test
    fun `test that items are randomized`() {
        val criteria = MutableStateFlow(SelectionCriteria(sortOrder = SortOrder.Random))

        runTest {
            val itemCounts = mutableListOf(0, 0, 0)

            for (i in 0 until 10000) {
                selectAssets(criteria).test {
                    val assets = awaitItem()

                    itemCounts[assets.indexOfFirst { asset -> asset.id.value == "1" }]++
                }
            }

            val fact12 = itemCounts[0].toFloat() / itemCounts[1].toFloat()
            val fact23 = itemCounts[1].toFloat() / itemCounts[2].toFloat()
            val fact13 = itemCounts[0].toFloat() / itemCounts[2].toFloat()
            assertTrue { fact12 - 1 < .1f && fact23 - 1 < .1f && fact13 - 1 < .1f }
        }
    }

    class FakeAssetRepository : AssetRepository {
        override val assets = MutableStateFlow(
            listOf(
                Asset(
                    id = AssetId("1"),
                    url = "image.jpg",
                    type = AssetType.Image,
                    addedTimestamp = Instant.fromEpochSeconds(1)
                ),
                Asset(
                    id = AssetId("2"),
                    url = "image.gif",
                    type = AssetType.Gif,
                    addedTimestamp = Instant.fromEpochSeconds(2)
                ),
                Asset(
                    id = AssetId("3"),
                    url = "image.mp4",
                    type = AssetType.Video,
                    addedTimestamp = Instant.fromEpochSeconds(0)
                ),
            )
        )

        override suspend fun loadAssets() = Unit

        override suspend fun fetchAssets(): ApiResult<Unit> = ApiResult.Success(Unit)

    }
}