package com.harsh.chess_application.presentation.chess.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.harsh.chess_application.domain.model.Piece
import com.harsh.chess_application.domain.model.PieceType
import com.harsh.chess_application.domain.model.PlayerColor

@Composable
fun ChessPieceView(piece: Piece) {
    val symbol = when (piece.type) {
        PieceType.PAWN -> "♙"
        PieceType.KNIGHT -> "♘"
        PieceType.BISHOP -> "♗"
        PieceType.ROOK -> "♖"
        PieceType.QUEEN -> "♕"
        PieceType.KING -> "♔"
    }
    
    val color = if (piece.color == PlayerColor.WHITE) Color.White else Color.Black
    
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = symbol,
            fontSize = 40.sp,
            color = color
        )
    }
}
