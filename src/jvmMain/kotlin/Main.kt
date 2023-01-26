// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.GameSettings
import ui.Screen
import ui.composable.*
import kotlin.system.exitProcess

@Composable
@Preview
fun App() {
    var screenState by remember { mutableStateOf<Screen>(Screen.Menu) }
    var gameSettings by remember { mutableStateOf(GameSettings()) }

    when (screenState) {
        is Screen.Menu -> {
            Menu(
                onPlayClick = { screenState = Screen.Settings },
                onScoreboardClick = { screenState = Screen.Scoreboard },
                onCreditsClick = { screenState = Screen.Credits },
                onExitClick = { (::exitProcess)(0) },
            )
        }

        is Screen.Settings -> {
            Settings(
                gameSettings = gameSettings,
                onEasyDifficultyClick = {
                    gameSettings = GameSettings(
                        columnsNumber = 10,
                        rowsNumber = 10,
                        bombsNumber = 10,
                    )
                    screenState = Screen.Game(gameSettings)
                },
                onMediumDifficultyClick = {
                    gameSettings = GameSettings(
                        columnsNumber = 20,
                        rowsNumber = 20,
                        bombsNumber = 30,
                    )
                    screenState = Screen.Game(gameSettings)
                },
                onHardDifficultyClick = {
                    gameSettings = GameSettings(
                        columnsNumber = 30,
                        rowsNumber = 30,
                        bombsNumber = 50,
                    )
                    screenState = Screen.Game(gameSettings)
                },
                onColumnsNumberChosen = { columns ->
                    if (columns.toIntOrNull() != null)
                        gameSettings = gameSettings.copy(columnsNumber = columns.toInt())
                    else if (columns.isBlank())
                        gameSettings = gameSettings.copy(columnsNumber = 0)
                },
                onRowsNumberChosen = { rows ->
                    if (rows.toIntOrNull() != null)
                        gameSettings = gameSettings.copy(rowsNumber = rows.toInt())
                    else if (rows.isBlank())
                        gameSettings = gameSettings.copy(rowsNumber = 0)
                },
                onBombsNumberChosen = { bombs ->
                    if (bombs.toIntOrNull() != null)
                        gameSettings = gameSettings.copy(bombsNumber = bombs.toInt())
                    else if (bombs.isBlank())
                        gameSettings = gameSettings.copy(bombsNumber = 0)
                },
                onBackClick = { screenState = Screen.Menu },
                onOkClick = { screenState = Screen.Game(gameSettings) },
            )
        }

        is Screen.Game -> {
            Game(
                gameSettings = gameSettings,
            )
        }

        is Screen.Scoreboard -> {
            Scoreboard(
                onBackClick = { screenState = Screen.Menu },
            )
        }

        is Screen.Credits -> {
            Credits(
                onBackClick = { screenState = Screen.Menu },
            )
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        MaterialTheme {
            App()
        }
    }
}
