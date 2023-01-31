package ui.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.mouseClickable
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.isSecondaryPressed
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.delay
import logic.Board
import logic.FieldState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Game(
    board: Board,
    onBackClick: () -> Unit,
) {
    val composition = currentComposer.composition
    var winnerDialogVisibility by remember { mutableStateOf(false) }
    var loserDialogVisibility by remember { mutableStateOf(false) }
    var ticks by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            ticks++
        }
    }

    Box {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(all = 30.dp)
                    .fillMaxWidth(),
            ) {
                Button(onClick = onBackClick) {
                    Text(
                        text = "Back",
                        fontWeight = FontWeight.ExtraBold,
                    )
                }
                Button(
                    onClick = {
                        board.resetCurrentBoard()
                        composition.invalidateAll()
                        ticks = 0
                    }
                ) {
                    Text(
                        text = "Refresh",
                        fontWeight = FontWeight.ExtraBold,
                    )
                }
                Button(
                    onClick = {},
                ) {
                    Icon(
                        painter = painterResource("drawable/alarm.svg"),
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                            .padding(end = 10.dp),
                    )
                    Text(
                        text = "$ticks",
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.defaultMinSize(minWidth = 20.dp),
                    )
                }
            }
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize(),
            ) {
                itemsIndexed(board.board) { rowIndex, _ ->
                    LazyRow(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxHeight(),
                    ) {
                        itemsIndexed(board.board.first()) { colIndex, fund ->
                            Button(
                                contentPadding = PaddingValues(all = 2.dp),
                                enabled = !board.board[rowIndex][colIndex].isUncovered,
                                onClick = {
                                    if (!board.board[rowIndex][colIndex].isUncovered) {
                                        board.uncoverField(rowIndex, colIndex)
                                        composition.invalidateAll()
                                    }
                                },
                                interactionSource = MutableInteractionSource(),
                                modifier = Modifier
                                    .size(size = if (board.board.size > 15) 25.dp else 32.dp)
                                    .padding(all = 1.dp)
                                    .mouseClickable {
                                        if (buttons.isSecondaryPressed) {
                                            board.flagField(rowIndex, colIndex)
                                            if (board.checkForWin()) winnerDialogVisibility = true
                                            composition.invalidateAll()
                                        }
                                    },
                            ) {
                                if (board.board[rowIndex][colIndex].isUncovered) {
                                    if (board.board[rowIndex][colIndex].state == FieldState.NUMBER)
                                        Text(
                                            text = board.board[rowIndex][colIndex].number.toString(),
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                        )
                                    else if (board.board[rowIndex][colIndex].state == FieldState.BOMB) {
                                        Image(
                                            painter = painterResource("drawable/bug.svg"),
                                            contentDescription = null,
                                            modifier = Modifier.fillMaxSize()
                                        )
                                        loserDialogVisibility = true
                                    }
                                } else if (board.board[rowIndex][colIndex].isFlagged) {
                                    Image(
                                        painter = painterResource("drawable/flag.svg"),
                                        contentDescription = null,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        WinnerDialog(
            visible = winnerDialogVisibility,
            onDisposeClick = {
                winnerDialogVisibility = false
                onBackClick()
            }
        )

        LoserDialog(
            visible = loserDialogVisibility,
            onDisposeClick = {
                loserDialogVisibility = false
                onBackClick()
            }
        )
    }
}

@Composable
private fun WinnerDialog(
    visible: Boolean,
    onDisposeClick: () -> Unit,
) {
    Dialog(
        visible = visible,
        onCloseRequest = onDisposeClick,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Congratulations!",
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(bottom = 20.dp),
            )
            Text(text = "You have successfully disarmed all the bombs.")
        }
    }
}

@Composable
private fun LoserDialog(
    visible: Boolean,
    onDisposeClick: () -> Unit,
) {
    Dialog(
        visible = visible,
        onCloseRequest = onDisposeClick,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(
                text = "Kaboom!",
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(bottom = 20.dp),
            )
            Text(text = "You're dead.")
        }
    }
}