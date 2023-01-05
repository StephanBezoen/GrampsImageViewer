package nl.acidcats.imageviewer.data.usecase

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val usecaseModule = module {
    factoryOf(::SelectAssets)
    factoryOf(::GetAssets)
}
