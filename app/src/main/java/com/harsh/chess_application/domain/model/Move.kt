package com.harsh.chess_application.domain.model

data class Move(
    val from: Position,
    val to: Position,
    val pieceMoved: Piece,
    val pieceCaptured: Piece? = null,
    val isCastling: Boolean = false,
    val isEnPassant: Boolean = false,
    val promotionPiece: PieceType? = null
)
