package nl.acidcats.imageviewer.data.model

import kotlinx.datetime.Instant
import nl.acidcats.imageviewer.extensions.fileExtension
import kotlin.jvm.JvmInline

@JvmInline
value class AssetId(val value:String)

/**
 * Core model holding data related to a single asset
 */
data class Asset(
    val id:AssetId,
    val url: String,
    val addedTimestamp: Instant,
    val type: AssetType
)

fun List<Asset>.ids() = map { it.id.value }

enum class AssetType (private val extensions:List<String>) {
    Image(extensions = listOf("jpg", "jpeg", "png")),
    Gif(extensions = listOf("gif")),
    Video(extensions = listOf("mp4")),
    Unknown(extensions = listOf());

    companion object {
        fun fromFilename(filename:String):AssetType {
            val extension = filename.fileExtension
            return values().firstOrNull { type -> type.extensions.contains(extension) } ?: Unknown
        }
    }
}
