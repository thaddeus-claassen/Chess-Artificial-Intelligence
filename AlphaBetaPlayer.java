
public class AlphaBetaPlayer {

	MiniMax miniMax;
	Boardgame game;
	double[] weights, features;
	
	AlphaBetaPlayer(Boardgame game, double[] weights, char player) {
		miniMax = new MiniMax(player, game);
		this.game = game;
		this.weights = weights;
	}// end constructor
	
	public void setFeatures(double[] theFeatures) {
		features = theFeatures;
	}// end setFeatures()
	
	public double getValue() {
		double value = 0;
		for (int i=0; i<features.length; i++) {
			value += (features[i]*weights[i]);
		}// end for
		return value;
	}// end getValue()
	
	public Move getMove(char player) {
		miniMax.setPlayer(player);
		Move bestMove = miniMax.alphaBeta(-1, 1, 0);
		return bestMove;
	}// end getMove()

}// end class
