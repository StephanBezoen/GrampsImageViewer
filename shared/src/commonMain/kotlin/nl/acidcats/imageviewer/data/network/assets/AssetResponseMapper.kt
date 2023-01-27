package nl.acidcats.imageviewer.data.network.assets

import nl.acidcats.imageviewer.data.model.Asset
import nl.acidcats.imageviewer.data.model.AssetId
import nl.acidcats.imageviewer.data.model.AssetType
import nl.acidcats.imageviewer.data.network.ServiceDef
import nl.acidcats.imageviewer.data.network.di.AssetResponseToAssetMapper
import nl.acidcats.imageviewer.data.network.di.StringToInstantMapper

internal class AssetResponseMapper(
    private val stringToDateMapper: StringToInstantMapper,
    private val serviceDef: ServiceDef
) : AssetResponseToAssetMapper {
    override fun map(from: AssetResponse): Asset {
        return Asset(
            id = AssetId(from.name),
            url = serviceDef.resolve(from.name),
            type = AssetType.fromFilename(from.name),
            addedTimestamp = stringToDateMapper.map(from.mtime)
        )
    }
}
