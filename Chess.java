import java.util.ArrayList;

public class Chess implements Boardgame {
	
	
	///////////// Castling does not check for if King is in check or if king passes through a square in check
	///////////// EnPassant did not work
		
	public static final char KING = 'k', QUEEN = 'q', ROOK = 'r', KNIGHT = 'n', BISHOP = 'b', 
			PAWN = 'p', WHITE = 'x', BLACK = 'y', IS_DRAW = 'z', NO_PROMOTION = 'p';
	
	public static final int NUM_PIECES = 32, NUM_ROWS = 8, NUM_COLS = 8, 
			INDEX_OF_WHITE_KING = 28, INDEX_OF_BLACK_KING = 4;

	Piece[][] board;
	Piece[] pieces;
	String p1, p2;
	boolean whitesTurn, gameOver;
	char winner;
	ArrayList<String> draw;
	double[] weights;
	ChessMiniMax alphaBeta;

	Chess(String player1, String player2) {
		p1 = player1;
		p2 = player2;
		pieces = new Piece[NUM_PIECES];
		board = new Piece[NUM_ROWS][NUM_COLS];
		draw = new ArrayList<String>();
		initializePieces();
		initializeRooksInKings();
		restartGame();
	}// end constructor
	
	public void restartGame() {
		gameOver = false;
		whitesTurn = true;
		alphaBeta = new ChessMiniMax(this);
		initializeBoard();
		draw.clear();
	}// end initializeGame()
	
	public void initializePieces() {
		pieces[0] = new Rook(0, 0, BLACK);
		pieces[1] = new Knight(0, 1, BLACK);
		pieces[2] = new Bishop(0, 2, BLACK);
		pieces[3] = new Queen(0, 3, BLACK);
		pieces[4] = new King(0, 4, BLACK);
		pieces[5] = new Bishop(0, 5, BLACK);
		pieces[6] = new Knight(0, 6, BLACK);
		pieces[7] = new Rook(0, 7, BLACK);
		pieces[8] = new Pawn(1, 0, BLACK);
		pieces[9] = new Pawn(1, 1, BLACK);
		pieces[10] = new Pawn(1, 2, BLACK);
		pieces[11] = new Pawn(1, 3, BLACK);
		pieces[12] = new Pawn(1, 4, BLACK);
		pieces[13] = new Pawn(1, 5, BLACK);
		pieces[14] = new Pawn(1, 6, BLACK);
		pieces[15] = new Pawn(1, 7, BLACK);
		pieces[16] = new Pawn(6, 0, WHITE);
		pieces[17] = new Pawn(6, 1, WHITE);
		pieces[18] = new Pawn(6, 2, WHITE);
		pieces[19] = new Pawn(6, 3, WHITE);
		pieces[20] = new Pawn(6, 4, WHITE);
		pieces[21] = new Pawn(6, 5, WHITE);
		pieces[22] = new Pawn(6, 6, WHITE);
		pieces[23] = new Pawn(6, 7, WHITE);
		pieces[24] = new Rook(7, 0, WHITE);
		pieces[25] = new Knight(7, 1, WHITE);
		pieces[26] = new Bishop(7, 2, WHITE);
		pieces[27] = new Queen(7, 3, WHITE);
		pieces[28] = new King(7, 4, WHITE);
		pieces[29] = new Bishop(7, 5, WHITE);
		pieces[30] = new Knight(7, 6, WHITE);
		pieces[31] = new Rook(7, 7, WHITE);
	}// end initializePieces
	
	public void initializeRooksInKings() {
		((King) pieces[INDEX_OF_BLACK_KING]).setRook((Rook)pieces[0], true);
		((King) pieces[INDEX_OF_BLACK_KING]).setRook((Rook)pieces[7], false);
		((King) pieces[INDEX_OF_WHITE_KING]).setRook((Rook)pieces[24], true);
		((King) pieces[INDEX_OF_WHITE_KING]).setRook((Rook)pieces[31], false);
	}// end initializeRooksInKings()
		
	public void initializeBoard() {
		for (int i = 0; i < pieces.length; i++) {
			Piece p = pieces[i];
			board[p.getRow()][p.getCol()] = p;
		}// end for
	}// end initializeArray()
	
	public ArrayList<Move> createMoveList() {
		ArrayList<Move> moves = new ArrayList<Move>();
		int init;
		if (whitesTurn) init = 16;
		else init = 0;
		for (int i = init; i < init+16; i++) {
			Piece thePiece = pieces[i];
			if (thePiece.isAlive()) {
				ArrayList<Move> theMoves = thePiece.getMoves(board);
				moves.addAll(theMoves);
			}// end if
		}// end for
		return moves;
	}// end createMoveList()
	
