package nl.acidcats.imageviewer.data

import nl.acidcats.imageviewer.data.network.di.networkModule
import nl.acidcats.imageviewer.data.usecase.usecaseModule

fun sharedAppModule() = listOf(usecaseModule, networkModule)