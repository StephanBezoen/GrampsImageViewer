package nl.acidcats.imageviewer.data.network.assets

@kotlinx.serialization.Serializable
data class AssetResponse(
    val name: String,
    val type: String,
    val mtime: String,
    val size: Int
)
