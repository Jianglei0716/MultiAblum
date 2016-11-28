package com.sy.ablum.cn.bean

import java.io.Serializable
import java.util.*

/**
 * Created by null on 16/6/22.
 */
class Folder : Any(), Serializable {
    var name: String = ""
    var path: String = ""
    var cover: Image? = null
    var images: ArrayList<Image>? = null


    fun addImage(image: Image?) {
        if (image == null)
            return
        if (images == null)
            images = ArrayList<Image>()
        images!!.add(image)
    }

    fun getConverImage(): Image {
        if (images != null && images!!.size != 0) {
            cover = (images as ArrayList<Image>)[0]
        }
        return cover!!
    }

    override fun equals(other: Any?): Boolean {
        if (other is Folder) {
            var folder = other
            return this.name.equals(folder.name)
        } else {
            return false
        }
    }
}