	public void humanMove(Piece piece, int row, int col, char promotion) {
		if ((whitesTurn && piece.getPlayer() == WHITE) || (!whitesTurn && piece.getPlayer() == BLACK)) {
			ArrayList<Move> moves = alphaBeta.removeIllegalMoves(piece.getMoves(board));
			for (Move move : moves) {
				if (move.getPiece().equals(piece) && move.getNewRow() == row && move.getNewCol() == col) {
					if (move.getPromotion() == promotion) {
						makeMove(move);
						Piece taken = move.getTakenPiece();
						if (taken == null) System.out.println("Taken is null");
						else System.out.println("Taken is " + taken.toString());
						break;
					}// end if
				}// end if
			}// end for
			moves.clear();
			gameOver = (gameWon() || isDraw());
			System.out.println(toString());
		}// end if
	}// end humanMove()
	
	public void computersGame() {
		System.out.println(toString());
		while (!gameOver()) {
			Move bestMove = alphaBeta.alphaBeta(-1, 1, 4);
			makeMove(bestMove);
			System.out.println(toString());
		}// end while
		System.out.println(getWinner());
	}// end computersGame()
	
	public void makeMove(Move move) {
		whitesTurn = !whitesTurn;
		Piece piece = move.getPiece();
		Piece taken = move.getTakenPiece();
		int oldRow = piece.getRow();
		int oldCol = piece.getCol();
		int newRow = move.getNewRow();
		int newCol = move.getNewCol();
		piece.setRow(newRow);
		piece.setCol(newCol);
		board[newRow][newCol] = piece;
		board[oldRow][oldCol] = null;
		piece.setHasMoved(true);
		if (taken != null) {
			taken.isAlive(false);
		}// end if
		if (piece instanceof King) {
			if (move.getCastle() != null) {
				handleCastle(move);
				draw.clear();
			}// end if
		} else if (piece instanceof Pawn) {
			if (((Pawn)piece).promoted == null) { 
				if (move.getPromotion() != PAWN) {
					((Pawn) piece).setPromotion(move.getPromotion());
				} else if (Math.abs(newRow - oldRow) == 2) {
					///////This allows for pieces to enPassant next turn
					clearEnPassant(move);
					if (newCol+1 < 8) {
						Piece enPassant1 = board[newRow][newCol+1];
						if (enPassant1 != null && enPassant1.player != move.getPlayer() && enPassant1 instanceof Pawn) {
//							System.out.println("enPassant1 is at " + newRow + " " + (newCol+1));
							((Pawn) enPassant1).setEnPassant(piece);
//							System.out.println(toString());
						}// end if
					}// end if
					if (newCol-1 >= 0) {
						Piece enPassant2 = board[newRow][newCol-1];
						if (enPassant2 != null && enPassant2.player != move.getPlayer() && enPassant2 instanceof Pawn) {
//							System.out.println("enPassant2 is at " + newRow + " " + (newCol-1));
							((Pawn) enPassant2).setEnPassant(piece);
//							System.out.println(toString());
						}// end if
					}// end if
				} else if (move.getEnPassant()) {
					board[taken.getRow()][taken.getCol()] = null;
				}// end if
			}// end if
		}// end if
		move.setDrawList(draw);
		if (taken != null || (piece instanceof Pawn && ((Pawn) piece).promoted == null) || (piece instanceof King && !move.hasMoved())) {
			draw.clear();
		} else {
			draw.add(toString());
		}// end if-else
	}// end makeMove()
	
	public void undoMove(Move move) {
		gameOver = false;
		whitesTurn = !whitesTurn;
		draw = move.getDrawList();		
		Piece piece = move.getPiece();
		Piece taken = move.getTakenPiece();
		int oldRow = move.getOldRow();
		int oldCol = move.getOldCol();
		int newRow = piece.getRow();
		int newCol = piece.getCol();
		board[newRow][newCol] = taken;
		board[oldRow][oldCol] = piece;
		piece.setRow(oldRow);
		piece.setCol(oldCol);
		if (taken != null) {
			taken.isAlive(true);
		}// end if
		if (!move.hasMoved()) {
			piece.setHasMoved(false);
		}// end if
		unclearEnPassant(move);
		if (piece instanceof King) {
			if (move.getCastle() != null) {
				undoCastle(move);
			}// end if
		} else if (piece instanceof Pawn) {
			if (((Pawn)piece).promoted == null) {
				if (Math.abs(newRow - oldRow) == 2) {
					if (newCol+1 < 8) {
						Piece enPassant1 = board[newRow][newCol+1];
						if (enPassant1 != null && enPassant1.getPlayer() != move.getPlayer() && enPassant1 instanceof Pawn) {
							((Pawn) enPassant1).setEnPassant(null);
						}// end if
					}// end if
					if (newCol-1 >= 0) {
						Piece enPassant2 = board[newRow][newCol-1];
						if (enPassant2 != null && enPassant2.getPlayer() != move.getPlayer() && enPassant2 instanceof Pawn) {
							((Pawn) enPassant2).setEnPassant(null);
						}// end if
					}// end if
				} else if (move.getEnPassant()) {
					board[taken.getRow()][taken.getCol()] = taken;
					board[newRow][newCol] = null;
				}// end if
			} else {
				if (move.getPromotion() != PAWN) {
					((Pawn) piece).setPromotion(Chess.PAWN);
				}// end if
			}// end if-else
		}// end if
	}// end undoMove()
	
