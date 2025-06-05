package com.example.pokedexapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokedexapp.utils.StatUtils
import androidx.compose.ui.res.colorResource
import com.example.pokedexapp.R

@Composable
fun getStatColorByValue(value: Float, maxValue: Float): Color {
    val percentage = value / maxValue
    return when {
        percentage > 0.5f -> colorResource(id = R.color.super_high)
        percentage > 0.4f -> colorResource(id = R.color.high)
        percentage > 0.3f -> colorResource(id = R.color.medium_high)
        percentage > 0.2f -> colorResource(id = R.color.medium)
        percentage > 0.1f -> colorResource(id = R.color.low)
        else -> colorResource(id = R.color.super_low)
    }
}

@Composable
fun StatBar(statName: String, value: Float, maxValue: Float) {
    val color = getStatColorByValue(value, maxValue)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = StatUtils.getAbbreviatedStatName(statName),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(0.8f)
        )

        Box(
            modifier = Modifier
                .weight(3f)
                .height(12.dp)
                .background(Color.Gray, shape = MaterialTheme.shapes.small)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(value / maxValue)
                    .background(color, shape = MaterialTheme.shapes.small),
            ) {
                Text(
                    text = value.toInt().toString(),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(end = 6.dp).align(Alignment.CenterEnd)
                )
            }
        }

        Text(
            text = maxValue.toInt().toString(),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
@Preview(showBackground = true)
@Composable
fun StatBarPreview() {
    Column(modifier = Modifier.padding(16.dp)) {
        StatBar(statName = "HP", value = 150f, maxValue = 300f)
        StatBar(statName = "Attack", value = 200f, maxValue = 300f)
        StatBar(statName = "Defense", value = 100f, maxValue = 300f)
        StatBar(statName = "Speed", value = 250f, maxValue = 300f)
    }
}