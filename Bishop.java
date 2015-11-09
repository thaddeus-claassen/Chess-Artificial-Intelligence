import java.util.ArrayList;

import processing.core.PApplet;

public class Bishop extends Piece {
	
	Bishop(int row, int col, char player) {
		super(row, col, player);
	}// end constructor

	public ArrayList<Move> getMoves(Piece[][] board) {
		currMove = 0;
		moves.clear();
		boolean upRight = true;
		boolean upLeft = true;
		boolean downRight = true;
		boolean downLeft = true;
		int r = row;
		int c = col;
		while (upRight || upLeft || downRight || downLeft) {
			if (upRight) {
				r--;
				c++;
			} else if (upLeft) {
				r--;
				c--;
			} else if (downRight) {
				r++;
				c++;
			} else {
				r++;
				c--;
			}// end if-else
			Move newMove = moveTo(board,r,c);
			if (newMove == null) {
				if (upRight) upRight = false;
				else if (upLeft) upLeft = false;
				else if (downRight) downRight = false;
				else downLeft = false;
				r = row;
				c = col;
			} else {
				Piece taken = newMove.getTakenPiece();
				if (taken != null && taken.getPlayer() != player) {
					if (upRight) upRight = false;
					else if (upLeft) upLeft = false;
					else if (downRight) downRight = false;
					else downLeft = false;
					r = row;
					c = col;
				}// end if
				moves.add(newMove);
			}// end if-else
		}// end while
		return moves;
	}// end getMoves()
	
	public void display(PApplet main) {
		String name = "Bishop";
		displayPicture(main, name);
	}// end display()
	
	public static void staticDisplay(PApplet main, char player, int width, int height) {
		String name = "Bishop";
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
