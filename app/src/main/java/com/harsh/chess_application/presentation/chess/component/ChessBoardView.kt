package com.harsh.chess_application.presentation.chess.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.harsh.chess_application.domain.model.PieceType
import com.harsh.chess_application.domain.model.Position
import com.harsh.chess_application.presentation.chess.state.ChessState

@Composable
fun ChessBoardView(
    state: ChessState,
    onSquareClick: (Position) -> Unit
) {
    val lastMove = state.moveHistory.lastOrNull()
    val kingInCheckPos = if (state.isCheck) {
        state.board.pieces.entries.find { 
            it.value.type == PieceType.KING && it.value.color == state.currentTurn 
        }?.key
    } else null

    Column(modifier = Modifier.fillMaxWidth()) {
        for (row in 0..7) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (col in 0..7) {
                    val position = Position(row, col)
                    Box(modifier = Modifier.weight(1f)) {
                        ChessSquare(
                            position = position,
                            piece = state.board.getPiece(position),
                            isSelected = state.selectedPosition == position,
                            isLastMove = lastMove?.from == position || lastMove?.to == position,
                            isValidMove = state.validMoves.contains(position),
                            isCheck = kingInCheckPos == position,
                            onClick = { onSquareClick(position) }
                        )
                    }
                }
            }
        }
    }
}
