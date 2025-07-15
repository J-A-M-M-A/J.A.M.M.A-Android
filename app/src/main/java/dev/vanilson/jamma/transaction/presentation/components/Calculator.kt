package dev.vanilson.jamma.transaction.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import java.nio.file.WatchEvent


@Composable
fun Calculator() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondaryContainer),
        verticalArrangement = Arrangement.Bottom
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
//                .padding(16.dp)
            ,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CalculatorButton(text = "1")
            CalculatorButton(text = "2")
            CalculatorButton(text = "3")
            CalculatorButton(text = null, icon = Icons.AutoMirrored.Outlined.Backspace)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
//                .padding(16.dp)
                ,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CalculatorButton(text = "4")
            CalculatorButton(text = "5")
            CalculatorButton(text = "6")
            CalculatorButton(text = null, icon = Icons.Outlined.CalendarMonth)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Row(
                    modifier = Modifier
//                        .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
                        .fillMaxWidth()
                        ,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    CalculatorButton(text = "7")
                    CalculatorButton(text = "8")
                    CalculatorButton(text = "9")
                    Spacer(modifier = Modifier)
                }
                Row(
                    modifier = Modifier
//                        .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
                        .fillMaxWidth()
                    ,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    CalculatorButton(text = "$")
                    CalculatorButton(text = "0")
                    CalculatorButton(text = ",")
                    Spacer(modifier = Modifier)
                }
            }
            Column(
                modifier = Modifier
//                    .padding(16.dp)
                ,
            ) {
                CalculatorButton(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.secondaryContainer)
//                        .fillMaxHeight(1f)
//                        .weight(1f)
                    ,
                    null,
                    Icons.Outlined.Check
                )
            }
        }
//        Row(modifier = Modifier.weight(1f)) {
//            Spacer(modifier = Modifier.background(Color.Cyan).weight(1f).padding(56.dp))
//        }
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
                .background(MaterialTheme.colorScheme.surface)
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
            modifier = Modifier.fillMaxSize().background(Color.Red).padding(8.dp),
            Arrangement.Center,
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.5f))
            Calculator()
        }
    }
}
