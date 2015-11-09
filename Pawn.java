import java.util.ArrayList;

import processing.core.PApplet;

public class Pawn extends Piece {
	
	public static final int NUM_PROMOTIONS = 4, MAX_NUM_MOVES_WITH_PROMOTION = 12, MAX_NUM_MOVES_WITHOUT_PROMOTION = 4;
	Piece promoted, enPassant;
	Piece[] possiblePromotions;
	
	Pawn(int row, int col, char player) {
		super(row, col, player);
		promoted = null;
		enPassant = null;
		possiblePromotions = setPossiblePromotions();
	}// end constructor
	
	private Piece[] setPossiblePromotions() {
		Piece[] possProm = new Piece[NUM_PROMOTIONS];
		possProm[0] = new Queen(-1, -1, player);
		possProm[1] = new Knight(-1, -1, player);
		possProm[2] = new Rook(-1, -1, player);
		possProm[3] = new Bishop(-1, -1, player);
		return possProm;
	}// end setPossiblePromotions()

	public ArrayList<Move> getMoves(Piece[][] board) {
		currMove = 0;
		moves.clear();
		if (promoted == null) { 
			int n;
			if (player == Chess.WHITE) {
				n = -1;
			} else  {
				n = 1;
			}// end if-else
			Move theMove = null;
			if (row+n == 0 || row+n == 7) {
				for (int i = 0; i < MAX_NUM_MOVES_WITH_PROMOTION; i++) {
					if (i == 0) theMove = forward(board, n, Chess.QUEEN);
					else if (i == 1) theMove = forward(board, n, Chess.ROOK);
					else if (i == 2) theMove = forward(board, n, Chess.KNIGHT);
					else if (i == 3) theMove = forward(board, n, Chess.BISHOP);
					else if (i == 4) theMove = attack(board, n, col+1, Chess.QUEEN);
					else if (i == 5) theMove = attack(board, n, col+1, Chess.ROOK);
					else if (i == 6) theMove = attack(board, n, col+1, Chess.BISHOP);
					else if (i == 7) theMove = attack(board, n, col+1, Chess.KNIGHT);
					else if (i == 8) theMove = attack(board, n, col-1, Chess.QUEEN);
					else if (i == 9) theMove = attack(board, n, col-1, Chess.ROOK);
					else if (i == 10) theMove = attack(board, n, col-1, Chess.KNIGHT);
					else if (i == 11) theMove = attack(board, n, col-1, Chess.BISHOP);
					if (theMove != null) {
						moves.add(theMove);
					}// end if
				}// end for
			} else {
				boolean didEnPassant = false;
				for (int i = 0; i < MAX_NUM_MOVES_WITHOUT_PROMOTION; i++) {
					if (i == 0) theMove = forward(board, n, Chess.NO_PROMOTION);
					else if (i == 1 && moves.size() == 1) theMove = jump(board, n);
					else if (i == 2) theMove = attack(board, n, col+1, Chess.NO_PROMOTION);
					else if (i == 3) theMove = attack(board, n, col-1, Chess.NO_PROMOTION);
					if (theMove != null) {
						moves.add(theMove);
					} else {
						if (i == 2 || (i == 3 && !didEnPassant)) {
							if (enPassant != null && col-enPassant.getCol() == Math.abs(1)) {
								theMove = enPassant(board, n, enPassant.getCol());
								if (theMove != null) {
									moves.add(theMove);
									didEnPassant = true;
								}// end if
							}// end if
						}// end if
					}// end if-else
				}// end for
			}// end if-else
		} else {
			ArrayList<Move> promotedMoves = promoted.getMoves(board);
			for (Move move : promotedMoves) {
				move.setPiece(this);
			}// end for
			moves.addAll(promotedMoves);
		}// end if-else
		return moves;
	}// end getMoves()
	
	public Move forward(Piece[][] board, int n, char promotion) {
		Move move = null;
		if (row+n >= 0 && row+n < 8) {
			if (board[row+n][col] == null) {
				move = new Move(row, col, row+n, col, this, null, player, promotion, hasMoved, null, false);
			}// end if
		}// end if
		return move;
	}// end forward()
	
	public Move jump(Piece[][] board, int n) {
		Move move = null;
		if (!hasMoved && row+2*n >=0 && row+2*n < 8) {
			if (board[row+2*n][col] == null) {
				move = new Move(row, col, row+2*n, col, this, null, player, Chess.NO_PROMOTION, hasMoved, null, false);
			}// end if
		}// end if
		return move;
	}// end jump()
	
	public Move attack(Piece[][] board, int n, int c, char promotion) {
		Move move = null;
		if (row+n >= 0 && row+n < 8 && c >= 0 && c < 8) {
			Piece square = board[row+n][c];
			if (square != null && square.getPlayer() != player) {
				move = new Move(row, col, row+n, c, this, square, player, promotion, hasMoved, null, false);
			}// end if
		}// end if
		return move;
	}// end attack()
	
	public Move enPassant(Piece[][] board,int n, int newCol) {
		Move move = new Move(row, col, row+n, newCol, this, enPassant, player, Chess.NO_PROMOTION, hasMoved, null, true);
		return move;
	}// end enPassant()
	
	public void setRow(int row) {
		this.row = row;
		if (promoted != null) {
			promoted.setRow(row);
		}// end if-else
	}// end setRow()
	
	public void setCol(int col) {
		this.col = col;
		if (promoted != null) {
			promoted.setCol(col);
		}// end if-else
	}// end setCol()
	
	public void display(PApplet main) {
		if (promoted == null) {
			String name = "Pawn";
			displayPicture(main, name);
		} else {
			promoted.display(main);
		}// end if-else
	}// end display()
	
	public Piece getEnPassant() {
		return enPassant;
	}// end getEnPassant()
	
	public void setEnPassant(Piece enPassant) {
		this.enPassant = enPassant;
	}// end setEnPassant()
	
	public void setPromotion(char pieceType) {
		if (pieceType == Chess.PAWN) promoted = null;
		else {
			if (pieceType == Chess.QUEEN) promoted = possiblePromotions[0];
			else if (pieceType == Chess.KNIGHT) promoted = possiblePromotions[1];
			else if (pieceType == Chess.ROOK) promoted = possiblePromotions[2];
			else promoted = possiblePromotions[3];
			promoted.setRow(row);
			promoted.setCol(col);
		}// end if-else
	}// end setPromotion()
	
}// end class
