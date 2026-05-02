public class Piece {
    public enum Shape {
        //All of the arrays below are square arrays
        I_SHAPE(new int[][] {{0,0,0,0},{1,1,1,1},{0,0,0,0},{0,0,0,0}}, 1),
        J_SHAPE(new int[][] {{1,0,0},{1,1,1},{0,0,0}}, 2),
        L_SHAPE(new int[][] {{0,0,1},{1,1,1},{0,0,0}}, 3),
        O_SHAPE(new int[][] {{1,1},{1,1}}, 4),
        S_SHAPE(new int[][] {{0,1,1},{1,1,0},{0,0,0}}, 5),
        T_SHAPE(new int[][] {{0,1,0},{1,1,1},{0,0,0}}, 6),
        Z_SHAPE(new int[][] {{1,1,0},{0,1,1},{0,0,0}}, 7);
    
        public final int[][] blocks;
        public final int id;

        Shape(int[][] blocks, int id) {
            this.blocks = blocks;
            this.id = id;
        }
    }

    private int[][] currentBlocks;
    private int x, y; //Top left corner position inside of the main GRID array
    private final int id;

    //Constructor
    public Piece(Shape shape){
        /*
        Basic syntax is
            Piece myPiece = new Piece(Piece.Shape.O_SHAPE);
        */
        this.currentBlocks = shape.blocks;
        this.id = shape.id;
        this.x = 3;
        this.y = 0;
    }

    public Piece() {
        /*
        Overloading for random Piece generation
        */
        Shape[] shapes = Shape.values(); //Store all enum values
        Shape randomShape = shapes[(int)(Math.random() * shapes.length)];
        this.currentBlocks = randomShape.blocks;
        this.id = randomShape.id;
        this.x = 3;
        this.y = 0;
    }


    //Getters
    public int[][] getBlock() {return currentBlocks;}
    public int getX() {return x;}
    public int getY() {return y;}
    public int getId() {return id;}

    //Rotation
    public void rotate(boolean clock_wise){ //true for 90° clockwise, false for counter-clockwise
        int size = currentBlocks.length;
        int[][] rotated = new int[size][size];
        for (int r = 0; r < size; r++){
            for (int c = 0; c < size; c++){
                if (clock_wise) {
                    rotated[c][size - 1 - r] = currentBlocks[r][c];
                } else {
                    rotated[size - 1 - c][r] = currentBlocks[r][c];
                }
            }
        }
        currentBlocks = rotated;
    }

    //Movement
    public void moveDown() {y++;}
    public void moveUp() {y--;}
    public void moveLeft() {x--;}
    public void moveRight() {x++;}

}