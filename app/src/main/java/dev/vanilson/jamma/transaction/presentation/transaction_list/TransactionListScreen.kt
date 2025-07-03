package dev.vanilson.jamma.transaction.presentation.transaction_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import dev.vanilson.jamma.core.presentation.BottomNavigationBar
import dev.vanilson.jamma.transaction.presentation.components.TextAndNumberBox
import dev.vanilson.jamma.transaction.presentation.components.TransactionListHeader
import dev.vanilson.jamma.transaction.presentation.components.TransactionListItem
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
            bottomBar = {
                BottomNavigationBar(
                    navHostController = navHostController
                )
            }
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(it),
            ) {
                TransactionListHeader(
                    modifier = Modifier.padding(top = 16.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextAndNumberBox(
                        modifier = Modifier.padding(8.dp, 16.dp),
                        label = "Day",
                        value = "$ 99.99", //todo state.day
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
                        value = "$ 999.99",//state.month
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
                        value = "$ 9 999.99"//state.month
                    )
                }
                TransactionList(
                    transactions = state.transactions,
                    modifier = Modifier.weight(1f)
                )
            }
        }
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
            }
        ),
        navHostController = NavHostController(LocalContext.current)
    )
}