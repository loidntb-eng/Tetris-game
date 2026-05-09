public class Piece {
    // Tetris piece shapes with 4 rotation states (in coordinates relative to position)
    private static final int[][][][] SHAPES = {
        // I piece
        {{{0, 0}, {0, 1}, {0, 2}, {0, 3}}, {{0, 0}, {1, 0}, {2, 0}, {3, 0}}, 
         {{0, 0}, {0, 1}, {0, 2}, {0, 3}}, {{0, 0}, {1, 0}, {2, 0}, {3, 0}}},
        // O piece (no rotation)
        {{{0, 0}, {1, 0}, {0, 1}, {1, 1}}, {{0, 0}, {1, 0}, {0, 1}, {1, 1}}, 
         {{0, 0}, {1, 0}, {0, 1}, {1, 1}}, {{0, 0}, {1, 0}, {0, 1}, {1, 1}}},
        // T piece
        {{{0, 1}, {1, 0}, {1, 1}, {1, 2}}, {{0, 0}, {1, 0}, {1, 1}, {2, 0}}, 
         {{1, 0}, {1, 1}, {1, 2}, {2, 1}}, {{0, 1}, {1, 1}, {2, 1}, {2, 0}}},
        // S piece
        {{{0, 1}, {0, 2}, {1, 0}, {1, 1}}, {{0, 0}, {1, 0}, {1, 1}, {2, 1}}, 
         {{0, 1}, {0, 2}, {1, 0}, {1, 1}}, {{0, 0}, {1, 0}, {1, 1}, {2, 1}}},
        // Z piece
        {{{0, 0}, {0, 1}, {1, 1}, {1, 2}}, {{0, 1}, {1, 0}, {1, 1}, {2, 0}}, 
         {{0, 0}, {0, 1}, {1, 1}, {1, 2}}, {{0, 1}, {1, 0}, {1, 1}, {2, 0}}},
        // J piece
        {{{0, 0}, {1, 0}, {2, 0}, {2, 1}}, {{0, 0}, {0, 1}, {0, 2}, {1, 2}}, 
         {{0, 0}, {0, 1}, {1, 1}, {2, 1}}, {{0, 0}, {1, 0}, {1, 1}, {1, 2}}},
        // L piece
        {{{0, 1}, {1, 1}, {2, 1}, {2, 0}}, {{0, 0}, {1, 0}, {2, 0}, {2, 1}}, 
         {{0, 0}, {0, 1}, {1, 0}, {2, 0}}, {{0, 0}, {0, 1}, {1, 1}, {2, 1}}}
    };
    
    private final int[][] blocks;
    private final int colorIndex;
    private int x;
    private int y;
    private final int shapeIndex;
    private int rotationState;
    
    public Piece() {
        this.shapeIndex = (int)(Math.random() * SHAPES.length);
        this.colorIndex = shapeIndex;
        this.x = Constants.GRID_WIDTH / 2 - 1;
        this.y = 0;
        this.rotationState = 0;
        this.blocks = new int[4][2];
        updateBlocks();
    }
    
    public void moveDown() {
        y++;
        updateBlocks();
    }
    
    public void moveUp() {
        y--;
        updateBlocks();
    }
    
    public void moveLeft() {
        x--;
        updateBlocks();
    }
    
    public void moveRight() {
        x++;
        updateBlocks();
    }
    
    public void rotate() {
        rotationState = (rotationState + 1) % 4;
        updateBlocks();
    }
    
    public void rotateBack() {
        rotationState = (rotationState + 3) % 4;
        updateBlocks();
    }
    
    private void updateBlocks() {
        int[][] shape = SHAPES[shapeIndex][rotationState];
        for (int i = 0; i < 4; i++) {
            blocks[i][0] = y + shape[i][0];
            blocks[i][1] = x + shape[i][1];
        }
    }
    
    public int[][] getBlocks() {
        return blocks;
    }
    
    public int getColorIndex() {
        return colorIndex;
    }
    
    public int getPieceX(int index) {
        return blocks[index][1];
    }
    
    public int getPieceY(int index) {
        return blocks[index][0];
    }
}
