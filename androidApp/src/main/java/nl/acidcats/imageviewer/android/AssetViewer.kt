package nl.acidcats.imageviewer.android

import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import nl.acidcats.imageviewer.data.model.Asset

@Composable
fun AssetViewer(assets: List<Asset>) {
    var index by remember { mutableStateOf(0) }
    if (assets.isNotEmpty()) {
        val asset = assets[index]
        AsyncImage(
            modifier = Modifier.clickable { index = (index + 1) % assets.size },
            model = asset.url,
            contentDescription = asset.id.value,
            alpha = 1f,
        )
    }
}