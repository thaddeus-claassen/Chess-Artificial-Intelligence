import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import processing.core.PApplet;

public class main extends PApplet {
		
	Chess chess;
	Piece piece;
	String thisPlayer;
	int row,col;
	boolean computerPlayers, selectPromotion;
	File scanFile;
	
	public void setup() {		
		size(600,600);

		piece = null;
		chess = new Chess("Player1", "Player2");
		row = -1;
		col = -1;
		scanFile = new File("features6");
		drawBoard();
		drawPieces();
		computerPlayers = false;
		selectPromotion = false;
		
		if (computerPlayers) {
			chess.computersGame();
		}// end if
	}// end setup()
	
	public void draw(){}
	
	public void drawBoard() {
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				if (r == row && c == col) {
					stroke(0);
					fill(255,255,0);
					rect(c*width/8, r*height/8, width/8, height/8);
				} else {
					if ((r+c)%2 == 0) fill(174, 124, 16);
					else fill(242, 228, 208);
					stroke(0);
					rect(c*width/8, r*height/8, width/8, height/8);
				}// end if-else
			}// end for
		}// end for
		line(0, height-1, width, height-1);
	}// end drawBoard()
	
	public void drawPieces() {
		for (int i = 0 ; i < chess.pieces.length; i++) {
			Piece thePiece = chess.getPiece(i);
			if (thePiece.isAlive()) {
				thePiece.display(this);
			}// end if
		}// end for
	}// end drawPieces()
	
	public void mousePressed() {
		if (!chess.getGameOver()) {
			char promotion = Chess.NO_PROMOTION;
			if (selectPromotion) {
				promotion = selectPromotion(mouseX, mouseY);
			} else {
				row = 8*mouseY/height;
				col = 8*mouseX/width;
				if (piece == null) {
					for (int index = 0 ; index < chess.pieces.length; index++) {
						Piece p = chess.getPiece(index);
						if (p.isAlive()) {
							if (p.getRow() == row && p.getCol() == col) {
								piece = p;
								break;
							}// end if
						}// end if
					}// end for
				} else {
					if (piece instanceof Pawn && ((Pawn)piece).promoted == null &&
							((piece.getPlayer() == Chess.WHITE && row == 0) || piece.getPlayer() == Chess.BLACK && row == 7)) {
						selectPromotion = true;
					} else {
						chess.humanMove(piece, row, col, promotion);
						piece = null;
					}// end if-else
				}// end if-else
				if (piece == null) {
					row = -1;
					col = -1;
				}// end if
			}// end if
		}// end if
		drawBoard();
		drawPieces();
		if (selectPromotion) {
			displayPromotionOptions(piece.getPlayer());
		}// end if
	}// end mousePressed()
	
	public char selectPromotion(int xValue, int yValue) {
		char promotion = Chess.NO_PROMOTION;
		if (xValue > width/2-100 && xValue < width/2+100) {
			if (yValue > height/2-100 && yValue < height/2+100) {
				if (xValue < width/2) {
					if (yValue < height/2) {
						promotion = Chess.QUEEN;
					}  else {
						promotion = Chess.KNIGHT;
					}// end if-else
				} else {
					if (yValue < height/2) {
						promotion = Chess.ROOK;
					}  else {
						promotion = Chess.BISHOP;
					}// end if-else
				}// end if-else
				chess.humanMove(piece, row, col, promotion);
				piece = null;
				selectPromotion = false;
			}// end if
		}// end if
		return promotion;
	}// end selectPromotion()
	
	public void displayPromotionOptions(char player) {
		stroke(0);
		fill(0, 255, 0);
		rect(width/2 - 100, height/2 - 100, 100, 100);
		rect(width/2, height/2 - 100, 100, 100);
		rect(width/2 - 100, height/2, 100, 100);
		rect(width/2, height/2, 100, 100);
		Queen.staticDisplay(this, player, width/2 - 50, height/2 - 50);
		Rook.staticDisplay(this, player, width/2 + 50, height/2 - 50);
		Knight.staticDisplay(this, player, width/2 - 50, height/2 + 50);	
		Bishop.staticDisplay(this, player, width/2 + 50, height/2 + 50);
	}// end displayPromotionOptions()
		
	public void createFile(File inputFile) {
		Scanner scan = null;
		try {
			scan = new Scanner(inputFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}// end try-catch
		int i = 0;
		while (scan.hasNext()) {
			i++;
		}// end while
		scan.close();
	}// end inputFile()
	
	public void printFile(File inputFile, double[] weights) {
		PrintStream stream = null;
		try {
			stream = new PrintStream(inputFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}// end try-catch
		for (int i=0; i<weights.length; i++) {
			stream.println(weights[i]);
		}// end for
		stream.flush();
	}// end printFile()
	
}// end main
