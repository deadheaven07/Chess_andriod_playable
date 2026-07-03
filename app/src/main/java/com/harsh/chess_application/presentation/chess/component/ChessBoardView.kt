package com.harsh.chess_application.presentation.chess.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.harsh.chess_application.domain.model.Position
import com.harsh.chess_application.presentation.chess.state.ChessState

@Composable
fun ChessBoardView(
    state: ChessState,
    onSquareClick: (Position) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        for (row in 0..7) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (col in 0..7) {
                    val position = Position(row, col)
                    ChessSquare(
                        position = position,
                        piece = state.board.getPiece(position),
                        isSelected = state.selectedPosition == position,
                        isValidMove = state.validMoves.contains(position),
                        onClick = { onSquareClick(position) }
                    )
                }
            }
        }
    }
}
