package ui

import logic.Board

sealed class Screen {
    object Menu : Screen()
    object Settings : Screen()
    object Scoreboard : Screen()
    object Credits : Screen()
    data class Game(val board: Board) : Screen()
}