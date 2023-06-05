package com.bayu.mademoviecompose.presentation

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bayu.mademoviecompose.presentation.navigation.MainGraph
import com.bayu.mademoviecompose.presentation.ui.theme.MADEMovieTheme
import com.bayu07750.mademovie.core.data.cons.Cons
import com.bayu07750.mademovie.core.data.local.mmkv.Session
import org.koin.android.ext.android.inject
import java.util.Locale

class MainActivity : ComponentActivity() {

    private val session: Session by inject()

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
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MADEMovieTheme(
                darkTheme = false
            ) {
                App()
            }
        }
    }
}

@Composable
private fun App() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier,
        bottomBar = {
            BottomNavigation(
                navController = navController,
            )
        },
        content = { innerPadding ->
            MainGraph(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
            )
        }
    )
}

@Composable
private fun BottomNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val currentDestination = navController.currentBackStackEntryAsState().value
    val currentDestinationRoute = currentDestination?.destination?.route
    val shouldShowBottomNav = BottomNavDestination.getAllBottomNavDestination().any { it.route == currentDestinationRoute }

    BottomAppBar(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.background,
    ) {
        if (shouldShowBottomNav) {
            BottomNavDestination.getAllBottomNavDestination().forEach { destination ->
                val selected = currentDestinationRoute?.equals(destination.route) == true
                val color = if (selected) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground
                BottomNavigationItem(
                    selected = selected,
                    onClick = {
                        navController.navigate(destination.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        val icon = if (selected) destination.selectedIcon else destination.unselectedIcon
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = stringResource(id = destination.label),
                            tint = color
                        )
                    },
                    modifier = Modifier,
                    enabled = true,
                    label = {
                        Text(
                            text = stringResource(id = destination.label),
                            color = color
                        )
                    },
                    alwaysShowLabel = false,
                )
            }
        }
    }
}
