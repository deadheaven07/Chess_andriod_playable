package com.harsh.chess_application.presentation.chess.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.harsh.chess_application.domain.model.Piece
import com.harsh.chess_application.domain.model.Position

@Composable
fun ChessSquare(
    position: Position,
    piece: Piece?,
    isSelected: Boolean,
    isLastMove: Boolean,
    isValidMove: Boolean,
    isCheck: Boolean,
    onClick: () -> Unit
) {
    val isDark = (position.row + position.col) % 2 != 0
    val baseColor = if (isDark) Color(0xFF779556) else Color(0xFFEBECD0)
    
    val backgroundColor = when {
        isCheck -> Color(0xFFEF5350).copy(alpha = 0.8f) // Red for check
        isSelected -> Color(0xFFF6F669) // Yellowish for selection
        isLastMove -> Color(0xFFF6F669).copy(alpha = 0.6f)
        else -> baseColor
    }

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .background(backgroundColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        // Render Rank/File labels on edges
        if (position.col == 0) {
            Text(
                text = (8 - position.row).toString(),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 2.dp, top = 2.dp),
                fontSize = 10.sp,
                color = if (isDark) Color(0xFFEBECD0) else Color(0xFF779556),
                fontWeight = FontWeight.Bold
            )
        }
        if (position.row == 7) {
            Text(
                text = ('a' + position.col).toString(),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 2.dp, bottom = 2.dp),
                fontSize = 10.sp,
                color = if (isDark) Color(0xFFEBECD0) else Color(0xFF779556),
                fontWeight = FontWeight.Bold
            )
        }

        if (isValidMove) {
            val isCapture = piece != null
            if (isCapture) {
                // Circle border for captures
                Box(
                    modifier = Modifier
                        .fillMaxSize(0.9f)
                        .border(4.dp, Color.Black.copy(alpha = 0.1f), CircleShape)
                )
            } else {
                // Dot for normal moves
                Box(
                    modifier = Modifier
                        .fillMaxSize(0.3f)
                        .background(Color.Black.copy(alpha = 0.1f), CircleShape)
                )
            }
        }
        
        piece?.let {
            ChessPieceView(it)
        }
    }
}
