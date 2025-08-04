package dev.vanilson.jamma.transaction.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Backspace
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun Calculator(
    onButtonClick: (String?) -> Unit = {},
    onClear: () -> Unit = {},
    onSubmit: () -> Unit = {},
    onBackspace: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CalculatorButton(
                    text = "1",
                    modifier = Modifier.weight(1f),
                    onClick = onButtonClick
                )
                CalculatorButton(
                    text = "2",
                    modifier = Modifier.weight(1f),
                    onClick = onButtonClick
                )
                CalculatorButton(
                    text = "3",
                    modifier = Modifier.weight(1f),
                    onClick = onButtonClick
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CalculatorButton(
                    text = "4",
                    modifier = Modifier.weight(1f),
                    onClick = onButtonClick
                )
                CalculatorButton(
                    text = "5",
                    modifier = Modifier.weight(1f),
                    onClick = onButtonClick
                )
                CalculatorButton(
                    text = "6",
                    modifier = Modifier.weight(1f),
                    onClick = onButtonClick
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CalculatorButton(
                    text = "7",
                    modifier = Modifier.weight(1f),
                    onClick = onButtonClick
                )
                CalculatorButton(
                    text = "8",
                    modifier = Modifier.weight(1f),
                    onClick = onButtonClick
                )
                CalculatorButton(
                    text = "9",
                    modifier = Modifier.weight(1f),
                    onClick = onButtonClick
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CalculatorButton(
                    text = "00",
                    modifier = Modifier.weight(1f),
                    onClick = onButtonClick
                )
                CalculatorButton(
                    text = "0",
                    modifier = Modifier.weight(1f),
                    onClick = onButtonClick
                )
                CalculatorButton(
                    text = ",",
                    modifier = Modifier.weight(1f),
                    onClick = onButtonClick
                )
            }
        }
        Column(
            modifier = Modifier
                .weight(0.33f)
                .fillMaxSize(),
        ) {
            CalculatorButton(
                text = null,
                icon = Icons.AutoMirrored.Outlined.Backspace,
                modifier = Modifier.weight(0.5f),
                onClick = {
                    onBackspace()
                }
            )
            CalculatorButton(
                text = "C",
                modifier = Modifier.weight(0.5f),
                onClick = {
                    onClear()
                }
            )
            CalculatorButton(
                text = null,
                icon = Icons.Outlined.Check,
                modifier = Modifier.weight(1f),
                onClick = {
                    onSubmit()
                }
            )
        }
    }
}


@Composable
fun CalculatorButton(
    modifier: Modifier = Modifier,
    text: String?,
    icon: ImageVector? = null,
    onClick: (String?) -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp, 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .clickable(onClick = {
                    onClick(text)
                }),
            contentAlignment = Alignment.Center
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(32.dp)
                )
            } else if (text != null) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CalculatorPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Red)
                .padding(8.dp),
            Arrangement.Center,
        ) {
            Calculator()
        }
    }
}
