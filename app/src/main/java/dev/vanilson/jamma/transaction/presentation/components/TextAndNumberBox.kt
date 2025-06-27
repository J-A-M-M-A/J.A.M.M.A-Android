package dev.vanilson.jamma.transaction.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TextAndNumberBox(
    modifier: Modifier = Modifier,
    label: String,
    value: String
) {
    Box(
        modifier = modifier
            .fillMaxWidth(0.5f)
            .clip(RoundedCornerShape(20))
    ){
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFFEAEAEA))
                .padding(8.dp)
        ) {
            Text(text = label, fontWeight = FontWeight.Bold)
            Text(text = value)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TextAndNumberBoxPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.2f)
        ) {
            TextAndNumberBox(
                modifier = Modifier,
                label = "Day",
                value = "$ 99,90",
            )
        }
    }

}