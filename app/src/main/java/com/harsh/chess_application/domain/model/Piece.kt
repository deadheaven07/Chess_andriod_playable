package com.harsh.chess_application.domain.model

data class Piece(
    val type: PieceType,
    val color: PlayerColor,
    val hasMoved: Boolean = false
)
