package nl.acidcats.imageviewer

import nl.acidcats.imageviewer.data.sharedAppModule
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

class KoinHelper : KoinComponent {
}

fun initKoin() {
    startKoin {
        modules(sharedAppModule())
    }
}
