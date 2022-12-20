package nl.acidcats.imageviewer.data.network

data class ServiceDef(
    val apiScheme: String,
    val apiHost: String,
    val apiPath: String,
    val key:String? = null,
    val secret:String? = null,
) {
    fun resolve(filename:String):String {
        val path = "$apiScheme://$apiHost"
        return if (path.isEmpty()) "$path/$filename"
        else "$path/$apiPath/$filename"
    }

}
