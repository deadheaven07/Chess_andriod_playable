package com.harsh.chess_application.domain.model

enum class PlayerColor {
    WHITE,
    BLACK;

    fun opposite(): PlayerColor = if (this == WHITE) BLACK else WHITE
}
