package com.harsh.chess_application.domain.usecase

import com.harsh.chess_application.domain.model.*
import kotlin.math.abs

class MoveValidator {

    fun getValidMoves(board: ChessBoard, position: Position): List<Position> {
        val piece = board.getPiece(position) ?: return emptyList()
        val pseudoLegalMoves = getPseudoLegalMoves(board, position, piece)
        
        // Filter out moves that leave the King in check
        return pseudoLegalMoves.filter { to ->
            !wouldBeInCheck(board, Move(position, to, piece))
        }
    }

    fun getPseudoLegalMoves(board: ChessBoard, position: Position, piece: Piece): List<Position> {
        return when (piece.type) {
            PieceType.PAWN -> getPawnMoves(board, position, piece.color)
            PieceType.KNIGHT -> getKnightMoves(board, position, piece.color)
            PieceType.BISHOP -> getSlidingMoves(board, position, piece.color, listOf(Pair(1, 1), Pair(1, -1), Pair(-1, 1), Pair(-1, -1)))
            PieceType.ROOK -> getSlidingMoves(board, position, piece.color, listOf(Pair(1, 0), Pair(-1, 0), Pair(0, 1), Pair(0, -1)))
            PieceType.QUEEN -> getSlidingMoves(board, position, piece.color, listOf(Pair(1, 1), Pair(1, -1), Pair(-1, 1), Pair(-1, -1), Pair(1, 0), Pair(-1, 0), Pair(0, 1), Pair(0, -1)))
            PieceType.KING -> getKingMoves(board, position, piece.color)
        }
    }

    private fun getPawnMoves(board: ChessBoard, position: Position, color: PlayerColor): List<Position> {
        val moves = mutableListOf<Position>()
        val direction = if (color == PlayerColor.WHITE) -1 else 1
        val startRow = if (color == PlayerColor.WHITE) 6 else 1

        // Forward move
        val oneForward = Position(position.row + direction, position.col)
        if (oneForward.isOnBoard() && board.getPiece(oneForward) == null) {
            moves.add(oneForward)
            
            // Double forward move
            val twoForward = Position(position.row + 2 * direction, position.col)
            if (position.row == startRow && board.getPiece(twoForward) == null) {
                moves.add(twoForward)
            }
        }

        // Captures
        val captures = listOf(
            Position(position.row + direction, position.col - 1),
            Position(position.row + direction, position.col + 1)
        )
        for (target in captures) {
            if (target.isOnBoard()) {
                val targetPiece = board.getPiece(target)
                if (targetPiece != null && targetPiece.color != color) {
                    moves.add(target)
                }
            }
        }

        return moves
    }

    private fun getKnightMoves(board: ChessBoard, position: Position, color: PlayerColor): List<Position> {
        val offsets = listOf(
            Pair(2, 1), Pair(2, -1), Pair(-2, 1), Pair(-2, -1),
            Pair(1, 2), Pair(1, -2), Pair(-1, 2), Pair(-1, -2)
        )
        return offsets.map { Position(position.row + it.first, position.col + it.second) }
            .filter { it.isOnBoard() && (board.getPiece(it) == null || board.getPiece(it)?.color != color) }
    }

    private fun getSlidingMoves(board: ChessBoard, position: Position, color: PlayerColor, directions: List<Pair<Int, Int>>): List<Position> {
        val moves = mutableListOf<Position>()
        for (dir in directions) {
            var currRow = position.row + dir.first
            var currCol = position.col + dir.second
            while (Position(currRow, currCol).isOnBoard()) {
                val target = Position(currRow, currCol)
                val piece = board.getPiece(target)
                if (piece == null) {
                    moves.add(target)
                } else {
                    if (piece.color != color) moves.add(target)
                    break
                }
                currRow += dir.first
                currCol += dir.second
            }
        }
        return moves
    }

    private fun getKingMoves(board: ChessBoard, position: Position, color: PlayerColor): List<Position> {
        val moves = mutableListOf<Position>()
        for (dr in -1..1) {
            for (dc in -1..1) {
                if (dr == 0 && dc == 0) continue
                val target = Position(position.row + dr, position.col + dc)
                if (target.isOnBoard()) {
                    val piece = board.getPiece(target)
                    if (piece == null || piece.color != color) {
                        moves.add(target)
                    }
                }
            }
        }
        return moves
    }

    fun isCheck(board: ChessBoard, color: PlayerColor): Boolean {
        val kingPos = board.pieces.entries.find { it.value.type == PieceType.KING && it.value.color == color }?.key ?: return false
        
        for (entry in board.pieces) {
            if (entry.value.color != color) {
                val moves = getPseudoLegalMoves(board, entry.key, entry.value)
                if (moves.contains(kingPos)) return true
            }
        }
        return false
    }

    private fun wouldBeInCheck(board: ChessBoard, move: Move): Boolean {
        val newPieces = board.pieces.toMutableMap()
        newPieces.remove(move.from)
        newPieces[move.to] = move.pieceMoved
        val newBoard = ChessBoard(newPieces)
        return isCheck(newBoard, move.pieceMoved.color)
    }
}
