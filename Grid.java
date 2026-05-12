import java.awt.Color;

// Person 1
// this class stores the board and checks collisions
// the board is just a 2d array of colors
// null means empty

public class Grid {

	// the board - Color means a block is there, null means empty
	public Color[][] board;

	public Grid() {
		board = new Color[Constants.ROWS][Constants.COLS];
		// java sets everything to null automatically
	}

	// check if a piece fits at position x, y
	// returns true if it fits, false if it doesnt
	public boolean fits(Piece p, int x, int y) {

		for (int i = 0; i < p.shape.length; i++) {
			for (int j = 0; j < p.shape[i].length; j++) {

				if (p.shape[i][j] == 0) {
					continue; // empty cell, skip it
				}

				int newX = x + j;
				int newY = y + i;

				// check left wall
				if (newX < 0) {
					return false;
				}

				// check right wall
				if (newX >= Constants.COLS) {
					return false;
				}

				// check bottom
				if (newY >= Constants.ROWS) {
					return false;
				}

				// check if another piece is already there
				// (only check if inside the board)
				if (newY >= 0) {
					if (board[newY][newX] != null) {
						return false;
					}
				}

			}
		}

		return true; // no problems found, it fits
	}

	// stamp the piece onto the board permanently
	public void stamp(Piece p) {
		for (int i = 0; i < p.shape.length; i++) {
			for (int j = 0; j < p.shape[i].length; j++) {
				if (p.shape[i][j] == 1) {
					int row = p.y + i;
					int col = p.x + j;
					if (row >= 0 && row < Constants.ROWS && col >= 0 && col < Constants.COLS) {
						board[row][col] = p.color;
					}
				}
			}
		}
	}

	// delete full rows and move everything down
	// returns how many rows were deleted
	public int deleteFullRows() {
		int deleted = 0;

		for (int r = Constants.ROWS - 1; r >= 0; r--) {

			// check if this row is full
			boolean full = true;
			for (int c = 0; c < Constants.COLS; c++) {
				if (board[r][c] == null) {
					full = false;
					break;
				}
			}

			if (full) {
				// move all rows above down by 1
				for (int row = r; row > 0; row--) {
					for (int col = 0; col < Constants.COLS; col++) {
						board[row][col] = board[row - 1][col];
					}
				}

				// clear the top row
				for (int col = 0; col < Constants.COLS; col++) {
					board[0][col] = null;
				}

				deleted++;
				r++; // check same row again
			}
		}

		return deleted;
	}

	// wipe the board for new game
	public void clear() {
		board = new Color[Constants.ROWS][Constants.COLS];
	}

}
