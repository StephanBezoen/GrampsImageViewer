package nl.acidcats.imageviewer.android

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import nl.acidcats.imageviewer.data.model.Asset

@Composable
fun AssetViewer(assets: List<Asset>, index: Int, nextImage: () -> Unit) {
    if (assets.isNotEmpty()) {
        val asset = assets[index]
        AsyncImage(
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { nextImage() },
            model = asset.url,
            contentDescription = asset.id.value,
            alpha = 1f,
        )
    }
}