package com.harsh.chess_application.presentation.chess.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harsh.chess_application.domain.model.*
import com.harsh.chess_application.domain.usecase.MoveValidator
import com.harsh.chess_application.presentation.chess.state.ChessState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ChessViewModel @Inject constructor(
    private val moveValidator: MoveValidator
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChessState())
    val uiState: StateFlow<ChessState> = _uiState.asStateFlow()

    fun onSquareClick(position: Position) {
        val currentState = _uiState.value
        
        if (currentState.selectedPosition == null) {
            selectPiece(position)
        } else {
            if (currentState.validMoves.contains(position)) {
                makeMove(currentState.selectedPosition, position)
            } else {
                selectPiece(position)
            }
        }
    }

    private fun selectPiece(position: Position) {
        val piece = _uiState.value.board.getPiece(position)
        if (piece != null && piece.color == _uiState.value.currentTurn) {
            val validMoves = moveValidator.getValidMoves(_uiState.value.board, position)
            _uiState.update { it.copy(selectedPosition = position, validMoves = validMoves) }
        } else {
            _uiState.update { it.copy(selectedPosition = null, validMoves = emptyList()) }
        }
    }

    private fun makeMove(from: Position, to: Position) {
        val piece = _uiState.value.board.getPiece(from) ?: return
        val capturedPiece = _uiState.value.board.getPiece(to)
        
        val move = Move(from, to, piece, capturedPiece)
        
        val newPieces = _uiState.value.board.pieces.toMutableMap()
        newPieces.remove(from)
        newPieces[to] = piece.copy(hasMoved = true)
        
        val newBoard = ChessBoard(newPieces)
        val nextTurn = _uiState.value.currentTurn.opposite()
        
        _uiState.update {
            it.copy(
                board = newBoard,
                currentTurn = nextTurn,
                selectedPosition = null,
                validMoves = emptyList(),
                moveHistory = it.moveHistory + move,
                isCheck = moveValidator.isCheck(newBoard, nextTurn)
            )
        }
        
        checkGameStatus(newBoard, nextTurn)
    }

    private fun checkGameStatus(board: ChessBoard, color: PlayerColor) {
        // Simple checkmate detection: if in check and no legal moves
        val inCheck = moveValidator.isCheck(board, color)
        val hasLegalMoves = board.pieces.filter { it.value.color == color }.any {
            moveValidator.getValidMoves(board, it.key).isNotEmpty()
        }
        
        if (!hasLegalMoves) {
            _uiState.update {
                it.copy(
                    isGameOver = true,
                    winner = if (inCheck) color.opposite() else null // null means stalemate
                )
            }
        }
    }

    fun resetGame() {
        _uiState.value = ChessState()
    }
}
