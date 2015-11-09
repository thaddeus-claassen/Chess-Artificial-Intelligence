import java.util.ArrayList;


public class MiniMax {

	Boardgame theGame;

	MiniMax(Boardgame theGame) {
		this.theGame = theGame;
	}// end constructor

	public Move alphaBeta(double alpha, double beta, int maxDepth) {
		Move bestMove = null;
		if (theGame.gameOver()) {
			if (theGame.isDraw()) alpha = 0;
			else alpha = -1;
		} else {
//			ArrayList<Move> moveList = new ArrayList<Move>();
//			moveList.addAll(theGame.createMoveList());
			ArrayList<Move> moveList = theGame.createMoveList();
			for (Move theMove : moveList) {
				theGame.makeMove(theMove);
				double newValue = Math.max(alpha, beta(alpha, beta, maxDepth-1));
				theGame.undoMove(theMove);
				if (bestMove == null || alpha < newValue) {
					bestMove = theMove;
					alpha = newValue;
				}// end if
				if (beta <= alpha) {
					break;
				}// end if
			}// end for
			bestMove.setValue(alpha);
		}// end if-else
		return bestMove;
	}// end alphaBeta()

	public double alpha(double alpha, double beta, int depthRemaining) {
		if (theGame.gameOver()) {
			if (theGame.isDraw()) alpha = 0;
			else alpha = -1;
		} else {
			if (depthRemaining > 0) {
//				ArrayList<Move> moveList = new ArrayList<Move>();
//				moveList.addAll(theGame.createMoveList());
				ArrayList<Move> moveList = theGame.createMoveList();
				for (Move theMove : moveList) {
					theGame.makeMove(theMove);
					alpha = Math.max(alpha, beta(alpha, beta, depthRemaining-1));
					theGame.undoMove(theMove);
					if (beta <= alpha) {
						break;
					}// end if
				}// end for
			} else {
				alpha = theGame.getBoardValue(true);
			}// end if-else
		}// end if-else
		return alpha;
	}//end alpha()

	public double beta(double alpha, double beta, int depthRemaining) {
		if (theGame.gameOver()) {
			if (theGame.isDraw()) beta = 0;
			else beta = 1;
		} else {
			if (depthRemaining > 0) {
				ArrayList<Move> moveList = theGame.createMoveList();
				for (Move theMove : moveList) {
					theGame.makeMove(theMove);
					beta = Math.min(beta, alpha(alpha, beta, depthRemaining-1));
					theGame.undoMove(theMove);
					if (beta <= alpha) {
						break;
					}// end if
				}// end for
			} else {
				beta = theGame.getBoardValue(false); 
			}// end if-else
		}// end if-else
		return beta;
	}// end beta()
	
}// end class
