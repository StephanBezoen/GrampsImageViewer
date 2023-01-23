@file:OptIn(ExperimentalFoundationApi::class)

package nl.acidcats.imageviewer.android.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nl.acidcats.imageviewer.data.model.Asset

@Composable
fun ImageAssetViewer(
    asset: Asset,
    errorState: MutableState<ErrorState>,
    onSelect: () -> Unit,
    onLongPress: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var job: Job? = null
    var isLoadingShown by remember { mutableStateOf(false) }

    fun hideLoader() {
        job?.cancel()
        job = null

        isLoadingShown = false
    }

    AsyncImage(
        modifier = Modifier
            .combinedClickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onSelect,
                onLongClick = onLongPress
            ),
        model = asset.url,
        contentDescription = asset.id.value,
        alpha = 1f,
        onError = { error ->
            errorState.value = ErrorState.LoadError(
                url = asset.url,
                message = error.result.throwable.message.orEmpty()
            )
            hideLoader()
        },
        onLoading = {
            job = scope.launch {
                delay(200)

                isLoadingShown = true
            }
        },
        onSuccess = { hideLoader() }
    )

    AnimatedVisibility(
        visible = isLoadingShown,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        LoadingView()
    }
}

