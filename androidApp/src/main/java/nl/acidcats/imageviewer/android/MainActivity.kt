package nl.acidcats.imageviewer.android

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import nl.acidcats.imageviewer.android.ui.AssetViewer
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val assets by viewModel.assets.observeAsState(listOf())
                    val index by viewModel.index.observeAsState(0)
                    val isMenuShown = remember { mutableStateOf(false) }

                    AssetViewer(
                        assets = assets,
                        index = index,
                        nextAsset = { viewModel.goNextAsset() },
                        showMenu = { isMenuShown.value = true }
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
    }
}


@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        AssetViewer(assets = listOf(), index = 0, nextAsset = {}, showMenu = {})
    }
}
