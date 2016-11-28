package anull.ablumlibrary

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.sy.ablum.cn.bean.Image
import java.io.File
import java.util.*

/**
 * Created by null on 2016/11/28.
 */
class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ImageHolder>() {
    var context: Context? = null
    var data: ArrayList<Image>? = null
    override fun onBindViewHolder(holder: ImageHolder?, position: Int) {
        Glide.with(context!!).load(File(data!![position].name)).override(240, 240).centerCrop().into(holder!!.photoImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ImageHolder {
        context = parent!!.context
        return ImageHolder(LayoutInflater.from(context).inflate(R.layout.item_photo_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return if (data == null || data!!.size == 0) 0 else data!!.size
    }


    inner class ImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var photoImage: ImageView? = null

        init {
            photoImage = itemView.findViewById(R.id.photo) as ImageView?
        }
    }
}