package nl.acidcats.imageviewer.android.ui

import android.net.Uri
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView
import nl.acidcats.imageviewer.data.model.Asset

@Composable
fun VideoAssetViewer(
    asset: Asset,
    errorState: MutableState<ErrorState>,
    onSelect: () -> Unit,
) {
    val context = LocalContext.current

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            repeatMode = Player.REPEAT_MODE_ONE
            prepare()

            addListener(object : Player.Listener {
                override fun onPlayerError(error: PlaybackException) {
                    errorState.value = ErrorState.LoadError(
                        url = asset.url,
                        message = error.message ?: error.errorCodeName
                    )
                }
            })
        }
    }
    exoPlayer.setMediaItem(MediaItem.fromUri(Uri.parse(asset.url)))

    val areControlsVisible = remember { mutableStateOf(false) }
    val lifecycleOwner = rememberUpdatedState(newValue = LocalLifecycleOwner.current)

    DisposableEffect(
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .alpha(.5f)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onSelect
                ),
            factory = {
                StyledPlayerView(context).apply {
                    player = exoPlayer
                    setControllerVisibilityListener(StyledPlayerView.ControllerVisibilityListener { visibility ->
                        areControlsVisible.value = (visibility == View.VISIBLE)
                    })
                    controllerAutoShow = false
                    hideController()
                }
            })
    ) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> exoPlayer.pause()
                Lifecycle.Event.ON_RESUME -> exoPlayer.play()
                else -> {
                    // Intentionally left empty
                }
            }
        }
        val lifecycle = lifecycleOwner.value.lifecycle
        lifecycle.addObserver(observer)

        onDispose {
            lifecycle.removeObserver(observer)

            exoPlayer.release()
        }
    }

    NextButton(areControlsVisible, onSelect)
}

@Composable
private fun NextButton(areControlsVisible: MutableState<Boolean>, onSelect: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .alpha(if (areControlsVisible.value) 1f else 0f)
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .background(MaterialTheme.colors.background)
        ) {
            Button(
                modifier = Modifier.padding(horizontal = 8.dp),
                onClick = {
                    onSelect()
                }
            ) {
                Text(text = "Next")
            }
        }
    }
}
