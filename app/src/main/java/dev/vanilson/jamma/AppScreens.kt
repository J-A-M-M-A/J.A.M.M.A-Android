package dev.vanilson.jamma

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.vanilson.jamma.transaction.presentation.transaction_list.TransactionListScreen
import dev.vanilson.jamma.ui.DashBoardScreen

enum class AppScreens(@StringRes val title: Int, val icon: ImageVector) {
    Dashboard(R.string.app_name, Icons.Outlined.Home),
    Settings(R.string.screen_settings, Icons.Outlined.Settings)
}

@Composable
fun Jamma(
    navHostController: NavHostController = rememberNavController()
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navHostController)
        }
    ) {
        NavHost(
            navController = navHostController,
            startDestination = AppScreens.Dashboard.name,
            modifier = Modifier.padding(it)
        ) {
            composable(route = AppScreens.Dashboard.name) {
                TransactionListScreen()
            }
            composable(route = AppScreens.Settings.name) {
                DashBoardScreen()
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navHostController: NavHostController) {
    NavigationBar {
        val navBackStackEntry = navHostController.currentBackStackEntry
        val currentRoute = navBackStackEntry?.destination?.route
        AppScreens.entries.forEach {
            NavigationBarItem(
                selected = currentRoute == it.name,
                onClick = {
                    navHostController.navigate(it.name)
                },
                icon = { it.icon }
            )
        }
    }
}
