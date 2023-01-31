package ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Menu(
    onPlayClick: () -> Unit,
    onCreditsClick: () -> Unit,
    onExitClick: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(
            text = "Menu",
            fontWeight = FontWeight.ExtraBold,
        )

        Button(
            onClick = onPlayClick,
            modifier = Modifier
                .size(
                    width = 300.dp,
                    height = 60.dp,
                ),
        ) {
            Text(
                text = "Play",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 14.sp,
            )
        }

        Button(
            onClick = onCreditsClick,
            modifier = Modifier
                .size(
                    width = 300.dp,
                    height = 60.dp,
                ),
        ) {
            Text(
                text = "Credits",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 14.sp,
            )
        }

        Button(
            onClick = onExitClick,
            modifier = Modifier
                .size(
                    width = 300.dp,
                    height = 60.dp,
                ),
        ) {
            Text(
                text = "Exit",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 14.sp,
            )
        }
    }
}