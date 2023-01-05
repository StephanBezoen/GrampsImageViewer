package nl.acidcats.imageviewer.data.network.assets

import kotlinx.datetime.Instant
import nl.acidcats.imageviewer.data.Mapper
import nl.acidcats.imageviewer.data.model.Asset
import nl.acidcats.imageviewer.data.model.AssetId
import nl.acidcats.imageviewer.data.model.AssetType
import nl.acidcats.imageviewer.data.network.ServiceDef

internal class AssetResponseMapper(
    private val stringToDateMapper: Mapper<String, Instant>,
    private val serviceDef: ServiceDef
): Mapper<AssetResponse, Asset> {
    override fun map(from: AssetResponse):Asset {
        return Asset(
            id = AssetId(from.name),
            url = serviceDef.resolve(from.name),
            type = AssetType.fromFilename(from.name),
            addedTimestamp = stringToDateMapper.map(from.mtime)
        )
    }
}
