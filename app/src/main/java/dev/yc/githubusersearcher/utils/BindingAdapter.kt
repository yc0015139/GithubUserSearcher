package dev.yc.githubusersearcher.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapter {

    @JvmStatic
    @BindingAdapter(value = ["imageUrl", "circleCrop"], requireAll = false)
    fun loadImage(imageView: ImageView?, imageUrl: String?, circleCrop: Boolean? = false) {
        if (imageUrl.isNullOrEmpty()) {
            return
        }

        imageView?.let {
            Glide.with(it)
                .apply { clear(it) }
                .load(imageUrl)
                .apply { if (circleCrop == true) circleCrop() }
                .into(it)
        }
    }
}