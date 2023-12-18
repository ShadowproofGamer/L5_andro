package com.example.l6_andro

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore

class ImageRepo {
    lateinit var uri: Uri


    fun getSharedList(): MutableList<ImageItem>? {
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        sharedStoreList?.clear()

        val contentResolver: ContentResolver = ctx.contentResolver
        val cursor = contentResolver.query(uri, null, null, null, null)

        if (cursor == null) {
            throw NullPointerException("Unknown URI: $uri")
        } else if (!cursor.moveToFirst()) {
            println("No photos")
        } else {
            val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)
            val nameColumn = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)

            do {
                val thisId = cursor.getLong(idColumn)
                val thisName = cursor.getString(nameColumn)
                val thisContentUri = ContentUris.withAppendedId(uri, thisId)
                val thisUriPath = thisContentUri.toString()

                sharedStoreList?.add(ImageItem(thisName, thisUriPath, "No path yet", thisContentUri))
            } while (cursor.moveToNext())
        }
        return sharedStoreList
    }

    companion object{
        private var INSTANCE: ImageRepo? = null
        private lateinit var ctx: Context
        var sharedStoreList: MutableList<ImageItem>? = null
        fun getInstance(ctx: Context): ImageRepo {
            if (INSTANCE == null){
                INSTANCE = ImageRepo()
                sharedStoreList = mutableListOf<ImageItem>()
                Companion.ctx = ctx
            }
            return INSTANCE as ImageRepo
        }
    }
}