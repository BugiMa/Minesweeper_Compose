package logic

class Field(
    var isUncovered: Boolean = false,
    var isFlagged: Boolean = false,
    var state: FieldState = FieldState.EMPTY,
    var number: Int = 0
) {}

enum class FieldState {
    EMPTY,
    NUMBER,
    BOMB
}