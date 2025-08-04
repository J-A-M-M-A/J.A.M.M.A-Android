package dev.vanilson.jamma

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.vanilson.jamma.transaction.presentation.transaction_add.TransactionAddScreen
import dev.vanilson.jamma.transaction.presentation.transaction_list.TransactionListScreen
import dev.vanilson.jamma.ui.DashBoardScreen

enum class AppScreens(
    @StringRes val title: Int,
    val icon: ImageVector? = null,
    val selectedIcon: ImageVector? = null,
    val showInBottomBar: Boolean = true
) {
    Dashboard(R.string.app_name, Icons.Outlined.Home, Icons.Filled.Home),
    Settings(R.string.screen_settings, Icons.Outlined.Settings, Icons.Filled.Settings),
    TransactionAdd(R.string.screen_add_transaction, showInBottomBar = false)
}

@Composable
fun Jamma(
    navHostController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navHostController,
        startDestination = AppScreens.Dashboard.name,
        modifier = Modifier
    ) {
        composable(route = AppScreens.Dashboard.name) {
            TransactionListScreen(
                navHostController = navHostController
            )
        }
        composable(route = AppScreens.Settings.name) {
            DashBoardScreen()
        }
        composable(route = AppScreens.TransactionAdd.name) {
            TransactionAddScreen(navHostController = navHostController)
        }
    }
}
