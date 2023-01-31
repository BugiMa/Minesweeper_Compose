package logic

import androidx.compose.runtime.Immutable

@Immutable
data class Field(
    val isUncovered: Boolean = false,
    val isFlagged: Boolean = false,
    val state: FieldState = FieldState.EMPTY,
    val number: Int = 0
)

enum class FieldState {
    EMPTY,
    NUMBER,
    BOMB
}