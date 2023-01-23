package nl.acidcats.imageviewer.android.ui

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nl.acidcats.imageviewer.data.model.Asset

@Composable
fun VideoAssetViewer(
    asset: Asset,
    errorState: MutableState<ErrorState>,
    onSelect: () -> Unit,
) {
    val context = LocalContext.current

    val scope = rememberCoroutineScope()
    var job: Job? = null
    val isLoadingShown = remember { mutableStateOf(false) }

    fun hideLoader() {
        job?.cancel()
        job = null

        isLoadingShown.value = false
    }

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

                override fun onPlaybackStateChanged(playbackState: Int) {
                    when (playbackState) {
                        Player.STATE_BUFFERING -> {
                            job = scope.launch {
                                delay(200)

                                isLoadingShown.value = true
                            }
                        }

                        else -> hideLoader()
                    }
                }
            })
        }
    }
    exoPlayer.setMediaItem(MediaItem.fromUri(Uri.parse(asset.url)))

    val lifecycleOwner = rememberUpdatedState(newValue = LocalLifecycleOwner.current)

    DisposableEffect(
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onSelect
                ),
            factory = {
                StyledPlayerView(context).apply {
                    player = exoPlayer
                    useController = false
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

    VideoLoadingView(isLoading = isLoadingShown)
}

@Composable
private fun VideoLoadingView(isLoading: MutableState<Boolean>) {
    AnimatedVisibility(
        visible = isLoading.value,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        LoadingView()
    }

}
