package nl.acidcats.imageviewer.data

/**
 * Generic interface for mapping data objects
 */
fun interface Mapper<in FROM, out TO> {
    fun map(from:FROM): TO
}