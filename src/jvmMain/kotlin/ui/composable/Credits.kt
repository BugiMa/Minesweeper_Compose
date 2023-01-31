package ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun Credits(
    onBackClick: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(
            text = "Credits",
            fontWeight = FontWeight.ExtraBold,
        )
        Text(
            text = "Monrad Katuszewski advanced python developer"
        )
        Button(
            onClick = onBackClick,
        ) {
            Text(text = "Back")
        }
    }
}