package logic

class Board(
    private val rows: Int,
    private val cols: Int,
    private val bombCount: Int
) {
    private lateinit var board: Array<Array<Field>>

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
                    field.state = FieldState.BOMB
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
                    board[r][c].number += 1
                    board[r][c].state = FieldState.NUMBER
                }
            }
        }
    }

    fun uncoverField(row: Int, col: Int) {
        board[row][col].let { field ->
            if (field.isUncovered || field.isFlagged) return
            when (field.state) {
                FieldState.EMPTY -> {}
                FieldState.NUMBER -> {}
                FieldState.BOMB -> {}
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
        board.forEach { row ->
            row.forEach { field ->
                field.apply {
                    isUncovered = false
                }
            }
        }
    }
}