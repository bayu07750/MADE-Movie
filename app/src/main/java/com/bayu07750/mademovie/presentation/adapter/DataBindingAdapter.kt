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
                    .placeholder(com.bayu07750.mademovie.core.R.drawable.placeholder)
                    .fallback(com.bayu07750.mademovie.core.R.drawable.placeholder)
                    .error(com.bayu07750.mademovie.core.R.drawable.placeholder)
                    .into(view)
            }
        }
    }
}