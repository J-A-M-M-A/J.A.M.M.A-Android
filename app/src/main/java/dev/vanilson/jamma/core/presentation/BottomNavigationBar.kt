package dev.vanilson.jamma.core.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.vanilson.jamma.AppScreens
import dev.vanilson.jamma.ui.theme.AppTheme

@Composable
fun BottomNavigationBar(navHostController: NavHostController) {
    val navBackStackEntry = navHostController.currentBackStackEntry
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier
    ) {
        AppScreens.entries.filter { it.showInBottomBar }.forEach {
            val selected = currentRoute == it.name
            NavigationBarItem(
                selected = false,
                onClick = {
                    navHostController.navigate(it.name)
                },
                icon = {
                    if (selected) {
                        it.selectedIcon?.let { imageVector ->
                            Icon(
                                imageVector,
                                contentDescription = it.name,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    } else {
                        it.icon?.let { imageVector ->
                            Icon(
                                imageVector,
                                contentDescription = it.name,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                },
            )
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BottomNavigationBarPreview() {
    AppTheme {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(navHostController = NavHostController(androidx.compose.ui.platform.LocalContext.current))
            }
        ) {
            Box(
                modifier = Modifier.padding(it)
            )
        }
    }
}

