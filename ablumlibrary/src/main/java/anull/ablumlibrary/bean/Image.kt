package com.sy.ablum.cn.bean

import java.io.Serializable

/**
 * Created by null on 16/6/22.
 */
class Image(name: String, path: String, time: Long) : Any(), Serializable {
    var name: String = ""
    var path: String = ""
    var time: Long = 0L

    init {
        this.name = name
        this.path = path
        this.time = time
    }

    override fun equals(other: Any?): Boolean {
        if (other is Image) {
            var image = other
            return this.name.equals(image.name)
        } else {
            return false
        }
    }
}