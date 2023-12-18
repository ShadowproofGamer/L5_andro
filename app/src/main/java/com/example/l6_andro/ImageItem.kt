package com.example.l6_andro

import android.net.Uri

class ImageItem {
    var name: String = "Empty name"
    var uripath: String = "Empty uri"
    var path: String = "Empty path"
    var curi: Uri? = null

    constructor()

    constructor(name:String, uripath:String, path: String, curi: Uri) : this() {
        this.name = name
        this.uripath = uripath
        this.path = path
        this.curi = curi
    }
}