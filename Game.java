import javax.swing.*;

// Person 1
// main class - runs the game

public class Game {

	public static void main(String[] args) {

		JFrame window = new JFrame("Tetris");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);

		GamePanel gp = new GamePanel();
		window.add(gp);

		window.pack(); // resize window to fit panel
		window.setLocationRelativeTo(null); // open in center of screen
		window.setVisible(true);

	}

}
