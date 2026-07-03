package com.harsh.chess_application.presentation.chess.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.harsh.chess_application.presentation.chess.component.ChessBoardView
import com.harsh.chess_application.presentation.chess.viewmodel.ChessViewModel

@Composable
fun ChessGameScreen(viewModel: ChessViewModel) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (state.isGameOver) "Game Over! Winner: ${state.winner ?: "Draw"}" 
                   else "Turn: ${state.currentTurn}",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (state.isCheck && !state.isGameOver) {
            Text(
                text = "Check!",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        ChessBoardView(
            state = state,
            onSquareClick = viewModel::onSquareClick
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = viewModel::resetGame) {
            Text("Reset Game")
        }
    }
}
