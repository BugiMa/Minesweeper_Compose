package ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ui.Colors
import ui.GameSettings

@Composable
fun Settings(
    gameSettings: GameSettings,
    onEasyDifficultyClick: () -> Unit,
    onMediumDifficultyClick: () -> Unit,
    onHardDifficultyClick: () -> Unit,
    onColumnsNumberChosen: (String) -> Unit,
    onRowsNumberChosen: (String) -> Unit,
    onBombsNumberChosen: (String) -> Unit,
    onBackClick: () -> Unit,
    onOkClick: () -> Unit,
) {
    var dialogVisibility by remember { mutableStateOf(false) }

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
                text = "Difficulty",
                fontSize = 24.sp,
                color = Color(Colors.white),
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(24.dp),
            )

            Button(
                onClick = onEasyDifficultyClick,
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
                    text = "Easy",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp,
                )
            }

            Button(
                onClick = onMediumDifficultyClick,
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
                    text = "Medium",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp,
                )
            }

            Button(
                onClick = onHardDifficultyClick,
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
                    text = "Hard",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp,
                )
            }

            Button(
                onClick = { dialogVisibility = true },
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
                    text = "Custom",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp,
                )
            }

            Button(
                onClick = onBackClick,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(Colors.blueDark),
                    contentColor = Color(Colors.white)
                ),
                modifier = Modifier.padding(32.dp),
                ) {
                Text(
                    text = "Back",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp,
                )
            }
        }
    }

    CustomSettingsDialog(
        gameSettings = gameSettings,
        visible = dialogVisibility,
        onColumnsNumberChosen = onColumnsNumberChosen,
        onRowsNumberChosen = onRowsNumberChosen,
        onBombsNumberChosen = onBombsNumberChosen,
        onDisposeClick = { dialogVisibility = false },
        onOkClick = {
            dialogVisibility = false
            onOkClick()
        },
    )
}

@Composable
fun CustomSettingsDialog(
    gameSettings: GameSettings,
    visible: Boolean,
    onColumnsNumberChosen: (String) -> Unit,
    onRowsNumberChosen: (String) -> Unit,
    onBombsNumberChosen: (String) -> Unit,
    onDisposeClick: () -> Unit,
    onOkClick: () -> Unit
) {
    Dialog(
        visible = visible,
        onCloseRequest = onDisposeClick,
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp),
            ) {
                Text(
                    text = "Rows: ",
                    fontWeight = FontWeight.ExtraBold,
                )
                Text(text = gameSettings.rowsNumber.toString())
                Slider(
                    value = gameSettings.rowsNumber.toFloat(),
                    valueRange = 4f..20f,
                    onValueChange = { value -> onRowsNumberChosen(value.toString()) },
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp),
            ) {
                Text(
                    text = "Columns: ",
                    fontWeight = FontWeight.ExtraBold,
                )
                Text(text = gameSettings.columnsNumber.toString())
                Slider(
                    value = gameSettings.columnsNumber.toFloat(),
                    valueRange = 4f..40f,
                    onValueChange = { value -> onColumnsNumberChosen(value.toString()) },
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp),
            ) {
                Text(
                    text = "Bombs: ",
                    fontWeight = FontWeight.ExtraBold,
                )
                Text(text = gameSettings.bombsNumber.toString())
                Slider(
                    value = gameSettings.bombsNumber.toFloat(),
                    valueRange = 1f..(gameSettings.columnsNumber * gameSettings.rowsNumber) - 1f,
                    onValueChange = { value -> onBombsNumberChosen(value.toString()) },
                )
            }

            Button(
                onClick = {
                    onOkClick()
                }
            ) {
                Text(text = "OK")
            }
        }
    }
}