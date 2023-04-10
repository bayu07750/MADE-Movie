package com.bayu07750.mademovie.presentation

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bayu07750.mademovie.R
import com.bayu07750.mademovie.core.data.cons.Cons
import com.bayu07750.mademovie.core.data.local.mmkv.Session
import com.bayu07750.mademovie.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject
import java.util.*

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private val session: Session by inject()

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    private lateinit var splashScreen: SplashScreen

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(
            newBase?.run {
                session.getString(Session.KEY_LANGUAGE).ifEmpty {
                    session.setValue(Session.KEY_LANGUAGE, Cons.Language.EN)
                }
                val locale = Locale(session.getString(Session.KEY_LANGUAGE))
                Locale.setDefault(locale)
                val config = this.resources.configuration
                config.setLocale(locale)
                config.setLayoutDirection(locale)
                createConfigurationContext(config)
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { false }
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        navHostFragment = binding.fragmentContainerView.getFragment()
        navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)
        navController.addOnDestinationChangedListener(this)
    }

    override fun onDestroy() {
        navController.removeOnDestinationChangedListener(this)
        super.onDestroy()
    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        binding.bottomNavigation.isVisible = destination.id == R.id.homeFragment ||
                destination.id == R.id.categoryFragment ||
                destination.label == "BookmarkFragment"
    }
}