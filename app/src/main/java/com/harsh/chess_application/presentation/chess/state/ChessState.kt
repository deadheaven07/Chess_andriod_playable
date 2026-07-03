package com.harsh.chess_application.presentation.chess.state

import com.harsh.chess_application.domain.model.ChessBoard
import com.harsh.chess_application.domain.model.Move
import com.harsh.chess_application.domain.model.PlayerColor
import com.harsh.chess_application.domain.model.Position

data class ChessState(
    val board: ChessBoard = ChessBoard.createInitialBoard(),
    val currentTurn: PlayerColor = PlayerColor.WHITE,
    val selectedPosition: Position? = null,
    val validMoves: List<Position> = emptyList(),
    val moveHistory: List<Move> = emptyList(),
    val isGameOver: Boolean = false,
    val winner: PlayerColor? = null,
    val isCheck: Boolean = false
)
