package ui.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
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
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(text = "Difficulty")

            Button(onClick = onEasyDifficultyClick) {
                Text(text = "Easy")
            }

            Button(onClick = onMediumDifficultyClick) {
                Text(text = "Medium")
            }

            Button(onClick = onHardDifficultyClick) {
                Text(text = "Hard")
            }

            Button(onClick = { dialogVisibility = true }) {
                Text(text = "Custom")
            }

            Button(onClick = onBackClick) {
                Text(text = "Back")
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp),
            ) {
                Text(text = "Rows: ")
                Text(text = gameSettings.rowsNumber.toString())
                Slider(
                    value = gameSettings.rowsNumber.toFloat(),
                    valueRange = 4f..32f,
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
                Text(text = "Columns: ")
                Text(text = gameSettings.columnsNumber.toString())
                Slider(
                    value = gameSettings.columnsNumber.toFloat(),
                    valueRange = 4f..32f,
                    onValueChange = { value -> onColumnsNumberChosen(value.toString()) },
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp),
            ) {
                Text(text = "Bombs: ")
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