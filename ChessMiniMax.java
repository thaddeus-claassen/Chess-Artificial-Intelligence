import java.util.ArrayList;
import java.util.Iterator;

public class ChessMiniMax extends MiniMax {
	
	ChessMiniMax(Boardgame theGame) {
		super(theGame);
	}// end constructor

	public double checkMate(int max) {
		Move bestMove = alphaBeta(-1, 1, max);
		return bestMove.getValue();
	}// end checkMate()
	
	public ArrayList<Move> removeIllegalMoves(ArrayList<Move> moves) {
		Iterator<Move> moveIterator = moves.iterator();
		while (moveIterator.hasNext()) {
			Move theMove = moveIterator.next();
			theGame.makeMove(theMove);
			double value = checkMate(1);
			if (value == 1) {
				moveIterator.remove();
			}// end if
			theGame.undoMove(theMove);
		}// end while
		return moves;
	}// end removeIllegalMoves()
	
}// end class
