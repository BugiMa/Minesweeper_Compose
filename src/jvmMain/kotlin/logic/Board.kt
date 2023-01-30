package logic

class Board(
    private val rows: Int,
    private val cols: Int,
    private val bombCount: Int
) {
    lateinit var board: Array<Array<Field>>

    init {
        createEmptyBoard()
    }

    private fun createEmptyBoard() {
        board = Array(rows) {
            Array(cols) {
                Field()
            }
        }
    }

    fun setup() {
        setupBombs()
        setupNumbers()
    }

    private fun setupBombs() {
        var i = 0
        while (i < bombCount) {
            val bombRow = (0 until rows).random()
            val bombCol = (0 until cols).random()

            board[bombRow][bombCol].let { field ->
                if (field.state != FieldState.BOMB) {
                    board[bombRow][bombCol] = field.copy(state = FieldState.BOMB)
                    i++
                }
            }
        }
    }

    private fun setupNumbers() {

        board.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, field ->
                if (field.state == FieldState.BOMB)
                    setupNumbersAroundBomb(rowIndex, colIndex)
            }
        }
    }

    private fun setupNumbersAroundBomb(row: Int, col: Int) {
        for (r in row.validRange(rows)) {
            for (c in col.validRange(cols)) {
                if (board[r][c].state != FieldState.BOMB) {
                    board[r][c] = board[r][c].copy(
                        number = board[r][c].number + 1,
                        state = FieldState.NUMBER,
                    )
                }
            }
        }
    }

    fun uncoverField(row: Int, col: Int) {
        board[row][col].let { field ->
            if (field.isUncovered || field.isFlagged) return
            board[row][col] = field.copy(isUncovered = true)
            when (field.state) {
                FieldState.EMPTY -> {uncoverAround(row, col)}
                FieldState.NUMBER -> {}
                FieldState.BOMB -> {}
            }
        }
    }

    private fun uncoverAround(row: Int, col: Int) {
        for (r in row.validRange(rows)) {
            for (c in col.validRange(cols)) {
                board[row][col].let { field ->
                    if (field.isUncovered || field.isFlagged) return
                    when (field.state) {
                        FieldState.EMPTY -> {
                            board[row][col] = field.copy(isUncovered = true)
                            uncoverAround(row, col)
                        }

                        FieldState.NUMBER -> {
                            board[row][col] = field.copy(isUncovered = true)
                        }

                        else -> return
                    }
                }
            }
        }
    }

    private fun Int.validRange(max: Int): IntRange {
        return IntRange(
            (this - 1).coerceAtLeast(0),
            (this + 1).coerceAtMost(max - 1)
        )
    }

    fun resetCurrentBoard() {
        board.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { fieldIndex, field ->
                board[rowIndex][fieldIndex] = field.copy(isUncovered = false)
            }
        }
    }
}