	public void clearEnPassant(Move move) {
		for (int i = 8; i < 24; i++) {
			Pawn pawn = (Pawn) pieces[i];
			if (pawn.isAlive()) {
				if (pawn.enPassant != null) {
					pawn.enPassant = null;
					if (move.getEnPassant1() == null) {
						move.setEnPassant1(pawn);
					} else {
						move.setEnPassant2(pawn);
						break;
					}// end if-else
				}// end if
			}// end if
		}// end for
	}// end clear
	
	public void unclearEnPassant(Move move) {
		Pawn enPassant1 = move.getEnPassant1();
		Pawn enPassant2 = move.getEnPassant2();
		if (enPassant1 != null) {
			enPassant1.setEnPassant(move.getPiece());
		}// end if
		if (enPassant2 != null) {
			enPassant2.setEnPassant(move.getPiece());
		}// end if
	}// end unclearEnPassant()
	
	public boolean gameOver() {
		boolean gameIsOver;
		if (!pieces[INDEX_OF_WHITE_KING].isAlive() || !pieces[INDEX_OF_BLACK_KING].isAlive()) {
			gameIsOver = true;
		} else {
			gameIsOver = isDraw();
		}// end if-else
		return gameIsOver;
	}// end gameOver()
	
	public boolean gameWon() {
		boolean gameWon = false;
		if (alphaBeta.checkMate(2) == -1) {
			gameWon = true;
		}// end if
		return gameWon;
	}// end gameWon()
	
	public boolean isDraw() {
		boolean isDraw = false;
		if (draw.size() > 49) isDraw = true;
		else if (threeFoldRepitition()) isDraw = true;
		else if (endGamePositions()) isDraw = true;
		return isDraw;
	}// end isDraw()
	
	public boolean threeFoldRepitition() {
		boolean isDraw = false;
		if (draw.size() >= 2) {
			int same = 0;
			String finalString = draw.get(draw.size()-1);
			for (String newString : draw) {
				if (newString.equals(finalString)) {
					same++;
					if (same == 3) isDraw = true;
				}// end if				
			}// end for
		}// end if
		return isDraw;
	}// end threeFoldRepitition()
	
	public String drawToString() {
		String string = "";
		for (String newString : draw) {
			if (newString == null) {
				string += "piece is null" + "\n";
			} else {
				string += newString + "\n";
			}// end if-else
		}// end for
		return string;
	}// end drawToString()
	
	public Piece getPiece(int index) {
		return pieces[index];
	}// end getPiece()
	
	public void handleCastle(Move move) {
		Rook rook = move.getCastle();
		int col;
		if (rook.getCol() == 0) {
			col = 3;
		} else {
			col = 5;
		}// end if-else
		board[rook.getRow()][col] = rook;
		board[rook.getRow()][rook.getCol()] = null;
		rook.setCol(col);
		rook.setHasMoved(true);
	}// end handleCastle()
	
	public void undoCastle(Move move) {
		Rook rook = move.getCastle();
		int col;
		if (rook.getCol() == 3) {
			col = 0;
		} else {
			col = 7;
		}// end if-else
		board[rook.getRow()][col] = rook;
		board[rook.getRow()][rook.getCol()] = null;
		rook.setCol(col);
		rook.setHasMoved(false);
	}// end undoCastle()
	
	public boolean endGamePositions() {
		boolean isDraw = true;
		int[] pieceCounter = new int[10];
		for (int i=0; i<8; i++) {
			for (int j=0; j<8; j++) {
				Piece p = board[i][j];
				if (p!=null && p.isAlive()) {
					if (p.getPlayer()==WHITE) {
						if (p instanceof Pawn) pieceCounter[8]++;
						else if (p instanceof Queen) pieceCounter[6]++;
						else if (p instanceof Rook) pieceCounter[4]++;
						else if (p instanceof Bishop) pieceCounter[2]++;
						else if (p instanceof Knight) pieceCounter[0]++;
					} else {
						if (p instanceof Pawn) pieceCounter[9]++;
						else if (p instanceof Queen) pieceCounter[7]++;
						else if (p instanceof Rook) pieceCounter[5]++;
						else if (p instanceof Bishop) pieceCounter[3]++;
						else if (p instanceof Knight) pieceCounter[1]++;
					}// end if-else
				}// end if
			}// end for
		}// end for
		if (pieceCounter[9]>0) isDraw = false;
		else if (pieceCounter[8]>0) isDraw = false;
		else if (pieceCounter[7]>0) isDraw = false;
		else if (pieceCounter[6]>0) isDraw = false;
		else if (pieceCounter[5]>0) isDraw = false;
		else if (pieceCounter[4]>0) isDraw = false;
		else if (pieceCounter[3]>=2) isDraw = false;
		else if (pieceCounter[2]>=2) isDraw = false;
		else if (pieceCounter[1]>0 && pieceCounter[3]>0) isDraw = false;
		else if (pieceCounter[2]>0 && pieceCounter[0]>0) isDraw = false;
		return isDraw;
	}// end endGamePositions()

