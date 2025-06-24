package dev.vanilson.jamma.transaction.presentation.transaction_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.vanilson.jamma.transaction.presentation.components.TransactionListItem
import dev.vanilson.jamma.transaction.presentation.models.TransactionUI
import dev.vanilson.jamma.transaction.presentation.models.toFormattedMoney
import java.time.LocalDateTime

@Composable
fun TransactionListScreen(
    state: TransactionListState,
    modifier: Modifier = Modifier
) {
    if (state.isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        TransactionList(
            transactions = state.transactions,
            modifier = modifier
        )
    }
}

@Composable
fun TransactionList(transactions: List<TransactionUI>, modifier: Modifier = Modifier) {
    LazyColumn(modifier) {
        items(transactions) {
            TransactionListItem(
                transactionUI = it,
                onItemClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun TransactionListScreenPreview() {
    TransactionListScreen(
        state = TransactionListState(
            transactions = (1..5).map {
                TransactionUI(
                    uid = 123,
                    title = "Zara",
                    amount = (100L).toFormattedMoney(),
                    dueDateTime = LocalDateTime.now()
                )
            }
        )
    )
}