package dev.vanilson.jamma.transaction.presentation.transaction_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import dev.vanilson.jamma.AppScreens
import dev.vanilson.jamma.TransactionEditScreenRoute
import dev.vanilson.jamma.transaction.presentation.components.TextAndNumberBox
import dev.vanilson.jamma.transaction.presentation.components.TransactionList
import dev.vanilson.jamma.transaction.presentation.components.TransactionListHeader
import dev.vanilson.jamma.transaction.presentation.models.CategoryUI
import dev.vanilson.jamma.transaction.presentation.models.TransactionUI
import dev.vanilson.jamma.transaction.presentation.models.toFormattedMoney
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDateTime

@Composable
fun TransactionListScreen(
    modifier: Modifier = Modifier,
    viewModel: TransactionListViewModel? = if (LocalInspectionMode.current) null else koinViewModel(),
    previewState: TransactionListState? = null,
    navHostController: NavHostController,
) {

    val state = viewModel?.state?.collectAsStateWithLifecycle()?.value ?: previewState
    ?: TransactionListState()

    val openDialog = remember { mutableStateOf(false) }

    if (state.isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navHostController.navigate(TransactionEditScreenRoute())
                    },
                    modifier = Modifier.padding(16.dp),
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    shape = FloatingActionButtonDefaults.shape
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Transaction")
                }
            }
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(it),
            ) {
                TransactionListHeader(
                    modifier = Modifier.padding(top = 16.dp),
                    amount = state.totalBalance,
                    onClickLeft = {
                        navHostController.navigate(AppScreens.Settings.name)
                    },
                    onClickRight = {

                    }
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextAndNumberBox(
                        modifier = Modifier.padding(8.dp, 16.dp),
                        label = "Day",
                        value = state.dayAmount,
                        onClick = {
                            viewModel?.saveTransaction(
                                TransactionUI(
                                    title = "Transaction #${viewModel.clickedTimes++}",
                                    amount = (1000..99999).random().toLong().toFormattedMoney(),
                                    dueDateTime = LocalDateTime.now(),
                                    category = CategoryUI(
                                        1,
                                        "Shopping",
                                        "\uD83D\uDECD\uFE0F",
                                    )
                                )
                            )
                        }
                    )
                    TextAndNumberBox(
                        modifier = Modifier.padding(8.dp, 16.dp),
                        label = "Week",
                        value = state.weekAmount,
                        onClick = {
                            viewModel?.saveTransaction(
                                TransactionUI(
                                    title = "Transaction #${viewModel.clickedTimes++}",
                                    amount = (1000..99999).random().toLong().toFormattedMoney(),
                                    dueDateTime = LocalDateTime.now().plusDays(1),
                                    category = CategoryUI(
                                        1,
                                        "Shopping",
                                        "\uD83D\uDECD\uFE0F",
                                    )
                                )
                            )
                        }
                    )
                    TextAndNumberBox(
                        modifier = Modifier.padding(8.dp, 16.dp),
                        label = "Month",
                        value = state.monthAmount,
                    )
                }
                Row {
                    Text(
                        "Recent Transactions",
                        modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                TransactionList(
                    transactions = state.transactions,
                    modifier = Modifier.weight(1f),
                    navHostController = navHostController,
                    onSwipeItemEndToStart = { swipedItem ->
                        viewModel?.markTransactionToDelete(swipedItem)
                        openDialog.value = true
                    },
                    onSwipeItemStartToEnd = { transactionUI ->
                        viewModel?.togglePaidStatus(transactionUI)
                    }
                )
            }
        }
    }
    if (openDialog.value) {
        AlertDialog(
            modifier = Modifier.clip(RoundedCornerShape(10)),
            onDismissRequest = { openDialog.value = false },
            title = { Text(text = "Delete Transaction") },
            text = { Text(state.transactionToDelete?.title ?: "Are you sure?") },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    ),
                    onClick = {
                        viewModel?.deleteMarkedTransaction()
                        openDialog.value = false
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("No")
                }
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TransactionListScreenPreview() {
    TransactionListScreen(
        previewState = TransactionListState(
            transactions = (1..15).map {
                TransactionUI(
                    uid = 123,
                    title = "Zara",
                    amount = (100L).toFormattedMoney(),
                    dueDateTime = LocalDateTime.now(),
                    category = CategoryUI(
                        name = "Shopping",
                        icon = "\uD83D\uDECD\uFE0F"
                    )
                )
            },
            isLoading = false,
        ),
        navHostController = NavHostController(LocalContext.current)
    )
}