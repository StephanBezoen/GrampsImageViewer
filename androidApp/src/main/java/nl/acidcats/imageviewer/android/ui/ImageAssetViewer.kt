package nl.acidcats.imageviewer.android.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import nl.acidcats.imageviewer.data.model.Asset

@Composable
fun ImageAssetViewer(
    asset: Asset,
    errorState: MutableState<ErrorState>,
    onSelect: () -> Unit,
) {
    AsyncImage(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onSelect() },
        model = asset.url,
        contentDescription = asset.id.value,
        alpha = 1f,
        onError = { error ->
            errorState.value = ErrorState.LoadError(
                url = asset.url,
                message = error.result.throwable.message.orEmpty()
            )
        }
    )
}