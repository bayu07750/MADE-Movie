package com.bayu07750.mademovie.presentation.util.power_menu

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.bayu07750.mademovie.R
import com.skydoves.powermenu.OnMenuItemClickListener
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem

class LanguagePowerMenuFactory : PowerMenuFactory {

    override fun create(
        ctx: Context,
        lifecycleOwner: LifecycleOwner,
        menuItemClickListener: OnMenuItemClickListener<PowerMenuItem>
    ): PowerMenu {
        return PowerMenuFactory.createBasePowerMenuBuilder(ctx, lifecycleOwner).apply {
            ctx.resources.getStringArray(R.array.list_available_language).forEach {
                addItem(PowerMenuItem(title = it, isSelected = false))
            }
            setAutoDismiss(true)
            setOnMenuItemClickListener(menuItemClickListener)
        }.build()
    }

    companion object {
        fun create(
            ctx: Context,
            lifecycleOwner: LifecycleOwner,
            menuItemClickListener: OnMenuItemClickListener<PowerMenuItem>
        ) = LanguagePowerMenuFactory().create(ctx, lifecycleOwner, menuItemClickListener)
    }
}