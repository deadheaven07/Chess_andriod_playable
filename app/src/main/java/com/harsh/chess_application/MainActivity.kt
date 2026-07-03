package com.harsh.chess_application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.harsh.chess_application.presentation.chess.screen.ChessGameScreen
import com.harsh.chess_application.presentation.chess.viewmodel.ChessViewModel
import com.harsh.chess_application.ui.theme.Chess_ApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Chess_ApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Using manual instantiation as a fallback for sync issues
                    // In a working Hilt setup, use hiltViewModel()
                    ChessGameScreen(viewModel = ChessViewModel())
                }
            }
        }
    }
}
