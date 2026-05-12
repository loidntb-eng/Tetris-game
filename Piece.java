import java.awt.Color;
import java.util.Random;

// Piece.java - Person 2
// 
// ok so i hardcoded all the rotations because i couldnt get the
// rotation algorithm to work properly with the I piece
// (it kept going out of bounds)
// so each piece just has all 4 rotations stored already
// then rotate() just switches to the next one
//
// each piece shape is a 2d array of 0s and 1s
// 1 = there is a block there
// 0 = empty

public class Piece {

	public int[][] shape;  // current shape
	public Color color;
	public int x;
	public int y;
	public int type;       // 0=I 1=O 2=T 3=S 4=Z 5=J 6=L

	int rotation; // which rotation we are on (0-3)

	// all 4 rotations for each piece
	// i drew these out on paper to make sure they are right

	// I piece rotations
	static int[][][] I_ROTS = {
		{{0,0,0,0},
		 {1,1,1,1},
		 {0,0,0,0},
		 {0,0,0,0}},

		{{0,0,1,0},
		 {0,0,1,0},
		 {0,0,1,0},
		 {0,0,1,0}},

		{{0,0,0,0},
		 {0,0,0,0},
		 {1,1,1,1},
		 {0,0,0,0}},

		{{0,1,0,0},
		 {0,1,0,0},
		 {0,1,0,0},
		 {0,1,0,0}}
	};

	// O piece - same in all rotations (square doesnt change)
	static int[][][] O_ROTS = {
		{{1,1},
		 {1,1}},

		{{1,1},
		 {1,1}},

		{{1,1},
		 {1,1}},

		{{1,1},
		 {1,1}}
	};

	// T piece rotations
	static int[][][] T_ROTS = {
		{{0,1,0},
		 {1,1,1},
		 {0,0,0}},

		{{0,1,0},
		 {0,1,1},
		 {0,1,0}},

		{{0,0,0},
		 {1,1,1},
		 {0,1,0}},

		{{0,1,0},
		 {1,1,0},
		 {0,1,0}}
	};

	// S piece rotations
	static int[][][] S_ROTS = {
		{{0,1,1},
		 {1,1,0},
		 {0,0,0}},

		{{0,1,0},
		 {0,1,1},
		 {0,0,1}},

		{{0,0,0},
		 {0,1,1},
		 {1,1,0}},

		{{1,0,0},
		 {1,1,0},
		 {0,1,0}}
	};

	// Z piece rotations
	static int[][][] Z_ROTS = {
		{{1,1,0},
		 {0,1,1},
		 {0,0,0}},

		{{0,0,1},
		 {0,1,1},
		 {0,1,0}},

		{{0,0,0},
		 {1,1,0},
		 {0,1,1}},

		{{0,1,0},
		 {1,1,0},
		 {1,0,0}}
	};

	// J piece rotations
	static int[][][] J_ROTS = {
		{{1,0,0},
		 {1,1,1},
		 {0,0,0}},

		{{0,1,1},
		 {0,1,0},
		 {0,1,0}},

		{{0,0,0},
		 {1,1,1},
		 {0,0,1}},

		{{0,1,0},
		 {0,1,0},
		 {1,1,0}}
	};

	// L piece rotations
	static int[][][] L_ROTS = {
		{{0,0,1},
		 {1,1,1},
		 {0,0,0}},

		{{0,1,0},
		 {0,1,0},
		 {0,1,1}},

		{{0,0,0},
		 {1,1,1},
		 {1,0,0}},

		{{1,1,0},
		 {0,1,0},
		 {0,1,0}}
	};

	// random piece
	public Piece() {
		Random r = new Random();
		type = r.nextInt(7);
		rotation = 0;
		setShapeAndColor();
		x = 0;
		y = 0;
	}
	

	// specific piece by type (used for hold)
	public Piece(int t) {
		type = t;
		rotation = 0;
		setShapeAndColor();
		x = 0;
		y = 0;
	}

	// set the shape array and color based on type
	void setShapeAndColor() {
		switch (type) {
		    case 0 -> { shape = I_ROTS[rotation]; color = Color.CYAN; }
		    case 1 -> { shape = O_ROTS[rotation]; color = Color.YELLOW; }
		    case 2 -> { shape = T_ROTS[rotation]; color = Color.MAGENTA; }
		    case 3 -> { shape = S_ROTS[rotation]; color = Color.GREEN; }
		    case 4 -> { shape = Z_ROTS[rotation]; color = Color.RED; }
		    case 5 -> { shape = J_ROTS[rotation]; color = Color.BLUE; }
    		case 6 -> { shape = L_ROTS[rotation]; color = Color.ORANGE; }
}

	}

	// go to next rotation
	public void rotate() {
		rotation++;
		if (rotation > 3) rotation = 0;
		setShapeAndColor();
	}

	// go back one rotation (used when rotation causes collision)
	public void rotateBack() {
		rotation--;
		if (rotation < 0) rotation = 3;
		setShapeAndColor();
	}
}
