package nl.acidcats.imageviewer.android

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
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
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Loading...",
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Center,
            )
        }
    }
}