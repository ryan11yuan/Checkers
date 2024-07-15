package Checkers;

/* 
* CheckersTest.java
*
* Purpose: Creates the checkers game and displays it on a panel
 * Description: CAS
 *
 * Name: Ryan Yuan
 * Date: December 20th, 2023
*/

public class Moves {

	// variables that represent current coordinates and which coordinates its going to
	private int fromRow;
	private int fromCol;
	private int toRow;
	private int toCol;

	// constructor to initialize them
	Moves(int fromRow, int toRow, int fromCol, int toCol) {
		
		this.fromRow = fromRow;
		this.fromCol = fromCol;
		this.toRow = toRow;
		this.toCol = toCol;
		
	}
	
	// access methods (get methods)
	
	// get the row that the piece is on or came from depending on the situation
	public int getFromRow() {
		return fromRow;
	}
	
	// get the col that the piece is on or came from depending on the situation
	public int getFromCol() {
		return fromCol;
	}
	
	// get the row that the piece is on or is going to depending on the situation
	public int getToRow() {
		return toRow;
	}
	
	// get the col that the piece is on or is going to depending on the situation
	public int getToCol() {
		return toCol;
	}
	
	
}