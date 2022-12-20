package nl.acidcats.imageviewer.data

fun interface Mapper<in FROM, out TO> {
    fun map(from:FROM): TO
}