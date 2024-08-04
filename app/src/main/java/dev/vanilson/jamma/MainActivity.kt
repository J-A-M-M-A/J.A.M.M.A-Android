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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import dev.vanilson.jamma.domain.model.Category
import dev.vanilson.jamma.domain.model.Transaction
import dev.vanilson.jamma.ui.theme.JAMMATheme
import dev.vanilson.jamma.viewmodels.MainViewModel
import kotlinx.coroutines.flow.flowOf
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()
    private var clickedTimes = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handleNotificationPermission()

        enableEdgeToEdge()
        setContent {
            JAMMATheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { onFabClick() }
                        ) {
                            Text(text = "+")
                        }
                    }
                ) { innerPadding ->
                    val transactions = viewModel.transactions.collectAsState(initial = emptyList())
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding),
                        transactions = transactions,
                        deleter = {
                            viewModel.deleteAllTransactions()
                        },
                        adder = {
                            viewModel.saveTransaction(
                                Transaction(
                                    title = "Transaction #${clickedTimes++}",
                                    amountInCents = 1 * 100,
                                    dueDateTime = LocalDateTime.now().plusDays(1),
                                    category = Category(1, "Shopping", "\uD83D\uDECD\uFE0F")
                                )
                            )
                        }
                    )
                }
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

    private fun onFabClick() {
        Timber.d("onFabClick")
        viewModel.saveTransaction(
            Transaction(
                title = "Transaction #${clickedTimes++}",
                amountInCents = 1 * 100,
                category = Category(1, "Shopping", "\uD83D\uDECD\uFE0F")
            )
        )
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
    transactions: State<List<Transaction>>,
    deleter: () -> Unit,
    adder: () -> Unit
) {
    Column {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        transactions.value.forEach {
            Text(
                text = "${it.title} - ${it.amountInCents} - ${
                    it.dueDateTime.format(
                        DateTimeFormatter.ISO_LOCAL_DATE
                    )
                } - ${it.category.icon}"
            )
        }
        Row {
            Button(
                onClick = {
                    deleter()
                }
            ) {
                Text(text = "Delete All")
            }
            Button(
                onClick = {
                    adder()
                }
            ) {
                Text(text = "Add Tomorrow")
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JAMMATheme {
        Greeting(
            "Android",
            transactions = flowOf(emptyList<Transaction>()).collectAsState(initial = emptyList()),
            deleter = {},
            adder = {}
        )
    }
}