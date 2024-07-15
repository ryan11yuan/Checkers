package Checkers;

/* 
 * View.java
 *
 * Purpose: Sets up all the buttons, labels, messages, and actions
 * Description: CAS
 *
 * Name: Ryan Yuan
 * Date: December 20th, 2023
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class CheckersView extends JFrame {

	private Board board; // board of the game

	private JButton start; // the start button
	private JButton reset; // the start button
	private JButton modes; // light or dark mode

	private boolean lightOrDarkMode; // false for light mode and true for dark mode

	private URL filePath; // URL file path for music

	private Image icon; // icon for the app

	// all the messages
	private static JLabel objMessage;
	private static JLabel gameMessage;
	private static JLabel winnerMessage;
	private static JLabel redMessage;
	private static JLabel blackMessage;
	private static JLabel redPiecesMessage; 
	private static JLabel blackPiecesMessage;

	// constructor to start the game
	public CheckersView (Board board) {

		super( "Checkers Game by Ryan Yuan" );

		this.board = board; 

		// window listener
		windowHandler windowListener = new windowHandler();
		addWindowListener(windowListener);   

		setSize(695, 825); // size of whole frame
		setLayout(null); 
		getContentPane().setBackground(Color.WHITE); // background
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false); 

		// add to the panel
		add(board);

		// set up labels and buttons
		setUpLabels();
		setUpButtons();

		// icon of the application
		icon = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("icon.png"));    
		setIconImage(icon);  

		// set mode as false (default)
		lightOrDarkMode = false;

		// file path for the piano music
		filePath = this.getClass().getResource("PianoMusic.wav");
		
		// play the music once the game starts
		playMusic();

	}

	// method that plays an audiofile when given a file path
	private void playMusic() {

		// convert the URL file path to a file
		File musicPath = new File(filePath.getPath());
		
		try {
			
			// create audio and convert into clip
			AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
			Clip clip = AudioSystem.getClip();
			
			// open and start it
			clip.open(audioInput);
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);

		} 
		
		// catch any errors with the file path
		catch (Exception e) {
			
			// let the user know
			System.out.println(e);
			
		}
		
	}

	// label setup 
	private void setUpLabels() {

		// centralize
		objMessage = new JLabel("", JLabel.CENTER);
		// add font
		objMessage.setFont(new Font("Courier", Font.BOLD, 16));
		// colour
		objMessage.setForeground(Color.BLACK);
		// position and size
		objMessage.setBounds(55, 715, 550, 100);
		// add to JFrame
		add(objMessage);

		// centralize
		gameMessage = new JLabel("", JLabel.CENTER);
		// add font
		gameMessage.setFont(new Font("Courier", Font.BOLD, 16));
		// colour
		gameMessage.setForeground(Color.BLACK);
		// position and size
		gameMessage.setBounds(55, 690, 550, 100);
		// add to JFrame
		add(gameMessage);

		// centralize
		winnerMessage = new JLabel("", JLabel.CENTER);
		// add font
		winnerMessage.setFont(new Font("Courier", Font.BOLD, 16));
		// colour
		winnerMessage.setForeground(Color.BLACK);
		// position and size
		winnerMessage.setBounds(55, 690, 550, 100);
		// add to JFrame
		add(winnerMessage);

		// centralize
		redPiecesMessage = new JLabel("12", JLabel.CENTER);
		// add font
		redPiecesMessage.setFont(new Font("Courier", Font.BOLD, 25));
		// colour
		redPiecesMessage.setForeground(Color.BLACK);
		// position and size
		redPiecesMessage.setBounds(310, 640, 550, 100);
		// add to JFrame
		add(redPiecesMessage);

		// centralize
		redMessage = new JLabel("Red:", JLabel.CENTER);
		// add font
		redMessage.setFont(new Font("Courier", Font.BOLD, 17));
		// colour
		redMessage.setForeground(Color.BLACK);
		// position and size
		redMessage.setBounds(310, 600, 550, 100);
		// add to JFrame
		add(redMessage);

		// centralize
		blackPiecesMessage = new JLabel("12", JLabel.CENTER);
		// add font
		blackPiecesMessage.setFont(new Font("Courier", Font.BOLD, 25));
		// colour
		blackPiecesMessage.setForeground(Color.BLACK);
		// position and size
		blackPiecesMessage.setBounds(45, 640, 100, 100);
		// add to JFrame
		add(blackPiecesMessage);

		// centralize
		blackMessage = new JLabel("Black:", JLabel.CENTER);
		// add font
		blackMessage.setFont(new Font("Courier", Font.BOLD, 17));
		// colour
		blackMessage.setForeground(Color.BLACK);
		// position and size
		blackMessage.setBounds(45, 600, 100, 100);
		// add to JFrame
		add(blackMessage);

	}

	// set up the button
	private void setUpButtons() {

		//register event handlers
		inputHandler handler = new inputHandler();

		start = new JButton("Start"); // initialize button
		start.setFont (new Font ("Courier", Font.BOLD, 30));
		start.setBounds(185, 635, 150, 45); // position and resize the button
		start.setBackground(Color.LIGHT_GRAY);
		add(start); // add the button

		// add to action listener
		start.addActionListener( handler );

		reset = new JButton("Reset"); // initialize button
		reset.setFont (new Font ("Courier", Font.BOLD, 30));
		reset.setBounds(345, 635, 150, 45); // position and resize the button
		reset.setBackground(Color.LIGHT_GRAY);
		add(reset); // add the button

		// add to action listener
		reset.addActionListener( handler );

		modes = new JButton("Light/Dark Switch"); // initialize button
		modes.setFont (new Font ("Courier", Font.BOLD, 15));
		modes.setBounds(185, 690, 310, 30); // position and resize the button
		modes.setBackground(Color.LIGHT_GRAY);
		add(modes); // add the button

		// add to action listener
		modes.addActionListener( handler );

		gameMessage.setText("Greetings User! Click start to begin the game");
	}

	// set message of a label
	public static void setMessage(String label, String msg) {

		if(label == "winnerMessage") {
			winnerMessage.setText(msg);
		}
		if(label == "objMessage") {
			objMessage.setText(msg);
		}
		if(label == "gameMessage") {
			gameMessage.setText(msg);
		}
		if(label == "redPiecesMessage") {
			redPiecesMessage.setText(msg);
		}
		if(label == "blackPiecesMessage") {
			blackPiecesMessage.setText(msg);
		}

	}

	// handles the inputs and implements the ActionListener interface
	private class inputHandler implements ActionListener {

		// when the button is pressed this activates
		public void actionPerformed(ActionEvent event) {

			// if the start button is clicked
			if(event.getSource().equals(start)) {

				// begin the game
				board.startGame();

			}

			// if the reset button is clicked
			if(event.getSource().equals(reset)) {

				// restart the game 
				board.restartGame();

			}

			// if the modes button is clicked
			if(event.getSource().equals(modes)) {

				// if the mode is dark 
				if(lightOrDarkMode == true) {

					// switch to the light mode
					lightOrDarkMode = false;

					getContentPane().setBackground(Color.WHITE); // background colour white

					// set the text to black
					objMessage.setForeground(Color.BLACK);
					gameMessage.setForeground(Color.BLACK);
					winnerMessage.setForeground(Color.BLACK);
					redMessage.setForeground(Color.BLACK);
					blackMessage.setForeground(Color.BLACK);
					redPiecesMessage.setForeground(Color.BLACK);
					blackPiecesMessage.setForeground(Color.BLACK);

					// set the background of the button
					reset.setBackground(Color.LIGHT_GRAY);
					start.setBackground(Color.LIGHT_GRAY);
					modes.setBackground(Color.LIGHT_GRAY);

					// set the colour of the text of the button
					reset.setForeground(Color.BLACK);
					start.setForeground(Color.BLACK);
					modes.setForeground(Color.BLACK);

				}

				// if the mode is light
				else {

					// dark mode
					lightOrDarkMode = true;

					getContentPane().setBackground(Color.BLACK); // background colour to black

					// set the text colour to white
					objMessage.setForeground(Color.WHITE);
					gameMessage.setForeground(Color.WHITE);
					winnerMessage.setForeground(Color.WHITE);
					redMessage.setForeground(Color.WHITE);
					blackMessage.setForeground(Color.WHITE);
					redPiecesMessage.setForeground(Color.WHITE);
					blackPiecesMessage.setForeground(Color.WHITE);

					// set the background of the colour 
					reset.setBackground(Color.DARK_GRAY);
					start.setBackground(Color.DARK_GRAY);
					modes.setBackground(Color.DARK_GRAY);

					// set the colour of the text of the button
					reset.setForeground(Color.WHITE);
					start.setForeground(Color.WHITE);
					modes.setForeground(Color.WHITE);

				}

				// change the mode of the tiles
				board.changeMode();

			}

		}

	}

	// window handler
	private class windowHandler implements WindowListener {

		// create a frame that includes all the JOptionPane's
		JFrame frame = new JFrame();

		// if the window is opened
		public void windowOpened(WindowEvent e) {  

			// buttons that the option pan
			String[] buttons = { "Proceed", "Show Rules"};

			// creating the option pane with two difference buttons
			int greeting = JOptionPane.showOptionDialog(frame, "Greetings User! Welcome to Checkers!\nClick the \"Show Rules\" button to see the rules of Checkers and \"Proceed\" to proceed to the game.", "Greeting Message",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[0]);

			// show rules button clicked
			if(greeting == JOptionPane.NO_OPTION) {

				// message that shows the user the movement rules
				JOptionPane.showMessageDialog(frame, "Movement Rules:"
						+ "\n1. You can only move pieces diagonally to open squares.\r\n"
						+ "2. All moves should be made across the dark squares.\r\n"
						+ "3. Each piece can move only a Single Square at a time.\r\n"
						+ "4. If the piece can make it to the farthest row from its initial place, it is considered “Kinged”, and another piece is placed on top of it.\r\n"
						+ "5. The king piece can also move only one square at a time. However, as per checkers rules, a player can move backward to prevent capture."
						+ "\n\nRemoving a Piece Rules: "
						+ "\n1. To capture the piece of your opponent, you have to diagonally jumper over them using your pieces that are adjacent to it into an empty square behind the enemy piece.\r\n"
						+ "2. You can jump multiple pieces when there is a square-in-between the piece of your opponent.\r\n"
						+ "3. A regular piece is allowed to jump a king and remove it from the board.\r\n"
						+ "4. There are two styles of checkers game play when it comes to jumping. This game uses the first one which is whenever a capture can be made, it must be made."
						, "Checkers Rules", JOptionPane.INFORMATION_MESSAGE
						);
			}

		}

		// if the window is closed
		public void windowClosed(WindowEvent e) {
			
			// thank you pane
			JOptionPane.showMessageDialog(frame, "Thank you for playing!");  

			// exist the program
			System.exit(0);

		}

		// once they click the window close button
		public void windowClosing(WindowEvent e) { 

			// have to call this method in order to make windowClosed happen
			dispose();

		}

		// unimplemented methods
		public void windowIconified(WindowEvent e) { }
		public void windowDeiconified(WindowEvent e) { }
		public void windowActivated(WindowEvent e) { }
		public void windowDeactivated(WindowEvent e) { }


	}
}