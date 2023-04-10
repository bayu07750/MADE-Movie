package com.bayu07750.mademovie.presentation.util.extension

import androidx.annotation.DrawableRes
import com.bayu07750.mademovie.R
import com.google.android.material.appbar.MaterialToolbar

fun MaterialToolbar.setupToolbar(
    title: String,
    titleCenter: Boolean = true,
    iconBack: Boolean = false,
    @DrawableRes icon: Int = R.drawable.arrow_back_white,
) {
    this.title = title
    this.isTitleCentered = titleCenter
    if (iconBack) {
        this.setNavigationIcon(icon)
    }
}