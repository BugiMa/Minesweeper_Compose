package ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun Menu(
    onPlayClick: () -> Unit,
    onScoreboardClick: () -> Unit,
    onCreditsClick: () -> Unit,
    onExitClick: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(text = "Menu")

        Button(
            onClick = onPlayClick,
        ) {
            Text(text = "Play")
        }

        Button(
            onClick = onScoreboardClick,
        ) {
            Text(text = "Score Board")
        }

        Button(
            onClick = onCreditsClick,
        ) {
            Text(text = "Credits")
        }

        Button(
            onClick = onExitClick,
        ) {
            Text(text = "Exit")
        }
    }
}