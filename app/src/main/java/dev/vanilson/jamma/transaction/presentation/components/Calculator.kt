package dev.vanilson.jamma.transaction.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Backspace
import androidx.compose.material.icons.outlined.CalendarMonth
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
fun Calculator() {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CalculatorButton(text = "1")
                CalculatorButton(text = "2")
                CalculatorButton(text = "3")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CalculatorButton(text = "4")
                CalculatorButton(text = "5")
                CalculatorButton(text = "6")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CalculatorButton(text = "7")
                CalculatorButton(text = "8")
                CalculatorButton(text = "9")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CalculatorButton(text = "$")
                CalculatorButton(text = "0")
                CalculatorButton(text = ",")
            }
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(end = 8.dp),
        ) {
            Column(
                modifier = Modifier,
//                    .weight(1f)
//                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                CalculatorButton(text = null, icon = Icons.AutoMirrored.Outlined.Backspace)
                CalculatorButton(text = null, icon = Icons.Outlined.CalendarMonth)
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight(1f),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                CalculatorButton(
                    modifier = Modifier
                        .fillMaxHeight(),
                    text = null,
                    icon = Icons.Outlined.Check
                )
            }
        }
    }
}


@Composable
fun CalculatorButton(
    modifier: Modifier = Modifier,
    text: String?,
    icon: ImageVector? = null,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier.then(
            Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(16.dp)
                .clickable(onClick = onClick)
        ),
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
//            Spacer(modifier = Modifier.fillMaxHeight(0.5f))
            Calculator()
        }
    }
}
