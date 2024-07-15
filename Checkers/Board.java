package Checkers;

/* 
 * Board.java
 *
 * Purpose: Creates a board of pieces
 * Description: CAS
 *
 * Name: Ryan Yuan
 * Date: December 20th, 2023
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Board extends JPanel implements MouseListener{

	// red and black players
	private Player redPlayer;
	private Player blackPlayer;

	// current player and next player
	private Player currentPlayer; 
	private Player nextPlayer; 

	// the tiles on the board
	private Tile [][] tiles = new Tile[8][8]; 

	// the pieces on the board
	private ArrayList <Piece> pieces = new ArrayList<Piece>(); 

	// the selected row and column
	private int selectedRow = -1; 
	private int selectedCol = -1; 

	// if the game is still in progress
	private Boolean gameInProgress = false; 

	// the trigger for the dark or light mode. the default is light mode (false) and dark mode is set as true
	private Boolean mode = false;

	// array of moves that are legal to make
	private Moves[] legalMovesForPlayer;

	// constructor that setups everything
	public Board() {

		// create the two players
		redPlayer = new Player("Red");
		blackPlayer = new Player("Black");

		boardSetup(); // add all the pieces to the board
		setCurrentPlayer(); // set the next player and current player
		setBounds(40, 20, 600, 600); // where the board is on the panel and its size

		addMouseListener(this); // for mousePressed event

	}

	// sets up the board by adding all the pieces to the board
	public void boardSetup() {

		// loops through each tile of the board
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {

				// fills the tile with the coordinates (i and j) then sets the occupied or not as false
				tiles[i][j] = new Tile(i, j, false);
				// set the position
				tiles[i][j].setPosition();

				// only put the pieces on the even tiles or the white tiles
				if( (i % 2 ) == ( j % 2 ) ) {
					if(i > 4) { // add red pieces on top 3 rows
						Piece reds = new Piece(redPlayer, tiles[i][j], Color.RED, i, j);
						pieces.add(reds);
						redPlayer.pieceAdded();
					}
					if(i < 3) { // add black pieces on bottom 3 rows 
						Piece blacks = new Piece(blackPlayer, tiles[i][j], Color.BLACK, i, j);
						pieces.add(blacks);
						blackPlayer.pieceAdded();
					}
				}
			}
		}
	}

	// print the board on the panel 
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		// draw the board
		drawBoard(g);

		// draw the pieces
		drawPieces(g);

		// only show the selected tiles when the game is in progress
		if(gameInProgress == true) {
			// draw the selected tiles options
			drawSelectedTiles(g);
		}

		// draw the black border
		drawBorder(g);

		// coordinates of the board
		drawCoordinates(g);

	}

	// helper method for paint component to draw the coordinates of the tiles
	private void drawCoordinates(Graphics g) {

		// set colour to white
		if(mode == false) {
			g.setColor(Color.BLACK);
		}
		else {
			g.setColor(Color.WHITE);
		}

		// draw the letters on the top side
		g.drawString("A", 20 + 30, 15);
		g.drawString("B", 20 + 30 + 70, 15);
		g.drawString("C", 20 + 30 + 70 + 70, 15);
		g.drawString("D", 20 + 30 + 70 + 70 + 70, 15);
		g.drawString("E", 20 + 30 + 70 + 70 + 70 + 70, 15);
		g.drawString("F", 20 + 30 + 70 + 70 + 70 + 70 + 70, 15);
		g.drawString("G", 20 + 30 + 70 + 70 + 70 + 70 + 70 + 70, 15);
		g.drawString("H", 20 + 30 + 70 + 70 + 70 + 70 + 70 + 70 + 70, 15);

		// draw numbers on the left side
		g.drawString("1", 5, 20 + 35);
		g.drawString("2", 5, 20 + 35 + 70);
		g.drawString("3", 5, 20 + 35 + 70 + 70);
		g.drawString("4", 5, 20 + 35 + 70 + 70 + 70);
		g.drawString("5", 5, 20 + 35 + 70 + 70 + 70 + 70);
		g.drawString("6", 5, 20 + 35 + 70 + 70 + 70 + 70 + 70);
		g.drawString("7", 5, 20 + 35 + 70 + 70 + 70 + 70 + 70 + 70);
		g.drawString("8", 5, 20 + 35 + 70 + 70 + 70 + 70 + 70 + 70 + 70);

		// draw letters on the bottom side
		g.drawString("A", 20 + 30, 595);
		g.drawString("B", 20 + 30 + 70, 595);
		g.drawString("C", 20 + 30 + 70 + 70, 595);
		g.drawString("D", 20 + 30 + 70 + 70 + 70, 595);
		g.drawString("E", 20 + 30 + 70 + 70 + 70 + 70, 595);
		g.drawString("F", 20 + 30 + 70 + 70 + 70 + 70 + 70, 595);
		g.drawString("G", 20 + 30 + 70 + 70 + 70 + 70 + 70 + 70, 595);
		g.drawString("H", 20 + 30 + 70 + 70 + 70 + 70 + 70 + 70 + 70, 595);

		// draw numbers on the right side
		g.drawString("1", 585, 20 + 35);
		g.drawString("2", 585, 20 + 35 + 70);
		g.drawString("3", 585, 20 + 35 + 70 + 70);
		g.drawString("4", 585, 20 + 35 + 70 + 70 + 70);
		g.drawString("5", 585, 20 + 35 + 70 + 70 + 70 + 70);
		g.drawString("6", 585, 20 + 35 + 70 + 70 + 70 + 70 + 70);
		g.drawString("7", 585, 20 + 35 + 70 + 70 + 70 + 70 + 70 + 70);
		g.drawString("8", 585, 20 + 35 + 70 + 70 + 70 + 70 + 70 + 70 + 70);

	}

	// helper method for paint component that draws the black border of the board
	private void drawBorder(Graphics g) {

		// border colour
		if(mode == false) {
			// light gray colour
			g.setColor(new Color(235, 235, 235));
		}
		else {
			g.setColor(Color.DARK_GRAY);
		}
		// create the rectangle which acts as the border 20 units outside the grid 

		// loop the amount of tiles there is (30 because 20/600) where 600 is the full length and 20 is the size of each tile
		for(int i = 0; i < 30; i++) {
			g.fillRect(i*20, 0, 20, 20);
			g.fillRect(0, i*20, 20, 20);
			g.fillRect(i*20, 580, 20, 20);
			g.fillRect(580, i*20, 20, 20);
		}
	}

	// helper method for paint component that draws the outlines for legal moves and jumps
	private void drawSelectedTiles(Graphics g) {
		g.setColor(Color.BLUE); // outline colour for pieces that can be moved

		// if there are moves for the player
		if(legalMovesForPlayer != null) {

			int length = legalMovesForPlayer.length;

			// draw the possible moves for the player
			for (int i = 0; i < length; i++) {
				g.drawRect( 1 + 20 + (legalMovesForPlayer[i].getFromCol())*70, 1 + 20 + legalMovesForPlayer[i].getFromRow()*70, 70, 70);
				g.drawRect( 20 + (legalMovesForPlayer[i].getFromCol())*70, 20 + legalMovesForPlayer[i].getFromRow()*70, 70, 70);
				g.drawRect( 20 - 1 + (legalMovesForPlayer[i].getFromCol())*70, 20 - 1 + legalMovesForPlayer[i].getFromRow()*70, 70, 70);
			}
		}

		// if the row is selected
		if (selectedRow >= 0) {

			// when selected the outline colour should be blue
			g.setColor(Color.RED);
			// draw the rectangle for the selected piece
			g.drawRect(20 + selectedCol * 70, 20 + selectedRow * 70, 70, 70);
			g.drawRect(1 + 20 + selectedCol * 70, 1 + 20 + selectedRow * 70, 70, 70);
			g.drawRect(20 - 1 + selectedCol * 70, 20 - 1 + selectedRow * 70, 70, 70);

			// the legal tiles that can be moved to for the player should be outlined with green
			g.setColor(Color.GREEN);

			int length = legalMovesForPlayer.length;

			// find all the legal moves for the player based on the selected row and column
			for (int j = 0; j < length; j++) {
				if (legalMovesForPlayer[j].getFromCol() == selectedCol && legalMovesForPlayer[j].getFromRow() == selectedRow) {
					g.drawRect( 1 + 20 + legalMovesForPlayer[j].getToCol() * 70, 1 + 20 + legalMovesForPlayer[j].getToRow() * 70, 70, 70);
					g.drawRect( 20 + legalMovesForPlayer[j].getToCol() * 70, 20 + legalMovesForPlayer[j].getToRow() * 70, 70, 70);
					g.drawRect( 20 - 1 + legalMovesForPlayer[j].getToCol() * 70, 20 - 1 + legalMovesForPlayer[j].getToRow() * 70, 70, 70);
				}
			}
		}
	}

	// helper method for paint component that draws the pieces on the board
	private void drawPieces(Graphics g) {

		// print pieces
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				// check each tile if its occupied or not
				if(tiles[i][j].isOccupied() == true) {

					// create piece object for each tile that is occupied
					Piece piece = tiles[i][j].getOccupant(pieces);

					// set the colour
					g.setColor(piece.getColour()); 

					// draw pieces to panel
					g.fillOval( 20 + (piece.getPieceCol() * 70 + 15), 20 + (piece.getPieceRow() * 70) + 15, 40, 40); 

					// if piece is king
					if(piece.isKing()) { 

						g.setColor(Color.WHITE);
						// draw a "K" on the piece that became a king
						g.drawString("K", 20 + (piece.getPieceCol()*70)+30, 20 + (piece.getPieceRow()*70)+40);

					}
				}

			}
		}

	}

	// helper method for paint component that draws the tiles for the board
	private void drawBoard(Graphics g) {

		// loop through each tile
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				// even tiles get coloured white
				if( (i % 2) == ( j % 2) ) {
					if(mode == false) {
						g.setColor(Color.WHITE);
					}
					else {
						// light gray
						g.setColor(new Color(240, 240, 240));
					}
				}
				// odd tiles get coloured gray
				else {
					if(mode == false) {
						g.setColor(Color.DARK_GRAY);
					}
					else {
						g.setColor(Color.BLACK);
					}
				}
				// fill the board
				// last two numbers mean the size of each tile
				// the i and j represent where the tiles should be placed
				// since i and j are nested loops it should create a grid, placing each tile 70 units away
				g.fillRect( 20 + (i*70), 20 + (j*70), 70, 70 );
			}
		}

	}

	// start the game
	public void startGame() {

		gameInProgress = true; // set progress as in progress
		setCurrentPlayer(); // set current player and next player

		// have to set legal moves for the player at the start
		legalMovesForPlayer = currentPlayer.legalJumps(pieces, tiles, nextPlayer); 

		if(legalMovesForPlayer == null) { 
			legalMovesForPlayer = currentPlayer.legalMoves(pieces, tiles, nextPlayer);
		}

		// set automatic text
		CheckersView.setMessage("gameMessage", "Game in progress");
		CheckersView.setMessage("objMessage", "Red's move");

		repaint(); // calls the paint component

	}

	// restarts the game
	public void restartGame() {

		// reset all the tiles as not occupied
		// clear the tile occupants (set all tiles that have pieces on them as not occupied)
		tiles[0][0].clearBoard(pieces);

		gameInProgress = false; // set progress as not in progress
		currentPlayer = redPlayer;

		// create the two players
		redPlayer = new Player("Red");
		blackPlayer = new Player("Black");

		boardSetup(); // add all the pieces to the board
		setCurrentPlayer(); // set the next player and current player

		addMouseListener(this); // for mousePressed event

		// have to set legal moves for the player at the start
		legalMovesForPlayer = currentPlayer.legalJumps(pieces, tiles, nextPlayer); 

		if(legalMovesForPlayer == null) { 
			legalMovesForPlayer = currentPlayer.legalMoves(pieces, tiles, nextPlayer);
		}

		// the number of pieces
		CheckersView.setMessage("redPiecesMessage", "12");
		CheckersView.setMessage("blackPiecesMessage", "12");

		// set automatic text
		CheckersView.setMessage("gameMessage", "Game not in progresss");
		CheckersView.setMessage("objMessage", "Click the start button to start the game");

		repaint(); // calls the paint component

	}

	// change the theme from dark to light and vice versa
	public void changeMode() {

		// change the mode variable
		if(mode == false) {
			mode = true;
		}
		else {
			mode = false;
		}

		// update the board colours
		repaint();

	}

	// set the current and next player
	public void setCurrentPlayer() {

		// if red is current player
		if(redPlayer.isCurrentPlayer()) {
			currentPlayer = redPlayer;
			nextPlayer = blackPlayer;
		}

		// if black is current player
		else {
			currentPlayer = blackPlayer;
			nextPlayer = redPlayer;
		}

	}

	// declare which player wins
	public void declareWinner() {

		// this is called when the game is over so we set the game message as followed
		CheckersView.setMessage("gameMessage", "GAME OVER");

		// if red has more pieces: red wins
		if (redPlayer.getNumPieces() > blackPlayer.getNumPieces()) {
			CheckersView.setMessage("objMessage", "Red Wins!");
		}

		// if they have the same amount of pieces the game ends in a draw
		else if(redPlayer.getNumPieces() == blackPlayer.getNumPieces()) {
			CheckersView.setMessage("objMessage", "Game Ends in a Draw!");
		}

		// if black has more pieces: black wins
		else {
			CheckersView.setMessage("objMessage", "Black Wins!");
		}
	}

	// when the tile is clicked
	public void tileClicked(int row, int col) {

		// try and see if there are legal moves left for the player
		try {
			int length = legalMovesForPlayer.length;

			// loop through all possible cases
			for (int j = 0; j < length; j++) {

				// check if this move is legal or not and means they are clicking a piece and not somewhere else
				if (legalMovesForPlayer[j].getFromRow() == row && legalMovesForPlayer[j].getFromCol() == col) {

					// we know where the person clicked so we set that as the selected coordinates
					selectedRow = row; 
					selectedCol = col;

					// we know that if the if statement above is true, it selected a piece 
					CheckersView.setMessage("objMessage", currentPlayer.getPlayer() + " : Choose the square to move piece to");
					repaint(); // call the paint component to create the outlines
					return; // stop from running the other code in this method

				}
			}

			// outside the grid
			if (selectedRow < 0) {
				// nothing should change
				CheckersView.setMessage("objMessage", currentPlayer.getPlayer() + " : Choose the piece you want to move");
				return; // stop from running the other code in this method
			}

			// loop through all possible cases
			for (int i = 0; i < length; i++) {

				// this is to check if the tile clicked is the one that the piece is to move to
				// the first two conditions is checking if they have already selected a piece to move and the following two conditions is checking if they are moving to a valid position
				if (legalMovesForPlayer[i].getFromRow() == selectedRow && legalMovesForPlayer[i].getFromCol() == selectedCol && legalMovesForPlayer[i].getToRow() == row && legalMovesForPlayer[i].getToCol() == col) {

					Piece piece = tiles[selectedRow][selectedCol].getOccupant(pieces); // get an instance of the piece object in that specific tile

					tiles[selectedRow][selectedCol].setIsOccupied(false); // set this tile as not occupied because this piece moves to the clicked piece

					// check if it belongs to the current player 
					if(piece != null && currentPlayer.belongsToCurrentPlayer(piece)) {

						removePiece(selectedRow, selectedCol, row, col);// remove this specific piece

						doMove(tiles[row][col], piece); // move the piece to the clicked destination

						canJumpAgain(selectedRow, row, col); // update if this may jump again

						return;
					}

				}

			}

			// otherwise if the click is not on a valid tile
			selectedRow = -1; 
			CheckersView.setMessage("objMessage", currentPlayer.getPlayer() + " : Choose the piece you want to move");

			repaint(); // call paint method
		}

		// because the array length is null when we try to access it
		catch(NullPointerException e) {
			declareWinner(); // declare winner, because one of the two players have no more moves left
		}

	}

	// check if the piece can jump again (in order to take another piece)
	public boolean canJumpAgain(int selectedRow, int row, int col) {

		// this is to check if the piece was moved two up or down which means that it took an opposition's piece by going over it
		if((selectedRow-row) == 2 || (row-selectedRow == 2)) {

			// update the legal moves for player 
			legalMovesForPlayer = currentPlayer.getNextLegalJumps(pieces, tiles, row, col, nextPlayer);

			// if there are legal moves for the player
			if(legalMovesForPlayer != null) {
				return true; // can jump again
			}

			// otherwise it cannot jump again (means that it cannot take another piece)
			else {
				moveDone(); // set to next player's move
				return false; // cannot jump again
			}

		}

		// otherwise it cannot jump again (means that it only moved 1 tile up)
		else {
			moveDone();  // set to next player's move
			return false; // cannot jump again
		}

	}

	// the current move has finished and we check if it is game over, if not then it is the next player's move
	public void moveDone() {

		// if the game is over
		if(checkNoPieces() == true) {

			declareWinner(); // declare the winner
			gameInProgress = false; // end game 
			selectedRow = -1; // reset the selected row
			repaint(); // update the panel

		}

		// otherwise the game is not over
		else {

			currentPlayer.switchPlayer(nextPlayer); // switch the player turn to the next player

			setCurrentPlayer(); // set the current player as the next player and vice versa

			legalMovesForPlayer = currentPlayer.legalJumps(pieces, tiles, nextPlayer); // set the legal moves for player as the legal jumps (taking a piece)

			// if there are no legal jumps 
			if(legalMovesForPlayer == null) {

				// we set the legal moves to the 1 tile moves
				legalMovesForPlayer = currentPlayer.legalMoves(pieces, tiles, nextPlayer);

			}

			// reset selected row
			selectedRow = -1;

			// set message accordingly
			if (currentPlayer.getPlayer() == "Red") {
				CheckersView.setMessage("objMessage", "Red's move");
			}  

			else {
				CheckersView.setMessage("objMessage", "Black's move");
			}

		}

		repaint(); // update panel

	}

	// check if game is over
	public Boolean checkNoPieces() {

		// check if the game is over (if there are not black pieces or red pieces)
		if(redPlayer.getNumPieces() == 0 || blackPlayer.getNumPieces() == 0) {
			return true;
		}

		// game is not over
		return false;

	}

	// when a piece is jumped over, we remove the piece
	private void removePiece(int fromRow, int fromCol, int toRow, int toCol) {

		// only valid if the row difference is 2 because it must move two rows up or down to take a piece
		if((fromRow-toRow) == 2 || (toRow-fromRow == 2)) {

			// taking from down to up
			if(fromRow > toRow) {

				// checking if the piece jumped to its right or left
				// piece being removed is on the left diagonal
				if(fromCol > toCol) { 
					nextPlayer.removePiece(tiles[fromRow-1][fromCol-1], pieces); // remove left diagonal recursively
				}
				// otherwise the piece being removed is on the right diagonal
				else { 
					nextPlayer.removePiece(tiles[fromRow-1][fromCol+1], pieces); // remove right diagonal recursively
				}
			}

			// taking from up to down
			else if(fromRow < toRow) {

				// checking if the piece jumped to its right or left
				// piece being removed is on the left diagonal
				if(fromCol > toCol) {
					nextPlayer.removePiece(tiles[fromRow+1][fromCol-1], pieces); // remove left diagonal recursively
				} 
				else {
					nextPlayer.removePiece(tiles[fromRow+1][fromCol+1], pieces); // remove right diagonal recursively
				}
			}

		}

	}

	// move the piece 
	public void doMove(Tile tile, Piece piece) {

		// number of pieces that player has
		CheckersView.setMessage("redPiecesMessage", "" + redPlayer.getNumPieces());
		CheckersView.setMessage("blackPiecesMessage", "" + blackPlayer.getNumPieces());

		currentPlayer.movePiece(tile, piece); // move the piece

		piece.makeKing(currentPlayer.getPlayer()); // check if it is on the last row on the opposing side and make it a king if true

	}

	// when the mouse is pressed
	public void mousePressed(MouseEvent e) {

		// when the game is in progress
		if(gameInProgress == true) {

			// get the coordinates that they clicked (divided by 70 because the tiles are 70 in size)
			// if the coordinate is (250, 220) that means they are on tile (6, 5) because 250/70 is 6 rounded and 220/70 is 5 rounded 
			// -20 because of the edge/padding from the coordinates drawn on the sides of the board
			int col = (e.getX() - 20) / 70; 
			int row = (e.getY() - 20) / 70;

			// checking if the clicked tile is valid
			if (col >= 0 && col < 8 && row >= 0 && row < 8) {
				tileClicked(row, col); // indicates that they clicked that tile
			} 

		}
	}

	// unimplemented methods
	public void mouseClicked(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
	public void mouseReleased(MouseEvent e) { }

}
