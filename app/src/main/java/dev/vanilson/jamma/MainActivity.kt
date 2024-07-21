package dev.vanilson.jamma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.vanilson.jamma.data.entity.Transaction
import dev.vanilson.jamma.ui.theme.JAMMATheme
import dev.vanilson.jamma.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()
    private var clickedTimes = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JAMMATheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { onFabClick() }
                        ) {
                            Text(text = "+");
                        }
                    }
                ) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun onFabClick() {
        Timber.d("onFabClick")
        viewModel.saveTransaction(
            Transaction(
                title = "Transaction #${clickedTimes++}",
                amountInCents = 1 * 100,
            )
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JAMMATheme {
        Greeting("Android")
    }
}