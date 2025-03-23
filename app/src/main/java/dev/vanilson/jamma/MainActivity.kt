package dev.vanilson.jamma

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import dev.vanilson.jamma.ui.theme.JAMMATheme
import timber.log.Timber


class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handleNotificationPermission()

        enableEdgeToEdge()
        setContent {
            JAMMATheme {
                Jamma()
            }
        }
    }

    private fun handleNotificationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED -> {
                Timber.d("POST_NOTIFICATIONS Permission granted")
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this, Manifest.permission.POST_NOTIFICATIONS
            ) -> {
                Timber.d("POST_NOTIFICATIONS shouldShowRequestPermissionRationale")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 100)
                }
            }

            else -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 100)
                }
            }
        }

        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                Timber.d("Permission granted")
            } else {
                Timber.d("Permission denied")
                Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show()
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }
}

//@Preview(showBackground = true, device = "id:pixel_9", showSystemUi = true)
//@Composable
//fun GreetingPreview() {
//    JAMMATheme {
//        Greeting(
//            "Android",
//            transactions = flowOf(emptyList<Transaction>()).collectAsState(initial = emptyList()),
//            deleter = {},
//            adder = {}
//        )
//    }
//}