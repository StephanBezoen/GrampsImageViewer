package nl.acidcats.imageviewer.extensions

val String.fileExtension:String
    get() = if (this.contains(".")) substring(lastIndexOf(".") + 1) else ""
