package anull.ablumlibrary

import android.Manifest
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import anull.multiablum.OnLoadFinish
import com.sy.ablum.cn.bean.Folder
import com.sy.ablum.cn.util.ImageLoaderCallback
import kotlinx.android.synthetic.main.activity_multi_selecte_ablum.*
import java.util.*

class MultiSelecteAblumActivity : AppCompatActivity(), OnLoadFinish {

    var REQUEST_CODE_ASK_PERMISSIONS = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi_selecte_ablum)
        initView()
    }

    fun initView() {
        photoGrid.layoutManager = GridLayoutManager(this, 3)
        checkPermission()
    }


    fun checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            getPhoneStoreagePermission()
        } else {
            supportLoaderManager.initLoader(ImageLoaderCallback.LOADER_ALL, null, ImageLoaderCallback(this, this))
        }
    }

    @TargetApi(23)
    fun getPhoneStoreagePermission() {
        var str = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        var hasPermissionReadStorage = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasPermissionReadStorage != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(str,
                    REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            supportLoaderManager.initLoader(ImageLoaderCallback.LOADER_ALL, null, ImageLoaderCallback(this, this))
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_ASK_PERMISSIONS -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    supportLoaderManager.initLoader(ImageLoaderCallback.LOADER_ALL, null, ImageLoaderCallback(this, this))
                } else {
                    this.finish()
                }
            }
        }

    }


    override fun onLoadFailed() {
    }

    override fun onLoadSuccess(folder: ArrayList<Folder>?) {
        var adapter = ImageAdapter()
        adapter.data = folder!![0].images
        photoGrid.adapter = adapter
    }
}
