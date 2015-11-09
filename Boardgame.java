import java.util.ArrayList;


public interface Boardgame {
  
  
  /**
   * makes move specified by its row and col.
   * use this method for games where pieces do not move from another position, i.e. Tic-Tac-Toe and ConnectFour.
   */ 
  public void makeMove(Move move);
  
  /**
   *  use this if makeMove() was used
   *  undoes the move specified by row and col. 
   */
  public void undoMove(Move move); 
    
  /**
   *  @return a character indicating whose move it is i.e. X or O
   */
  public char whoseMove(); 
    
  /**
   * 
   * @return true if the game is over, false otherwise 
   *
   */
  public boolean gameOver();  

  /**
   * @return character indicating the winner
   */ 
  public char getWinner();
  
  public boolean isDraw();

  /**
   * @return
   */
  public double getBoardValue(boolean isWhite);
  
  public ArrayList<Move> createMoveList();
  
}// end class
