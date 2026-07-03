package com.harsh.chess_application.presentation.chess.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.harsh.chess_application.domain.model.Piece
import com.harsh.chess_application.domain.model.Position

@Composable
fun ChessSquare(
    position: Position,
    piece: Piece?,
    isSelected: Boolean,
    isValidMove: Boolean,
    onClick: () -> Unit
) {
    val isDark = (position.row + position.col) % 2 != 0
    val backgroundColor = when {
        isSelected -> Color(0xFFF5F682)
        isDark -> Color(0xFF769656)
        else -> Color(0xFFEEEED2)
    }

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .background(backgroundColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (isValidMove) {
            Box(
                modifier = Modifier
                    .fillMaxSize(0.3f)
                    .background(Color.Black.copy(alpha = 0.2f), CircleShape)
            )
        }
        
        piece?.let {
            ChessPieceView(it)
        }
    }
}
