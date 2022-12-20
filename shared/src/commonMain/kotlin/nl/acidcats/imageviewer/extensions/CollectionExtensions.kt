package nl.acidcats.imageviewer.extensions

import nl.acidcats.imageviewer.data.Mapper

fun <T, R> Iterable<T>.mapUsing(mapper: Mapper<T, R>) = map(mapper::map)
