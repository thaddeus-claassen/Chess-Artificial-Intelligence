import java.util.ArrayList;

import processing.core.PApplet;

public abstract class Piece {
	
	int row, col, currMove;
	char player;
	boolean alive, hasMoved;
	ArrayList<Move> moves;
	
	Piece (int row, int col, char player) {
		this.row = row;
		this.col = col;
		this.player = player;
		alive = true;
		hasMoved = false;
		currMove = 0;
		moves = new ArrayList<Move>();
	}// end constructor
	
	/*
	 * returns a move if the piece can move to the square at the row and col
	 */
	public Move moveTo(Piece[][] board, int r, int c) {
		Move move = null;
		if (r >= 0 && r < 8 && c >= 0 && c < 8) {
			Piece piece = board[r][c];
			if (piece == null || (piece != null && piece.getPlayer() != player)) {
				move = new Move(row, col, r, c, this, piece, player, Chess.NO_PROMOTION, hasMoved, null, false);
			}// end if
		}// end if
		return move;
	}// end moveTo()
					
	public boolean isAlive() {
		return alive;
	}// return isAlive()

	public void isAlive(boolean alive) {
		this.alive = alive;
	}// end isDead()

	public int getRow() {
		return row;
	}// end getRow()

	public void setRow(int r) {
		row = r;
	}// end setRow()

	public int getCol() {
		return col;
	}// end getCol()

	public void setCol(int c) {
		col = c;
	}// end setCol;

	public char getPlayer() {
		return player;
	}// end getPlayer()
	
	public void setHasMoved(boolean moved) {
		hasMoved = moved;
	}// end setCanCastle()
	
	public boolean hasMoved() {
		return hasMoved;
	}// end hasMoved()
	
	public abstract ArrayList<Move> getMoves(Piece[][] board);
	
	public abstract void display(PApplet main);
	
	public void displayPicture(PApplet main, String name) {
		if (player == Chess.WHITE) {
			main.stroke(255);
			main.fill(255);
			main.ellipse(col*main.width/8+main.width/16, row*main.height/8+main.height/16, 40, 40);
			main.stroke(0);
			main.fill(0);
			main.text(name, col*main.width/8+main.width/16-15,row*main.height/8+main.height/16+5);
		} else {
			main.stroke(0);
			main.fill(0);
			main.ellipse(col*main.width/8+main.width/16, row*main.height/8+main.height/16, 40, 40);
			main.stroke(255);
			main.fill(255);
			main.text(name,col*main.width/8+main.width/16-15,row*main.height/8+main.height/16+5);
		}//end if-else
	}// end displayPicture()
	
	public String toString() {
		String string = "";
		if (player == Chess.WHITE) {
			string += "w";
		} else {
			string += "b";
		}// end if-else
		if (this instanceof Pawn) string += "p";
		else if (this instanceof Knight) string += "n";
		else if (this instanceof Bishop) string += "b";
		else if (this instanceof Rook) string += "r";
		else if (this instanceof Queen) string += "q";
		else string += "k";
		return string;
	}// end toString()
		
}// end abstract class
