package ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ui.Colors

@Composable
fun Credits(
    onBackClick: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().background(Color(Colors.gray)),
    ) {
        Text(
            text = "Credits",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp,
            color = Color(Colors.white)
        )
        Text(
            text = "Game by Monrad Katuszewski advanced python developer",
            fontSize = 16.sp,
            color = Color(Colors.white)
        )
        Button(
            onClick = onBackClick,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(Colors.blue),
                contentColor = Color(Colors.white),
            )
        ) {
            Text(text = "Back")
        }
    }
}