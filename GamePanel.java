import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class GamePanel extends JPanel implements KeyListener {
    private final GameLogic gameLogic;
    private Timer gameTimer;
    
    public GamePanel() {
        this.setPreferredSize(new Dimension(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        this.setBackground(Constants.BG_COLOR);
        this.gameLogic = new GameLogic();
        this.setFocusable(true);
        this.addKeyListener(this); // Standard Swing pattern
    }
    
    public void start() {
        gameTimer = new Timer(16, e -> {
            gameLogic.update();
            repaint();
        });
        gameTimer.start();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw grid
        drawGrid(g2d);
        
        // Draw placed pieces
        drawPlacedPieces(g2d);
        
        // Draw current piece
        drawCurrentPiece(g2d);
        
        // Draw score
        drawScore(g2d);
        
        // Draw game over message
        if (gameLogic.isGameOver()) {
            drawGameOver(g2d);
        }
    }
    
    private void drawGrid(Graphics2D g2d) {
        g2d.setColor(Constants.GRID_COLOR);
        g2d.setStroke(new BasicStroke(1));
        
        for (int row = 0; row <= Constants.GRID_HEIGHT; row++) {
            g2d.drawLine(0, row * Constants.CELL_SIZE, 
                        Constants.GRID_WIDTH * Constants.CELL_SIZE, 
                        row * Constants.CELL_SIZE);
        }
        
        for (int col = 0; col <= Constants.GRID_WIDTH; col++) {
            g2d.drawLine(col * Constants.CELL_SIZE, 0, 
                        col * Constants.CELL_SIZE, 
                        Constants.GRID_HEIGHT * Constants.CELL_SIZE);
        }
    }
    
    private void drawPlacedPieces(Graphics2D g2d) {
        Grid grid = gameLogic.getGrid();
        for (int row = 0; row < Constants.GRID_HEIGHT; row++) {
            for (int col = 0; col < Constants.GRID_WIDTH; col++) {
                int colorIndex = grid.getCell(row, col);
                if (colorIndex > 0) {
                    drawCell(g2d, row, col, Constants.PIECE_COLORS[colorIndex - 1]);
                }
            }
        }
    }
    
    private void drawCurrentPiece(Graphics2D g2d) {
        Piece piece = gameLogic.getCurrentPiece();
        Color color = Constants.PIECE_COLORS[piece.getColorIndex()];
        
        for (int[] block : piece.getBlocks()) {
            int row = block[0];
            int col = block[1];
            if (row >= 0 && row < Constants.GRID_HEIGHT && col >= 0 && col < Constants.GRID_WIDTH) {
                drawCell(g2d, row, col, color);
            }
        }
    }
    
    private void drawCell(Graphics2D g2d, int row, int col, Color color) {
        int x = col * Constants.CELL_SIZE;
        int y = row * Constants.CELL_SIZE;
        
        g2d.setColor(color);
        g2d.fillRect(x + 1, y + 1, Constants.CELL_SIZE - 2, Constants.CELL_SIZE - 2);
        
        g2d.setColor(new Color(255, 255, 255));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(x + 1, y + 1, Constants.CELL_SIZE - 2, Constants.CELL_SIZE - 2);
    }
    
    private void drawScore(Graphics2D g2d) {
        g2d.setColor(Constants.TEXT_COLOR);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        g2d.drawString("Score: " + gameLogic.getScore(), 10, Constants.GRID_HEIGHT * Constants.CELL_SIZE + 35);
        
        // Draw instructions
        g2d.setFont(new Font("Arial", Font.PLAIN, 14));
        g2d.drawString("←→ Move  ↓ Speed Up  Space Rotate  R Restart", 10, Constants.GRID_HEIGHT * Constants.CELL_SIZE + 55);
    }
    
    private void drawGameOver(Graphics2D g2d) {
        // Semi-transparent overlay
        g2d.setColor(new Color(0, 0, 0, 200));
        g2d.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.GRID_HEIGHT * Constants.CELL_SIZE);
        
        g2d.setColor(Constants.TEXT_COLOR);
        g2d.setFont(new Font("Arial", Font.BOLD, 48));
        String gameOverText = "GAME OVER";
        FontMetrics fm = g2d.getFontMetrics();
        int x = (Constants.WINDOW_WIDTH - fm.stringWidth(gameOverText)) / 2;
        int y = (Constants.GRID_HEIGHT * Constants.CELL_SIZE - fm.getHeight()) / 2 + fm.getAscent();
        g2d.drawString(gameOverText, x, y);
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (gameLogic.isGameOver()) {
            if (e.getKeyCode() == KeyEvent.VK_R) {
                gameLogic.restart();
            }
            return;
        }
        
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> gameLogic.movePieceLeft();
            case KeyEvent.VK_RIGHT -> gameLogic.movePieceRight();
            case KeyEvent.VK_DOWN -> gameLogic.movePieceDown();
            case KeyEvent.VK_SPACE -> gameLogic.rotatePiece();
            case KeyEvent.VK_R -> gameLogic.restart();
            default -> {}
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {}
    
    @Override
    public void keyTyped(KeyEvent e) {}
}
