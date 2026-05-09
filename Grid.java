import java.util.Arrays;

public class Grid {
    private final int[][] grid;
    private final int width;
    private final int height;
    
    public Grid() {
        this.width = Constants.GRID_WIDTH;
        this.height = Constants.GRID_HEIGHT;
        this.grid = new int[height][width];
        // Grid is automatically initialized to 0 by Java
    }
    
    public void clear() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                grid[row][col] = 0;
            }
        }
    }
    
    // Check if piece can be placed
    public boolean canPlace(Piece piece) {
        for (int[] block : piece.getBlocks()) {
            int row = block[0];
            int col = block[1];
            
            // Check boundaries
            if (col < 0 || col >= width || row >= height) {
                return false;
            }
            
            // Check placed blocks
            if (row >= 0 && grid[row][col] != 0) {
                return false;
            }
        }
        return true;
    }
    
    // Place piece permanently
    public void placePiece(Piece piece) {
        for (int[] block : piece.getBlocks()) {
            int row = block[0];
            int col = block[1];
            if (row >= 0 && row < height && col >= 0 && col < width) {
                grid[row][col] = piece.getColorIndex() + 1;
            }
        }
    }
    
    // Clear full lines and return score
    public int clearLines() {
        int linesCleared = 0;
        for (int row = height - 1; row >= 0; row--) {
            if (isLineFull(row)) {
                removeLine(row);
                linesCleared++;
                row++;
            }
        }
        return linesCleared;
    }
    
    private boolean isLineFull(int row) {
        for (int col = 0; col < width; col++) {
            if (grid[row][col] == 0) {
                return false;
            }
        }
        return true;
    }
    
    private void removeLine(int row) {
        for (int r = row; r > 0; r--) {
            System.arraycopy(grid[r - 1], 0, grid[r], 0, width);
        }
        Arrays.fill(grid[0], 0);
    }
    
    public int getCell(int row, int col) {
        if (row < 0 || row >= height || col < 0 || col >= width) {
            return 0;
        }
        return grid[row][col];
    }
    
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}