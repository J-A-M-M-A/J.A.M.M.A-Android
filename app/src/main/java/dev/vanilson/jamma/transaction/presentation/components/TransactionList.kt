package dev.vanilson.jamma.transaction.presentation.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dev.vanilson.jamma.TransactionEditScreenRoute
import dev.vanilson.jamma.transaction.presentation.models.TransactionUI

@Composable
fun TransactionList(
    transactions: List<TransactionUI>,
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    onSwipeItemEndToStart: (TransactionUI) -> Unit = {},
    onSwipeItemStartToEnd: (TransactionUI) -> Unit = {},
) {
    val openDialog = remember { mutableStateOf(false) }
    LazyColumn(modifier) {
        items(transactions) { transaction ->
            TransactionListItem(
                transactionUI = transaction,
                onItemClick = {
                    navHostController.navigate(
                        TransactionEditScreenRoute(
                            transactionId = transaction.uid
                        )
                    )
                },
                onSwipeEndToStart = onSwipeItemEndToStart,
                onSwipeStartToEnd = onSwipeItemStartToEnd,
            )
        }
    }
}