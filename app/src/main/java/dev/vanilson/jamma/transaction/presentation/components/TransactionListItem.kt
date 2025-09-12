package dev.vanilson.jamma.transaction.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import dev.vanilson.jamma.transaction.presentation.models.CategoryUI
import dev.vanilson.jamma.transaction.presentation.models.TransactionUI
import dev.vanilson.jamma.ui.theme.AppTheme
import dev.vanilson.jamma.ui.theme.errorDefault
import dev.vanilson.jamma.ui.theme.successDefault
import dev.vanilson.jamma.utils.toFormattedMoney
import java.time.LocalDateTime

@Composable
fun TransactionListItem(
    transactionUI: TransactionUI,
    onItemClick: (TransactionUI) -> Unit,
    onSwipeStartToEnd: (TransactionUI) -> Unit = {},
    onSwipeEndToStart: (TransactionUI) -> Unit = {},
) {
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
        positionalThreshold = { it / 3 },
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.StartToEnd) onSwipeStartToEnd(transactionUI)
            else if (it == SwipeToDismissBoxValue.EndToStart) onSwipeEndToStart(transactionUI)
            // Reset item when toggling done status
            //it != SwipeToDismissBoxValue.StartToEnd
            false
        }
    )

    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        modifier = Modifier
            .fillMaxSize()
            .clickable { onItemClick(transactionUI) },
        backgroundContent = {
            when (swipeToDismissBoxState.dismissDirection) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    Icon(
                        imageVector = if (transactionUI.isPaid) Icons.Default.CheckBox else Icons.Default.CheckBoxOutlineBlank,
                        contentDescription = if (transactionUI.isPaid) "Paid" else "Not Paid",
                        modifier = Modifier
                            .fillMaxSize()
                            .background(if (transactionUI.isPaid) Color.Gray else successDefault)
                            .wrapContentSize(Alignment.CenterStart)
                            .padding(12.dp),
                        tint = Color.White
                    )
                }

                SwipeToDismissBoxValue.EndToStart -> {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Remove item",
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Red)
                            .wrapContentSize(Alignment.CenterEnd)
                            .padding(12.dp),
                        tint = Color.White
                    )
                }

                SwipeToDismissBoxValue.Settled -> {}
            }
        }
    ) {
        ListItem(
            headlineContent = { Text(transactionUI.title) },
            leadingContent = {
                Box(
                    modifier = Modifier
                        .clip(Shapes().extraLarge)
                        .background(
                            if (transactionUI.isPaid)
                                successDefault
                            else if (transactionUI.isOverdue)
                                errorDefault
                            else MaterialTheme.colorScheme.primaryContainer
                        )
                ) {
                    Text(
                        text = transactionUI.category.icon,
                        style = TextStyle(
                            fontSize = 8.em,
                        ),
                        modifier = Modifier.padding(8.dp)
                    )
                }
            },
            supportingContent = {
                Text(
                    text = transactionUI.category.name,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.outline
                )
            },
            trailingContent = {
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "$ ${transactionUI.amount.formatted}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = transactionUI.formattedDueDate,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        )
    }
}

@Preview
@Composable
private fun TransactionListItemPreview() {
    AppTheme {
        TransactionListItem(
            TransactionUI(
                uid = 123,
                title = "Zara",
                amount = (100L).toFormattedMoney(),
                dueDateTime = LocalDateTime.now(),
                category = CategoryUI(
                    uid = 1,
                    name = "Shopping",
                    icon = "\uD83D\uDECD\uFE0F"
                ),
                income = false,
                walletId = 1,
            ),
            onItemClick = {},
        )
    }
}
