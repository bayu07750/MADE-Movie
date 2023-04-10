package com.bayu07750.mademovie.presentation.util.power_menu

import android.content.Context
import android.view.Gravity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.LifecycleOwner
import com.bayu07750.mademovie.R
import com.skydoves.powermenu.OnMenuItemClickListener
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem

interface PowerMenuFactory {

    fun create(
        ctx: Context,
        lifecycleOwner: LifecycleOwner,
        menuItemClickListener: OnMenuItemClickListener<PowerMenuItem>
    ): PowerMenu


    companion object {
        fun createBasePowerMenuBuilder(ctx: Context, lifecycleOwner: LifecycleOwner): PowerMenu.Builder {
            val typeface = ResourcesCompat.getFont(ctx, R.font.poppins_w400)
            return PowerMenu.Builder(ctx).apply {
                setLifecycleOwner(lifecycleOwner)
                setTextColor(ContextCompat.getColor(ctx, R.color.white))
                setTextGravity(Gravity.START)
                if (typeface != null) {
                    setTextTypeface(typeface)
                }
                setMenuColor(ContextCompat.getColor(ctx, R.color.dark_gunmetal))
            }
        }
    }
}