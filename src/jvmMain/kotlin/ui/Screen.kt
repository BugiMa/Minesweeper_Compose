package ui

sealed class Screen {
    object Menu : Screen()
    object Settings : Screen()
    object Scoreboard : Screen()
    object Credits : Screen()
    data class Game(val gameSettings: GameSettings) : Screen()
}