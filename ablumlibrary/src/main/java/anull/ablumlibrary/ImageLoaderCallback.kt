package com.sy.ablum.cn.util

import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import anull.ablumlibrary.R
import anull.multiablum.OnLoadFinish
import com.sy.ablum.cn.bean.Folder
import com.sy.ablum.cn.bean.Image
import java.io.File
import java.util.*

/**
 * Created by null on 16/6/22.
 */
class ImageLoaderCallback(context: Context, onLoadFinish: OnLoadFinish) : android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {
    companion object {
        val LOADER_ALL = 0
        val LOADER_CATEGORY = 1
    }

    var folders: ArrayList<Folder>? = null
    var onLoadFinish: OnLoadFinish? = null
    var context: Context? = null

    var IMAGE_PROJECTION = arrayOf(MediaStore.Images.Media.DATA, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATE_ADDED, MediaStore.Images.Media._ID)


    init {
        this.context = context
        this.onLoadFinish = onLoadFinish
    }

    override fun onLoadFinished(loader: Loader<Cursor>?, data: Cursor?) {
        handleResult(data!!)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor>? {
        if (id == LOADER_ALL) {
            val cursorLoader = CursorLoader(context,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                    null, null, IMAGE_PROJECTION[2] + " DESC")
            return cursorLoader
        } else if (id == LOADER_CATEGORY) {
            val cursorLoader = CursorLoader(context,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                    IMAGE_PROJECTION[0] + " like '%" + args!!.getString("path") + "%'", null, IMAGE_PROJECTION[2] + " DESC")
            return cursorLoader
        }
        return null
    }

    override fun onLoaderReset(loader: Loader<Cursor>?) {

    }


    private fun handleResult(cursor: Cursor?) {
        if (onLoadFinish == null) {
            return
        }
        if (cursor == null) {
            onLoadFinish!!.onLoadFailed()
        }
        folders = ArrayList<Folder>()
        val topFolder = Folder()
        topFolder.path = ""
        topFolder.name = context!!.getString(R.string.all_image)
        folders!!.add(topFolder)
        if (cursor!!.count == 0) {
            onLoadFinish!!.onLoadSuccess(folders!!)
            return
        }
        cursor.moveToFirst()
        do {
            val path = cursor.getString(cursor.getColumnIndexOrThrow(IMAGE_PROJECTION[0]))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(IMAGE_PROJECTION[1]))
            val dateTime = cursor.getLong(cursor.getColumnIndexOrThrow(IMAGE_PROJECTION[2]))
            val file = File(path)
            if (file.length() != 0L) {
                val image = Image(path, name, dateTime)
                topFolder.addImage(image)
                val folderFile = file.parentFile
                var folder = getFolderByPath(folderFile.path)
                if (folder == null) {
                    folder = Folder()
                    folder.name = folderFile.name
                    folder.path = folderFile.absolutePath
                    folders!!.add(folder)
                }
                folder.addImage(image)
            }
        } while (cursor.moveToNext())
        if (onLoadFinish != null) {
            onLoadFinish!!.onLoadSuccess(folders!!)
        }
    }

    private fun getFolderByPath(path: String): Folder? {
        var i = 1
        val count = folders!!.size
        while (i < count) {
            val folder = folders!![i]
            if (folder.path.equals(path))
                return folder
            i++
        }
        return null
    }

}