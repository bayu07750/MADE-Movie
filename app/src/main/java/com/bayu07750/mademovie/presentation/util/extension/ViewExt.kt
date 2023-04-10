package com.bayu07750.mademovie.presentation.util.extension

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

fun View.updatePaddingWithInsets(
    applyToTop: Boolean = true,
    applyToBottom: Boolean = true,
    targetTop: View? = null,
    targetBottom: View? = null,
) {
    ViewCompat.setOnApplyWindowInsetsListener(this) { _, windowInsets ->
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
        val top = insets.top
        val bottom = insets.bottom
        if (applyToTop) if (targetTop != null) targetTop.updatePadding(top = top) else updatePadding(top = top)
        if (applyToBottom) if (targetBottom != null) targetBottom.updatePadding(bottom = bottom) else updatePadding(
            bottom = bottom
        )
        WindowInsetsCompat.CONSUMED
    }
}