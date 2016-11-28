package anull.multiablum

import com.sy.ablum.cn.bean.Folder
import java.util.*

/**
 * Created by null on 2016/11/28.
 */
interface OnLoadFinish {
    fun onLoadFailed()

    fun onLoadSuccess(folder: ArrayList<Folder>?)
}