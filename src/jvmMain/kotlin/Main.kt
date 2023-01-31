// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import logic.Board
import ui.GameSettings
import ui.Screen
import ui.composable.Credits
import ui.composable.Game
import ui.composable.Menu
import ui.composable.Settings
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
                onCreditsClick = { screenState = Screen.Credits },
                onExitClick = { (::exitProcess)(0) },
            )
        }

        is Screen.Settings -> {
            Settings(
                gameSettings = gameSettings,
                onEasyDifficultyClick = {
                    val board = Board(
                        rows = 10,
                        cols = 10,
                        bombCount = 10,
                    )
                    board.setup()
                    screenState = Screen.Game(board)
                },
                onMediumDifficultyClick = {
                    val board = Board(
                        rows = 15,
                        cols = 20,
                        bombCount = 50,
                    )
                    board.setup()
                    screenState = Screen.Game(board)
                },
                onHardDifficultyClick = {
                    val board = Board(
                        rows = 20,
                        cols = 40,
                        bombCount = 100,
                    )
                    board.setup()
                    screenState = Screen.Game(board)
                },
                onColumnsNumberChosen = { columns ->
                    if (columns.substringBefore('.').toIntOrNull() != null) {
                        gameSettings = gameSettings.copy(
                            columnsNumber = columns.substringBefore('.').toInt(),
                            bombsNumber = gameSettings.getBombsNumber(columns.substringBefore('.').toInt(), true),
                        )
                    } else if (columns.isBlank())
                        gameSettings = gameSettings.copy(columnsNumber = 0)
                },
                onRowsNumberChosen = { rows ->
                    if (rows.substringBefore('.').toIntOrNull() != null) {
                        gameSettings = gameSettings.copy(
                            rowsNumber = rows.substringBefore('.').toInt(),
                            bombsNumber = gameSettings.getBombsNumber(rows.substringBefore('.').toInt(), false),
                        )
                    } else if (rows.isBlank())
                        gameSettings = gameSettings.copy(rowsNumber = 0)
                },
                onBombsNumberChosen = { bombs ->
                    if (bombs.substringBefore('.').toIntOrNull() != null)
                        gameSettings = gameSettings.copy(bombsNumber = bombs.substringBefore('.').toInt())
                    else if (bombs.isBlank())
                        gameSettings = gameSettings.copy(bombsNumber = 0)
                },
                onBackClick = { screenState = Screen.Menu },
                onOkClick = {
                    val board = Board(
                        rows = gameSettings.rowsNumber,
                        cols = gameSettings.columnsNumber,
                        bombCount = gameSettings.bombsNumber,
                    )
                    board.setup()
                    screenState = Screen.Game(board)
                },
            )
        }

        is Screen.Game -> {
            Game(
                onBackClick = { screenState = Screen.Menu },
                board = (screenState as Screen.Game).board,
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
    Window(
        title = "Minesweeper",
        onCloseRequest = ::exitApplication,
        resizable = true,
        alwaysOnTop = true,
    ) {
        MaterialTheme {
            window.placement = WindowPlacement.Fullscreen
            App()
        }
    }
}

private fun GameSettings.getBombsNumber(updatedValue: Int, isColumns: Boolean) =
    if (bombsNumber < updatedValue * if (isColumns) rowsNumber else columnsNumber) bombsNumber
    else (updatedValue * if (isColumns) rowsNumber else columnsNumber) - 1

