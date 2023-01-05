package nl.acidcats.imageviewer.android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.aakira.napier.Napier
import nl.acidcats.imageviewer.data.model.Asset
import nl.acidcats.imageviewer.data.model.AssetType

@Composable
fun AssetViewer(assets: List<Asset>, index: Int, nextAsset: () -> Unit) {
    Napier.d { "index = $index" }

    if (assets.isNotEmpty()) {
        val errorState = remember { mutableStateOf<ErrorState>(ErrorState.None) }

        val asset = assets[index]
        Napier.d { "asset = $asset" }
        when (asset.type) {
            AssetType.Image, AssetType.Gif -> ImageAssetViewer(
                onSelect = nextAsset,
                asset = asset,
                errorState = errorState
            )
            AssetType.Video -> {
                VideoAssetViewer(
                    asset = asset,
                    errorState = errorState,
                    onSelect = nextAsset
                )
            }
            else -> nextAsset()
        }

        if (errorState.value != ErrorState.None) {
            Snackbar(message = "Failed ${asset.url}", buttonText = "Close") {
                errorState.value = ErrorState.None
            }
        }
    } else {
        LoadingView()
    }
}

@Composable
private fun LoadingView() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "Loading...",
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun Snackbar(message: String, buttonText: String, onClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .background(MaterialTheme.colors.background)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 8.dp)
                    .widthIn(max = 220.dp),
                text = message,
                color = MaterialTheme.colors.onBackground,
                maxLines = 2,
                overflow = TextOverflow.Visible,
            )
            Button(
                modifier = Modifier.padding(horizontal = 8.dp),
                onClick = onClick
            ) {
                Text(text = buttonText)
            }
        }
    }
}

@Preview
@Composable
fun PreviewSnackbar() {
    Snackbar(message = "Failed https://www.somedomain.someimage.jpeg", buttonText = "Click") {}
}

sealed class ErrorState {
    object None : ErrorState()
    data class LoadError(val url: String, val message: String) : ErrorState()
}