package nl.acidcats.imageviewer.data.usecase

import org.koin.dsl.module

val usecaseModule = module {
    factory {
        SelectAssets(repository = get())
    }
    factory {
        GetAssets(repository = get())
    }
}
