package com.bayu07750.mademovie.presentation.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bayu07750.mademovie.R
import com.bumptech.glide.Glide

class DataBindingAdapter {

    companion object {
        @JvmStatic
        @BindingAdapter(
            value = ["glideImage"],
            requireAll = false,
        )
        fun coilImage(view: ImageView?, coilImage: String?) {
            if (view != null) {
                Glide.with(view)
                    .load(coilImage)
                    .placeholder(R.drawable.placeholder)
                    .fallback(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(view)
            }
        }
    }
}