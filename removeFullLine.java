//add to gameLogic (as Score function)
private void removeFullLine() {
    int linesCleared = 0;
    for (int i = boxHeight - 1; i >= 0; i--) {
        boolean lineIsFull = true;
        for (int j = 0; j < boxWidth; j++) {
            if (box[i][j] == 0) {
                lineIsFull = false;
                break;
            }
        }
        if (lineIsFull) {
            linesCleared++;
            for (int k = i; k > 0; k--) {
                for (int j = 0; j < boxWidth; j++) box[k][j] = box[k - 1][j];
            }
            i++;
        }
    }
    if (linesCleared > 0) {
        switch (linesCleared) {
            case 1: score += 100; break;
            case 2: score += 300; break;
            case 3: score += 500; break;
            case 4: score += 800; break;
        }
        isFallingFinished = true;
        currentPiece.setPieceShape(0);
        repaint();
    }
}