package dev.vanilson.jamma.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.vanilson.jamma.domain.model.Category
import dev.vanilson.jamma.domain.model.Transaction
import dev.vanilson.jamma.viewmodels.MainViewModel
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

var clickedTimes = 0

@Composable
fun TransactionsScreen(viewModel: MainViewModel = koinViewModel()) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onFabClick(viewModel) },
                containerColor = Color(14, 27, 37),
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
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

fun onFabClick(viewModel: MainViewModel) {
    Timber.d("onFabClick")
    viewModel.saveTransaction(
        Transaction(
            title = "Transaction #${clickedTimes++}",
            amountInCents = 1 * 100,
            category = Category(1, "Shopping", "\uD83D\uDECD\uFE0F")
        )
    )
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