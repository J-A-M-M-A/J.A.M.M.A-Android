package dev.vanilson.jamma

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.vanilson.jamma.ui.TransactionsScreen

enum class AppScreens(@StringRes val title: Int) {
    Dashboard(R.string.app_name)
}

@Composable
fun Jamma(
    navHostController: NavHostController = rememberNavController()
) {
    Scaffold() {
        NavHost(
            navController = navHostController,
            startDestination = AppScreens.Dashboard.name,
            modifier = Modifier.padding(it)
        ) {
            composable(route = AppScreens.Dashboard.name) {
                TransactionsScreen()
            }
        }
    }
}
