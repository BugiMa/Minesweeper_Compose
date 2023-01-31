package ui.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.mouseClickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import ui.Colors

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Game(
    board: Board,
    onBackClick: () -> Unit,
) {
    val composition = currentComposer.composition
    var winnerDialogVisibility by remember { mutableStateOf(false) }
    var loserDialogVisibility by remember { mutableStateOf(false) }
    var shouldTick by remember { mutableStateOf(true) }
    var ticks by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        while (shouldTick) {
            delay(1000)
            ticks++
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize().background(color = Color(Colors.grayDark)),
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(all = 32.dp)
                    .fillMaxWidth(),
            ) {
                Button(
                    onClick = onBackClick,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(Colors.blue),
                        contentColor = Color(Colors.white)
                    ),
                ) {
                    Text(
                        text = "Back",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp
                    )
                }
                Button(
                    onClick = {
                        board.resetCurrentBoard()
                        composition.invalidateAll()
                        ticks = 0
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(Colors.blue),
                        contentColor = Color(Colors.white)
                    ),
                ) {
                    Text(
                        text = "Refresh",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp
                    )
                }
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(Colors.blue),
                        contentColor = Color(Colors.white)
                    ),
                ) {
                    Icon(
                        painter = painterResource("drawable/alarm.svg"),
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .padding(end = 10.dp),
                    )
                    Text(
                        text = "$ticks",
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.defaultMinSize(minWidth = 48.dp),
                        fontSize = 16.sp
                    )
                }
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.wrapContentHeight()
                        .background(
                            color = Color(Colors.gray),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(24.dp)
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
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color(Colors.blue),
                                        contentColor = Color(Colors.white),
                                        disabledBackgroundColor = Color(Colors.blueDark),
                                        disabledContentColor = Color(Colors.white)
                                    ),
                                    interactionSource = MutableInteractionSource(),
                                    modifier = Modifier
                                        .size(size = if (board.board.size > 15) 25.dp else 32.dp)
                                        .padding(all = 1.dp)
                                        .mouseClickable {
                                            if (buttons.isSecondaryPressed) {
                                                board.flagField(rowIndex, colIndex)
                                                if (board.checkForWin()) {
                                                    winnerDialogVisibility = true
                                                    shouldTick = false
                                                }
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
                                                modifier = Modifier.fillMaxSize(),
                                                colorFilter = ColorFilter.tint(Color(Colors.red))
                                            )
                                            loserDialogVisibility = true
                                        }
                                    } else if (board.board[rowIndex][colIndex].isFlagged) {
                                        Image(
                                            painter = painterResource("drawable/flag.svg"),
                                            contentDescription = null,
                                            colorFilter = ColorFilter.tint(Color(Colors.orange))
                                        )
                                    }
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
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().background(Color(Colors.greenDark)),
            ) {
            Text(
                text = "Congratulations!",
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(bottom = 20.dp),
                color = Color(Colors.white),
            )
            Text(
                text = "You have successfully disarmed all the bombs.",
                color = Color(Colors.white),
            )
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
            modifier = Modifier.fillMaxSize().background(Color(Colors.redDark)),
        ) {
            Text(
                text = "Kaboom!",
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.ExtraBold,
                color = Color(Colors.white),
                modifier = Modifier.padding(bottom = 20.dp),
            )
            Text(
                text = "You're dead.",
                color = Color(Colors.white),
            )
        }
    }
}