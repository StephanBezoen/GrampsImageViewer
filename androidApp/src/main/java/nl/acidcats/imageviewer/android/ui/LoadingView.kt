package nl.acidcats.imageviewer.android.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoadingView() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            text = "Loading...",
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(backgroundColor = 0x000000)
@Composable
fun PreviewLoadingView() {
    LoadingView()
}
