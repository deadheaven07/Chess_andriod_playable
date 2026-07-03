package com.harsh.chess_application.domain.model

data class ChessBoard(
    val pieces: Map<Position, Piece> = emptyMap()
) {
    fun getPiece(position: Position): Piece? = pieces[position]

    companion object {
        fun createInitialBoard(): ChessBoard {
            val pieces = mutableMapOf<Position, Piece>()
            
            // Pawns
            for (col in 0..7) {
                pieces[Position(1, col)] = Piece(PieceType.PAWN, PlayerColor.BLACK)
                pieces[Position(6, col)] = Piece(PieceType.PAWN, PlayerColor.WHITE)
            }

            // Rooks
            pieces[Position(0, 0)] = Piece(PieceType.ROOK, PlayerColor.BLACK)
            pieces[Position(0, 7)] = Piece(PieceType.ROOK, PlayerColor.BLACK)
            pieces[Position(7, 0)] = Piece(PieceType.ROOK, PlayerColor.WHITE)
            pieces[Position(7, 7)] = Piece(PieceType.ROOK, PlayerColor.WHITE)

            // Knights
            pieces[Position(0, 1)] = Piece(PieceType.KNIGHT, PlayerColor.BLACK)
            pieces[Position(0, 6)] = Piece(PieceType.KNIGHT, PlayerColor.BLACK)
            pieces[Position(7, 1)] = Piece(PieceType.KNIGHT, PlayerColor.WHITE)
            pieces[Position(7, 6)] = Piece(PieceType.KNIGHT, PlayerColor.WHITE)

            // Bishops
            pieces[Position(0, 2)] = Piece(PieceType.BISHOP, PlayerColor.BLACK)
            pieces[Position(0, 5)] = Piece(PieceType.BISHOP, PlayerColor.BLACK)
            pieces[Position(7, 2)] = Piece(PieceType.BISHOP, PlayerColor.WHITE)
            pieces[Position(7, 5)] = Piece(PieceType.BISHOP, PlayerColor.WHITE)

            // Queens
            pieces[Position(0, 3)] = Piece(PieceType.QUEEN, PlayerColor.BLACK)
            pieces[Position(7, 3)] = Piece(PieceType.QUEEN, PlayerColor.WHITE)

            // Kings
            pieces[Position(0, 4)] = Piece(PieceType.KING, PlayerColor.BLACK)
            pieces[Position(7, 4)] = Piece(PieceType.KING, PlayerColor.WHITE)

            return ChessBoard(pieces)
        }
    }
}
