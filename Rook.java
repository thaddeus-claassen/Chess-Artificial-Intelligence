import java.util.ArrayList;

import processing.core.PApplet;

public class Rook extends Piece {

	Rook(int row, int col, char player) {
		super(row, col, player);
	}// end constructor
	
	public ArrayList<Move> getMoves(Piece[][] board) {
		currMove = 0;
		moves.clear();
		boolean right = true;
		boolean left = true;
		boolean up = true;
		boolean down = true;
		int r = row;
		int c = col;
		while (right || left || up || down) {
			if (right) c++;
			else if (left) c--;
			else if (up) r--;
			else r++;
			Move newMove = moveTo(board, r, c);
			if (newMove == null) {
				if (right) right = false;
				else if (left) left = false;
				else if (up) up = false;
				else down = false;
				r = row;
				c = col;
			} else {
				Piece taken = newMove.getTakenPiece();
				if (taken != null && taken.getPlayer() != player) {
					if (right) right = false;
					else if (left) left = false;
					else if (up) up = false;
					else down = false;
					r = row;
					c = col;
				}// end if
				moves.add(newMove);
			}// end if-else
		}// end while
		return moves;
	}// end getMoves()
	
	public void display(PApplet main) {
		String name = "Rook";
		displayPicture(main, name);
	}// end display()
	
	public static void staticDisplay(PApplet main, char player, int width, int height) {
		String name = "Rook";
		if (player==Chess.WHITE) {
			main.stroke(255);
			main.fill(255);
			main.ellipse(width, height, 40, 40);
			main.stroke(0);
			main.fill(0);
			main.text(name, width-15 ,height+5);
		} else {
			main.stroke(0);
			main.fill(0);
			main.ellipse(width, height, 40, 40);
			main.stroke(255);
			main.fill(255);
			main.text(name, width-15, height+5);
		}//end if-else
	}// end staticDisplay()
	
}// end class
