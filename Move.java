import java.util.ArrayList;

public class Move {
	
	int oldRow, oldCol, newRow, newCol;
	ArrayList<String> draw;
	double value;
	char player, promotion;
	Piece piece, takenPiece;
	Rook castle;
	Pawn  enPassant1, enPassant2;
	boolean hasMoved, enPassant;
	double[] features;
	
	Move(int oldRow, int oldCol, int newRow, int newCol, Piece piece, Piece takenPiece, char player, char promotion,
			boolean hasMoved, Rook castle, boolean enPassant) {
		this.oldRow = oldRow;
		this.oldCol = oldCol;
		this.newRow = newRow;
		this.newCol = newCol;
		this.piece = piece;
		this.takenPiece = takenPiece;
		this.player = player;
		this.promotion = promotion;
		this.hasMoved = hasMoved;
		this.castle = castle;
		this.enPassant = enPassant;
		draw = new ArrayList<String>();
		enPassant1 = null;
		enPassant2 = null;
		value = 0;
	}// end constructor
	
	public void setPiece(Piece piece) {
		this.piece = piece;
	}// end setPiece()
	
	public int getOldRow() {
		return oldRow;
	}/// end getOldRow()
	
	public int getOldCol() {
		return oldCol;
	}/// end getOldCol()
	
	public int getNewRow() {
		return newRow;
	}// end getNewRow()

	public int getNewCol() {
		return newCol;
	}// end getNewCol()

	public char getPlayer() {
		return player;
	}// end getPlayer()
	
	public Piece getPiece() {
		return piece;
	}// end getPiece()

	public Piece getTakenPiece() {
		return takenPiece;
	}// end getTakenPiece()
	
	public boolean getEnPassant() {
		return enPassant;
	}// end getEnPassant()
	
	public boolean hasMoved() {
		return hasMoved;
	}// end hasMoved()
	
	public ArrayList<String> getDrawList() {
		return draw;
	}// end getDrawArray()
	
	public void setDrawList(ArrayList<String> drawList) {
		draw.clear();
		draw.addAll(drawList);
	}// end setDrawArray()
	
	public Rook getCastle() {
		return castle;
	}// end isCastle()
	
	public char getPromotion() {
		return promotion;
	}// end getPromotion();
	
	public double getValue() {
		return value;
	}// end getValue()
	
	public void setValue(double value) {
		this.value = value;
	}// end setValue()
	
	public Pawn getEnPassant1() {
		return enPassant1;
	}// end getEnPassant1()
	
	public void setEnPassant1(Pawn enPassant1) {
		this.enPassant1 = enPassant1;
	}// end setEnPassant1()
	
	public Pawn getEnPassant2() {
		return enPassant2;
	}// end getEnPassant2()
	
	public void setEnPassant2(Pawn enPassant2) {
		this.enPassant2 = enPassant2;
	}// end setEnPassant1()
	
	public String toString() {
		String string = "";
		string += "From row " + oldRow + " col " + oldCol + "\n To row " + newRow + " col " + newCol;
		return string;
	}// end toString()
	
}// end class
