package ui.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeDialog
import androidx.compose.ui.text.input.KeyboardType
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
        create = { ComposeDialog() },
        dispose = { onDisposeClick() },
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
                Text(text = "Rows")
                TextField(
                    value = gameSettings.rowsNumber.toString(),
                    onValueChange = { value -> onRowsNumberChosen(value) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.width(100.dp),
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp),
            ) {
                Text(text = "Columns")
                TextField(
                    value = gameSettings.columnsNumber.toString(),
                    onValueChange = { value -> onColumnsNumberChosen(value) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.width(100.dp),
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp),
            ) {
                Text(text = "Bombs")
                TextField(
                    value = gameSettings.bombsNumber.toString(),
                    onValueChange = { value -> onBombsNumberChosen(value) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.width(100.dp),
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