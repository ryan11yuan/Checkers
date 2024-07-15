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

import javax.swing.SwingUtilities;

public class CheckersTest {

	public static void main(String[] args) {

		// create a board and run it using the view class
		Board checkers = new Board();

		// this fixes the lists and stuff to appear
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new CheckersView(checkers);
			}

		});

	}
}