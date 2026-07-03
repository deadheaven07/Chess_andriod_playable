package com.harsh.chess_application.domain.model

data class Position(val row: Int, val col: Int) {
    fun isOnBoard(): Boolean = row in 0..7 && col in 0..7
}
