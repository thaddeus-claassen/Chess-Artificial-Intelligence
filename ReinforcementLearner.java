
//public class ReinforcementLearner extends AlphaBetaPlayer {
//	
//	public static final double alpha = 0.1;
//	
//	ReinforcementLearner(Boardgames theGame, double[] weights, char player) {
//		super(theGame, weights, player);
//	}// end constructor
//	
//	public double[] updateWeightsUsingSARSA(Move[] moves, char winner) {
//		double[][] theFeatures = new double[moves.length][Chess.NUM_FEATURES];
//		for (int i=0; i<moves.length && moves[i]!=null; i++) {
//			System.arraycopy(moves[i].getFeatures(), 0, theFeatures[i], 0, Chess.NUM_FEATURES); 
//		}// end for
//		int reward;
//		if (winner == 'z') reward = 0;
//		else if (winner == game.getPlayer1()) reward = 1;
//		else reward = -1;
//		for (int j=0; j<moves.length && moves[j]!=null; j++) {
//			for (int i=0; i<Chess.FEATURESLENGTH; i++) {
//				if (j==0) weights[i] += alpha*(reward-moves[j].getValue())*theFeatures[j][i];
//				else weights[i] += alpha*(moves[j-1].getValue()-moves[j].getValue())*theFeatures[j][i];
//			}// end for
//		}// end for
//		return weights;
//	}// end updateWeights()
//	
//	public void setWeights(double[] newWeights) {
//		weights = newWeights;
//	}// end setWeights()
//	
//	public double[] getWeights() {
//		return weights;
//	}// end getWeights()
//	
//	public String doneMovesToString(Move[] doneMoves) {
//		String string = "";
//		for (int i=0; i<doneMoves.length; i++) {
//			if (doneMoves[i]==null) string += "null"+"\n";
//			else string += "not null"+"\n";
//		}// end for
//		return string;
//	}// end doneMovesToString()
//	
//	public String featuresToString(double[] theFeatures) {
//		String string = "";
//		for (int i=0; i<theFeatures.length; i++) {
//			string += i+"      "+theFeatures[i]+"\n";
//		}//end for
//		return string;
//	}// end featuresToString()
//	
//	public String weightsToString() {
//		String string = "";
//		for (int i=0; i<weights.length; i++) {
//			string += i+"   "+weights[i]+"\n";
//		}// end for
//		return string;
//	}// end weightsToString()
//	
//}// end class
