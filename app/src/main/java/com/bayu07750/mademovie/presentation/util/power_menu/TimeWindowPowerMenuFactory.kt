package com.bayu07750.mademovie.presentation.util.power_menu

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.bayu07750.mademovie.R
import com.skydoves.powermenu.OnMenuItemClickListener
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem

class TimeWindowPowerMenuFactory : PowerMenuFactory {

    override fun create(
        ctx: Context,
        lifecycleOwner: LifecycleOwner,
        menuItemClickListener: OnMenuItemClickListener<PowerMenuItem>
    ): PowerMenu {
        return PowerMenuFactory.createBasePowerMenuBuilder(ctx, lifecycleOwner).apply {
            addItem(PowerMenuItem(title = ctx.getString(com.bayu07750.mademovie.core.R.string.this_week), isSelected = false))
            addItem(PowerMenuItem(title = ctx.getString(com.bayu07750.mademovie.core.R.string.today), isSelected = false))
            setAutoDismiss(true)
            setOnMenuItemClickListener(menuItemClickListener)
        }.build()
    }

    companion object {
        fun create(
            ctx: Context,
            lifecycleOwner: LifecycleOwner,
            menuItemClickListener: OnMenuItemClickListener<PowerMenuItem>
        ) = TimeWindowPowerMenuFactory().create(ctx, lifecycleOwner, menuItemClickListener)
    }
}