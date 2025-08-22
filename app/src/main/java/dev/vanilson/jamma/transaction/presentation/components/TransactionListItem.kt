package dev.vanilson.jamma.transaction.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import dev.vanilson.jamma.transaction.presentation.models.CategoryUI
import dev.vanilson.jamma.transaction.presentation.models.TransactionUI
import dev.vanilson.jamma.transaction.presentation.models.toFormattedMoney
import dev.vanilson.jamma.ui.theme.AppTheme
import dev.vanilson.jamma.ui.theme.errorDefault
import dev.vanilson.jamma.ui.theme.successDefault
import java.time.LocalDateTime

@Composable
fun TransactionListItem(
    transactionUI: TransactionUI,
    onItemClick: (TransactionUI) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
            .clickable { onItemClick(transactionUI) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        //icon
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
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = transactionUI.title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = transactionUI.category.name,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.outline
            )
        }
        Column(
            modifier = Modifier.weight(1f),
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
                    name = "Shopping",
                    icon = "\uD83D\uDECD\uFE0F"
                )
            ),
            onItemClick = {},
        )
    }
}
