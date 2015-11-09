import java.util.ArrayList;

import processing.core.PApplet;

public class King extends Piece {
	
	Rook leftRook, rightRook;
	
	King(int row, int col, char player) {
		super(row, col, player);
	}// end constructor
	
	public ArrayList<Move> getMoves(Piece[][] board) {
		currMove = 0;
		moves.clear();
		for (int i =- 1; i <= 1; i++) {
			for (int j =- 1; j <= 1; j++) {
				if (i != 0 || j != 0) {
					Move newMove = moveTo(board, row+i, col+j);
					if (newMove != null) {
						moves.add(newMove);
					}// end if
				}// end if
			}// end for
		}// end for
		if (!hasMoved) {
			if (!leftRook.hasMoved()) {
				Move newMove = null;
				if (board[row][col-1] == null && board[row][col-2] == null && board[row][col-3] == null) {
					newMove = new Move(row, col, row, col-2, this, null, player, Chess.NO_PROMOTION, hasMoved, leftRook, false);
					if (newMove != null) {
						moves.add(newMove);
					}// end if
				}// end if
			}// end if
			if (!rightRook.hasMoved()) {
				Move newMove = null;
				if (board[row][col+1] == null && board[row][col+2] == null) {
					newMove = new Move(row, col, row, col+2, this, null, player, Chess.NO_PROMOTION, hasMoved, rightRook, false);
					if (newMove != null) {
						moves.add(newMove);
					}// end if
				}// end if
			}// end if
		}// end if
		return moves;
	}// end getMoves()
		
	public void display(PApplet main) {
		String name = "King";
		displayPicture(main, name);
	}// end display()
	
	public void setRook(Rook rook, boolean left) {
		if (left) {
			leftRook = rook;
		} else {
			rightRook = rook;
		}// end setRook()
	}// setLeftRook
	
	
}// end class
