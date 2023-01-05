package nl.acidcats.imageviewer.android.ui

import android.net.Uri
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView
import io.github.aakira.napier.Napier
import nl.acidcats.imageviewer.data.model.Asset

@Composable
fun VideoAssetViewer(
    asset: Asset,
    errorState: MutableState<ErrorState>,
    onSelect: () -> Unit,
) {
    val context = LocalContext.current

    val exoPlayer = ExoPlayer.Builder(context).build().apply {
        setMediaItem(MediaItem.fromUri(Uri.parse(asset.url)))
        repeatMode = Player.REPEAT_MODE_ONE
        prepare()
        playWhenReady = true

        addListener(object : Player.Listener {
            override fun onPlayerError(error: PlaybackException) {
                errorState.value = ErrorState.LoadError(
                    url = asset.url,
                    message = error.message ?: error.errorCodeName
                )
            }
        })
    }

    DisposableEffect(
        asset,
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onSelect() },
            factory = {
                StyledPlayerView(context).apply {
                    player = exoPlayer
                }
            })
    ) {
        onDispose { exoPlayer.release() }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .background(MaterialTheme.colors.background)
        ) {
            Button(
                modifier = Modifier.padding(horizontal = 8.dp),
                onClick = {
                    Napier.d { "Next button click" }
                    onSelect()
                }
            ) {
                Text(text = "Next")
            }
        }
    }

}