package ui.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.GameSettings

@Composable
fun Game(
    gameSettings: GameSettings,
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Button(onClick = {}) {
                Text(text = "Back")
            }
            Button(onClick = {}) {
                Text(text = "Refresh")
            }
            Button(onClick = {}) {
                Text(text = "2137")
            }
        }
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight(),
        ) {
            items(
                count = gameSettings.columnsNumber,
            ) {
                LazyRow(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    items(
                        count = gameSettings.rowsNumber,
                    ) { item ->
                        Button(
                            onClick = {},
                            modifier = Modifier
                                .size(16.dp)
                                .padding(all = 1.dp),
                        ) {
                            Text(text = "$item")
                        }
                    }
                }
            }
        }
    }
}