	public char whoseMove() {
		char whoseMove;
		if (whitesTurn) whoseMove = WHITE;
		else whoseMove = BLACK;
		return whoseMove;
	}// end whoseMove()

	public char getWinner() {
		if (isDraw()) winner = 'z';
		else if (whitesTurn) winner = BLACK;
		else winner = WHITE;
		return winner;
	}// end getWinner()
	
	public boolean getGameOver() {
		return gameOver;
	}// end gameIsOver()
	
	public char getPlayer1() {
		return WHITE;
	}// end getPlayer1()
	
	public char getPlayer2() {
		return BLACK;
	}// end getPlayer2()
	
	public String toString() {
		String string = "";
		
		////toString() part which says if the black king can castle with the left rook and right rook.

		King bKing = (King) pieces[INDEX_OF_BLACK_KING];
		if (bKing != null && bKing instanceof King && !bKing.hasMoved()) {
			if (!bKing.leftRook.hasMoved()) {
				string += "T ";
			} else {
				string += "F ";
			}// end if
			if (!bKing.rightRook.hasMoved()) {
				string += "T ";
			} else {
				string += "F ";
			}// end if-else
		} else {
			string += "F F ";
		}// end if-else
		
		int numEnPassant = 0;
		for (int i = 8; i < 16; i++) {
			Pawn pawn = (Pawn) pieces[i];
			if (pawn.isAlive() && pawn.getEnPassant() != null) {
				string += "E";
				numEnPassant++;
				if (numEnPassant == 2) {
					break;
				}// end if
			}// end if
		}// end for
		string += "\n";
		
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				Piece piece = board[row][col];
				if (piece == null) string += "--" + " ";
				else {
					if (piece.getPlayer() == WHITE) {
						if (piece instanceof Pawn) {
							if (((Pawn)piece).promoted == null) string += "wp" + " ";
							else if (((Pawn)piece).promoted instanceof Queen) string += "wq" + " ";
							else if (((Pawn)piece).promoted instanceof Rook) string += "wr" + " ";
							else if (((Pawn)piece).promoted instanceof Knight) string += "wn" + " ";
							else if (((Pawn)piece).promoted instanceof Bishop) string += "wb" + " ";
						}// end if
						else string += piece.toString() + " ";
					} else {
						if (piece instanceof Pawn) {
							if (((Pawn)piece).promoted == null) string += "bp" + " ";
							else if (((Pawn)piece).promoted instanceof Queen) string += "bq" + " ";
							else if (((Pawn)piece).promoted instanceof Rook) string += "br" + " ";
							else if (((Pawn)piece).promoted instanceof Knight) string += "bn" + " ";
							else if (((Pawn)piece).promoted instanceof Bishop) string += "bb" + " ";
						}// end if
						else string += piece.toString() + " ";
					}// end if-else
				}// end if-else
			}//end for
			string += "\n";
		}// end for
		
		////toString() part which says if the white king can castle with the left rook and right rook.
		King wKing = (King) pieces[INDEX_OF_WHITE_KING];
		if (wKing != null && wKing instanceof King && !wKing.hasMoved()) {
			if (!wKing.leftRook.hasMoved()) {
				string += "T ";
			} else {
				string += "F ";
			}// end if
			if (!wKing.rightRook.hasMoved()) {
				string += "T ";
			} else {
				string += "F ";
			}// end if-else
		} else {
			string += "F F ";
		}// end if-else
		
		numEnPassant = 0;
		for (int i = 8; i < 16; i++) {
			Pawn pawn = (Pawn) pieces[i];
			if (pawn.isAlive() && pawn.getEnPassant() != null) {
				string += "E";
				numEnPassant++;
				if (numEnPassant == 2) {
					break;
				}// end if
			}// end if
		}// end for
		string += "\n";
		
		return string;
	}// end toString()

	public double getBoardValue(boolean isWhite) {
		return 0;
	}// end getBoardValue()
	
}// end class
