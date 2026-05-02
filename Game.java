import javax.swing.*;

public class Game {
    private static GamePanel gamePanel;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tetris Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            
            gamePanel = new GamePanel();
            frame.add(gamePanel);
            
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            
            gamePanel.start();
        });
    }
}

class GameLogic {
    private Grid grid;
    private Piece currentPiece;
    private int score = 0;
    private boolean gameOver = false;
    private long lastFallTime;
    
    public GameLogic() {
        this.grid = new Grid();
        this.currentPiece = new Piece();
        this.lastFallTime = System.currentTimeMillis();
    }
    
    public void update() {
        long currentTime = System.currentTimeMillis();
        
        if (currentTime - lastFallTime > Constants.FALL_SPEED) {
            movePieceDown();
            lastFallTime = currentTime;
        }
    }
    
    private void movePieceDown() {
        currentPiece.moveDown();
        
        if (!grid.canPlace(currentPiece)) {
            currentPiece.moveUp();
            grid.placePiece(currentPiece);
            
            int linesCleared = grid.clearLines();
            score += linesCleared * 100;
            
            currentPiece = new Piece();
            
            if (!grid.canPlace(currentPiece)) {
                gameOver = true;
            }
        }
    }
    
    public void movePieceLeft() {
        currentPiece.moveLeft();
        if (!grid.canPlace(currentPiece)) {
            currentPiece.moveRight();
        }
    }
    
    public void movePieceRight() {
        currentPiece.moveRight();
        if (!grid.canPlace(currentPiece)) {
            currentPiece.moveLeft();
        }
    }
    
    public void rotatePiece() {
        currentPiece.rotate();
        if (!grid.canPlace(currentPiece)) {
            currentPiece.rotateBack();
        }
    }
    
    public Grid getGrid() { return grid; }
    public Piece getCurrentPiece() { return currentPiece; }
    public int getScore() { return score; }
    public boolean isGameOver() { return gameOver; }
}