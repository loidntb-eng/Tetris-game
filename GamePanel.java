import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// GamePanel - Person 3
// this took me forever but it works now
// DO NOT TOUCH the drawBoard method i spent 3 hours on it

public class GamePanel extends JPanel implements KeyListener {

	GameLogic game;
	Timer t;

	public GamePanel() {
		game = new GameLogic();

		// window size = board + side panel
		setPreferredSize(new Dimension(Constants.BOARD_W + Constants.PANEL_W, Constants.BOARD_H));
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);

		// timer runs every 16ms (about 60fps)
		t = new Timer(16, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.update();
				repaint();
			}
		});
		t.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		drawBoard(g);
		drawSidePanel(g);

		if (!game.started) {
			drawStartScreen(g);
		}

		if (game.paused) {
			drawPauseScreen(g);
		}

		if (game.over) {
			drawGameOverScreen(g);
		}
	}

	void drawBoard(Graphics g) {

		// black background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Constants.BOARD_W, Constants.BOARD_H);

		// draw all the locked cells on the board
		for (int row = 0; row < Constants.ROWS; row++) {
			for (int col = 0; col < Constants.COLS; col++) {
				Color c = game.grid.board[row][col];
				if (c != null) {
					drawBlock(g, col * Constants.TILE, row * Constants.TILE, c);
				}
			}
		}

		// draw the current piece falling
		if (game.current != null && !game.over) {
			Piece p = game.current;
			for (int i = 0; i < p.shape.length; i++) {
				for (int j = 0; j < p.shape[i].length; j++) {
					if (p.shape[i][j] == 1) {
						int px = (p.x + j) * Constants.TILE;
						int py = (p.y + i) * Constants.TILE;
						if (p.y + i >= 0) {
							drawBlock(g, px, py, p.color);
						}
					}
				}
			}
		}

		// grid lines so you can see the cells
		g.setColor(new Color(50, 50, 50));
		for (int i = 0; i <= Constants.COLS; i++) {
			g.drawLine(i * Constants.TILE, 0, i * Constants.TILE, Constants.BOARD_H);
		}
		for (int i = 0; i <= Constants.ROWS; i++) {
			g.drawLine(0, i * Constants.TILE, Constants.BOARD_W, i * Constants.TILE);
		}

		// white border
		g.setColor(Color.WHITE);
		g.drawRect(0, 0, Constants.BOARD_W - 1, Constants.BOARD_H - 1);
	}

	// draws one block at pixel position px py
	void drawBlock(Graphics g, int px, int py, Color c) {
		int s = Constants.TILE; // just a shortcut so i dont have to type Constants.TILE every time
		// main fill
		g.setColor(c);
		g.fillRect(px + 1, py + 1, s - 2, s - 2);

		// lighter top and left (makes it look 3d)
		g.setColor(c.brighter());
		g.fillRect(px + 1, py + 1, s - 2, 4);
		g.fillRect(px + 1, py + 1, 4, s - 2);

		// darker bottom and right
		g.setColor(c.darker());
		g.fillRect(px + 1, py + s - 5, s - 2, 4);
		g.fillRect(px + s - 5, py + 1, 4, s - 2);
	}

	void drawSidePanel(Graphics g) {
		// gray background for side panel
		g.setColor(new Color(25, 25, 25));
		g.fillRect(Constants.BOARD_W, 0, Constants.PANEL_W, Constants.BOARD_H);

		// left border
		g.setColor(Color.WHITE);
		g.drawLine(Constants.BOARD_W, 0, Constants.BOARD_W, Constants.BOARD_H);

		int x = Constants.BOARD_W + 10;

		// SCORE
		g.setColor(Color.GRAY);
		g.setFont(new Font("Arial", Font.PLAIN, 12));
		g.drawString("SCORE", x, 30);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("" + game.score, x, 55);

		// LEVEL
		g.setColor(Color.GRAY);
		g.setFont(new Font("Arial", Font.PLAIN, 12));
		g.drawString("LEVEL", x, 90);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("" + game.level, x, 115);

		// LINES
		g.setColor(Color.GRAY);
		g.setFont(new Font("Arial", Font.PLAIN, 12));
		g.drawString("LINES", x, 150);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("" + game.linesCleared, x, 175);

		// NEXT PIECE
		g.setColor(Color.GRAY);
		g.setFont(new Font("Arial", Font.PLAIN, 12));
		g.drawString("NEXT", x, 215);

		// draw next1
		drawSmallPiece(g, game.next1, x, 225);

		// draw next2 below it
		drawSmallPiece(g, game.next2, x, 300);

		// HOLD
		g.setColor(Color.GRAY);
		g.setFont(new Font("Arial", Font.PLAIN, 12));
		g.drawString("HOLD", x, 390);

		if (game.held != null) {
			drawSmallPiece(g, game.held, x, 400);
		}

		// controls
		g.setColor(new Color(100, 100, 100));
		g.setFont(new Font("Arial", Font.PLAIN, 11));
		g.drawString("LEFT  - move left",  x, 490);
		g.drawString("RIGHT - move right", x, 505);
		g.drawString("UP    - rotate",     x, 520);
		g.drawString("DOWN  - soft drop",  x, 535);
		g.drawString("SPACE - hard drop",  x, 550);
		g.drawString("C     - hold",       x, 565);
		g.drawString("P     - pause",      x, 580);
		g.drawString("ENTER - restart",    x, 595);
	}

	// draws a small version of the piece for next/hold box
	// i made the mini blocks 18px
	void drawSmallPiece(Graphics g, Piece p, int startX, int startY) {
		if (p == null) return;
		int size = 18;
		for (int i = 0; i < p.shape.length; i++) {
			for (int j = 0; j < p.shape[i].length; j++) {
				if (p.shape[i][j] == 1) {
					int px = startX + j * size;
					int py = startY + i * size;
					g.setColor(p.color);
					g.fillRect(px + 1, py + 1, size - 2, size - 2);
					g.setColor(p.color.brighter());
					g.fillRect(px + 1, py + 1, size - 2, 3);
					g.fillRect(px + 1, py + 1, 3, size - 2);
					g.setColor(p.color.darker());
					g.fillRect(px + 1, py + size - 4, size - 2, 3);
					g.fillRect(px + size - 4, py + 1, 3, size - 2);
				}
			}
		}
	}

	void drawStartScreen(Graphics g) {
		g.setColor(new Color(0, 0, 0, 170));
		g.fillRect(0, 0, Constants.BOARD_W + Constants.PANEL_W, Constants.BOARD_H);

		g.setColor(Color.CYAN);
		g.setFont(new Font("Arial", Font.BOLD, 48));
		g.drawString("TETRIS", 70, 260);

		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.PLAIN, 14));
		g.drawString("Press ENTER to start", 100, 300);
	}

	void drawPauseScreen(Graphics g) {
		g.setColor(new Color(0, 0, 0, 150));
		g.fillRect(0, 0, Constants.BOARD_W, Constants.BOARD_H); // only dim the board not the side

		g.setColor(Color.YELLOW);
		g.setFont(new Font("Arial", Font.BOLD, 40));
		g.drawString("PAUSED", 50, 290);

		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.PLAIN, 13));
		g.drawString("Press P to continue", 70, 325);
	}

	void drawGameOverScreen(Graphics g) {
		g.setColor(new Color(0, 0, 0, 160));
		g.fillRect(0, 0, Constants.BOARD_W, Constants.BOARD_H);

		g.setColor(Color.RED);
		g.setFont(new Font("Arial", Font.BOLD, 36));
		g.drawString("GAME OVER", 30, 270);

		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.PLAIN, 14));
		g.drawString("Score: " + game.score, 90, 305);
		g.drawString("Press ENTER to play again", 30, 330);
	}

	// keyboard controls
	public void keyPressed(KeyEvent e) {
		int k = e.getKeyCode();

		if (k == KeyEvent.VK_ENTER) {
			game.newGame();
		}
		if (k == KeyEvent.VK_LEFT) {
			game.goLeft();
		}
		if (k == KeyEvent.VK_RIGHT) {
			game.goRight();
		}
		if (k == KeyEvent.VK_DOWN) {
			game.goDown();
		}
		if (k == KeyEvent.VK_UP) {
			game.rotate();
		}
		if (k == KeyEvent.VK_SPACE) {
			game.dropNow();
		}
		if (k == KeyEvent.VK_C) {
			game.hold();
		}
		if (k == KeyEvent.VK_P) {
			game.pause();
		}
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

}
