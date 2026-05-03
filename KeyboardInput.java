//add to the Game.java

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

setFocusable(true);

addKeyListener(new KeyAdapter() {
    @Override
    public void keyPressed(KeyEvent e) {
        if (currentPiece.getPieceShape() == 0 || isFallingFinished) {
            return;
        }
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                tryMove(currentPiece, currentPieceX - 1, currentPieceY);
                break;
            case KeyEvent.VK_RIGHT:
                tryMove(currentPiece, currentPieceX + 1, currentPieceY);
                break;
            case KeyEvent.VK_DOWN:
                dropDown();
                break;
        }
    }
});