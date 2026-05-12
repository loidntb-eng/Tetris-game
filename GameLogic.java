// GameLogic - controls the game
// GamePanel calls update() every frame and calls the move methods when keys are pressed

class GameLogic {

	public Grid grid;
	public Piece current;
	public Piece held;
	public boolean usedHold; // can only hold once per piece

	public Piece next1;
	public Piece next2;
	public Piece next3;

	public int score;
	public int level;
	public int linesCleared;

	public boolean over;
	public boolean paused;
	public boolean started;

	long lastDrop;
	int dropDelay;

	public GameLogic() {
		grid = new Grid();
		newGame();
	}

	public void newGame() {
		grid.clear();

		score = 0;
		level = 1;
		linesCleared = 0;
		dropDelay = Constants.SPEED_LEVEL1;

		over = false;
		paused = false;
		started = true;
		usedHold = false;
		held = null;

		// make the first pieces
		next1 = new Piece();
		next2 = new Piece();
		next3 = new Piece();

		spawnPiece();

		lastDrop = System.currentTimeMillis();
	}

	// called every frame
	public void update() {
		if (!started) return;
		if (over) return;
		if (paused) return;

		long now = System.currentTimeMillis();
		if (now - lastDrop >= dropDelay) {
			lastDrop = now;
			fall();
		}
	}

	// piece falls down one row
	void fall() {
		if (grid.fits(current, current.x, current.y + 1)) {
			current.y++;
		} else {
			// cant fall anymore, lock it
			grid.stamp(current);
			int lines = grid.deleteFullRows();
			addScore(lines);
			usedHold = false;
			spawnPiece();
		}
	}

	// add score based on how many lines cleared
	void addScore(int lines) {
		linesCleared += lines;

		// simple scoring
		if (lines == 1) score += 100 * level;
		if (lines == 2) score += 300 * level;
		if (lines == 3) score += 500 * level;
		if (lines == 4) score += 800 * level; // tetris!

		// update level every 10 lines
		level = (linesCleared / 10) + 1;
		if (level > 5) level = 5;

		// set speed based on level
		if (level == 1) dropDelay = Constants.SPEED_LEVEL1;
		if (level == 2) dropDelay = Constants.SPEED_LEVEL2;
		if (level == 3) dropDelay = Constants.SPEED_LEVEL3;
		if (level == 4) dropDelay = Constants.SPEED_LEVEL4;
		if (level == 5) dropDelay = Constants.SPEED_LEVEL5;
	}

	// spawn the next piece from the queue
	void spawnPiece() {
		current = next1;
		next1 = next2;
		next2 = next3;
		next3 = new Piece();

		// center the piece at top
		current.x = Constants.COLS / 2 - current.shape[0].length / 2;
		current.y = 0;

		// if the new piece already overlaps something, game over
		if (!grid.fits(current, current.x, current.y)) {
			over = true;
		}
	}

	// --- player input ---

	public void goLeft() {
		if (!started || over || paused) return;
		if (grid.fits(current, current.x - 1, current.y)) {
			current.x--;
		}
	}

	public void goRight() {
		if (!started || over || paused) return;
		if (grid.fits(current, current.x + 1, current.y)) {
			current.x++;
		}
	}

	public void goDown() {
		if (!started || over || paused) return;
		if (grid.fits(current, current.x, current.y + 1)) {
			current.y++;
			score++;
		} else {
			grid.stamp(current);
			int lines = grid.deleteFullRows();
			addScore(lines);
			usedHold = false;
			spawnPiece();
		}
		lastDrop = System.currentTimeMillis();
	}

	public void rotate() {
		if (!started || over || paused) return;
		current.rotate();
		// if it doesnt fit after rotating, undo it
		if (!grid.fits(current, current.x, current.y)) {
			// try moving right first
			if (grid.fits(current, current.x + 1, current.y)) {
				current.x++;
			} else if (grid.fits(current, current.x - 1, current.y)) {
				current.x--;
			} else {
				// just undo the rotation
				current.rotateBack();
			}
		}
	}

	public void dropNow() {
		if (!started || over || paused) return;
		// keep moving down until it cant
		while (grid.fits(current, current.x, current.y + 1)) {
			current.y++;
			score += 2;
		}
		grid.stamp(current);
		int lines = grid.deleteFullRows();
		addScore(lines);
		usedHold = false;
		spawnPiece();
	}

	public void hold() {
		if (!started || over || paused) return;
		if (usedHold) return; // already held once this turn

		usedHold = true;

		if (held == null) {
			held = new Piece(current.type);
			spawnPiece();
		} else {
			// swap current with held
			Piece temp = held;
			held = new Piece(current.type);
			current = temp;
			current.x = Constants.COLS / 2 - current.shape[0].length / 2;
			current.y = 0;
		}
	}

	public void pause() {
		if (!started || over) return;
		paused = !paused;
		if (!paused) {
			lastDrop = System.currentTimeMillis();
		}
	}

}
