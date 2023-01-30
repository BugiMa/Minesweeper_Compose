package ui.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import logic.Board
import logic.Field
import logic.FieldState

@Composable
fun Game(
    board: Board,
    onBackClick: () -> Unit,
) {
    val composition = currentComposer.composition

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(all = 30.dp)
                .fillMaxWidth(),
        ) {
            Button(onClick = onBackClick) {
                Text(text = "Back")
            }
            Button(onClick = {
                board.resetCurrentBoard()
                composition.invalidateAll()
            }) {
                Text(text = "Refresh")
            }
            Button(onClick = {}) {
                Text(text = "2137")
            }
        }
        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth(),
        ) {
            itemsIndexed(board.board) { rowIndex, _ ->
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxHeight(),
                ) {
                    itemsIndexed(board.board.first()) { colIndex, field ->
                        Button(
                            onClick = {
                                if (!field.isUncovered) {
                                    board.uncoverField(rowIndex, colIndex)
                                    composition.invalidateAll()
                                }
                            },
                            modifier = Modifier
                                .size(size = 40.dp)
                                .padding(all = 1.dp),
                        ) {
                            Text(
                                text = board.board[rowIndex][colIndex].getText(),
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun Field.getText() =
    if (isUncovered) {
        when (state) {
            FieldState.NUMBER -> number.toString()
            FieldState.BOMB -> "B"
            FieldState.EMPTY -> "U"
        }
    } else if (isFlagged) "F"
    else "C"
