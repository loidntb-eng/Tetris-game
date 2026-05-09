public class Constants {
    // Grid settings
    public static final int GRID_WIDTH = 10;
    public static final int GRID_HEIGHT = 20;
    public static final int CELL_SIZE = 30;
    
    // Window settings
    public static final int WINDOW_WIDTH = GRID_WIDTH * CELL_SIZE;
    public static final int WINDOW_HEIGHT = GRID_HEIGHT * CELL_SIZE + 50;
    
    // Game speed (milliseconds)
    public static final int FALL_SPEED = 200;
    
    // Colors
    public static final java.awt.Color BG_COLOR = new java.awt.Color(0, 0, 0);
    public static final java.awt.Color GRID_COLOR = new java.awt.Color(50, 50, 50);
    public static final java.awt.Color TEXT_COLOR = new java.awt.Color(255, 255, 255);
    
    // Piece colors (7 Tetris pieces)
    public static final java.awt.Color[] PIECE_COLORS = {
        new java.awt.Color(0, 255, 255),      // I - Cyan
        new java.awt.Color(255, 255, 0),      // O - Yellow
        new java.awt.Color(128, 0, 128),      // T - Purple
        new java.awt.Color(0, 255, 0),        // S - Green
        new java.awt.Color(255, 0, 0),        // Z - Red
        new java.awt.Color(0, 0, 255),        // J - Blue
        new java.awt.Color(255, 165, 0)       // L - Orange
    };
}