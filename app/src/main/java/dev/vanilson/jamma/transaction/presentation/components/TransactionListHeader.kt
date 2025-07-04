package dev.vanilson.jamma.transaction.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TransactionListHeader(
    modifier: Modifier = Modifier,
    amount: String,
    onClickLeft: () -> Unit = {},
    onClickRight: () -> Unit = {}
) {
    Row(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Icon(
            imageVector = Icons.Outlined.AccountCircle,
            contentDescription = "Account",
            modifier = Modifier
                .padding(8.dp)
                .size(28.dp)
                .clickable(
                    onClick = onClickLeft
                ),
            tint = MaterialTheme.colorScheme.primary,
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = amount,
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = "Total Balance",
                style = MaterialTheme.typography.titleMedium
            )
        }

        Icon(
            imageVector = Icons.Outlined.Notifications,
            contentDescription = "Notifications",
            modifier = Modifier
                .padding(8.dp)
                .size(28.dp)
                .clickable(
                    onClick = onClickRight
                ),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}