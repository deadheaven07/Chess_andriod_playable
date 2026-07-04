package com.harsh.chess_application.presentation.chess.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.harsh.chess_application.domain.model.PlayerColor
import com.harsh.chess_application.presentation.chess.component.ChessBoardView
import com.harsh.chess_application.presentation.chess.viewmodel.ChessViewModel

@Composable
fun ChessGameScreen(viewModel: ChessViewModel) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF302E2B)) // Dark background like chess.com
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Opponent Info (Black)
        PlayerInfo(
            color = PlayerColor.BLACK,
            isActive = state.currentTurn == PlayerColor.BLACK,
            isWinner = state.isGameOver && state.winner == PlayerColor.BLACK
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f, fill = false)
        ) {
            if (state.isGameOver) {
                Text(
                    text = "Game Over! ${state.winner?.let { "$it Wins!" } ?: "Draw"}",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            } else if (state.isCheck) {
                Text(
                    text = "Check!",
                    color = Color(0xFFEF5350),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .padding(4.dp)
                    .background(Color.Black.copy(alpha = 0.2f))
            ) {
                ChessBoardView(
                    state = state,
                    onSquareClick = viewModel::onSquareClick
                )
            }
        }

        // Current Player Info (White)
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            PlayerInfo(
                color = PlayerColor.WHITE,
                isActive = state.currentTurn == PlayerColor.WHITE,
                isWinner = state.isGameOver && state.winner == PlayerColor.WHITE
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = viewModel::resetGame,
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF81B64C)
                    )
                ) {
                    Text("New Game", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun PlayerInfo(color: PlayerColor, isActive: Boolean, isWinner: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(if (color == PlayerColor.WHITE) Color.White else Color.Black, androidx.compose.foundation.shape.RoundedCornerShape(4.dp))
                    .border(1.dp, Color.Gray, androidx.compose.foundation.shape.RoundedCornerShape(4.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = if (color == PlayerColor.WHITE) "Player 1 (White)" else "Player 2 (Black)",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal
            )
        }
        
        if (isActive && !isWinner) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(Color(0xFF81B64C), CircleShape)
            )
        }
        
        if (isWinner) {
            Text("🏆", fontSize = 20.sp)
        }
    }
}
