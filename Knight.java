import java.util.ArrayList;

import processing.core.PApplet;

public class Knight extends Piece {
		
	Knight(int row, int col, char player) {
		super(row, col, player);
	}// end constructor

	public ArrayList<Move> getMoves(Piece[][] board) {
		currMove = 0;
		moves.clear();
		for (int i=-2; i<=2; i++) {
			for (int j=-2; j<=2; j++) {
				if (Math.abs(i)+Math.abs(j)==3) {
					int r = row+i;
					int c = col+j;
					Move theMove = moveTo(board,r,c);
					if (theMove != null) {
						moves.add(theMove);
					}// end if
				}// end if
			}// end for
		}// end for
		return moves;
	}// end getMoves()
	
	public void display(PApplet main) {
		String name = "Knight";
		displayPicture(main, name);
	}// end display()
	
	public static void staticDisplay(PApplet main, char player, int width, int height) {
		String name = "Knight";
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
