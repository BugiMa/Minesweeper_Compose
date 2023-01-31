package ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.Colors

@Composable
fun Menu(
    onPlayClick: () -> Unit,
    onCreditsClick: () -> Unit,
    onExitClick: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize().background(color = Color(Colors.grayDark)),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.wrapContentHeight()
                .background(
                    color = Color(Colors.gray),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(24.dp)
        ) {
            Text(
                text = "Menu",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(Colors.white),
                modifier = Modifier.padding(24.dp),
            )

            Button(
                onClick = onPlayClick,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(Colors.blue),
                    contentColor = Color(Colors.white)
                ),
                modifier = Modifier.padding(16.dp)
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
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(Colors.blue),
                    contentColor = Color(Colors.white)
                ),
                modifier = Modifier.padding(16.dp)
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
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(Colors.blue),
                    contentColor = Color(Colors.white)
                ),
                modifier = Modifier.padding(16.dp)
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
}