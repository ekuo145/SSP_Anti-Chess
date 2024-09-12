// Define the Piece class and related enums
public class Piece {
    // Enum for piece type
    public enum PieceType {
        KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN;
    }

    // Enum for piece color
    public enum Color {
        WHITE, BLACK;
    }

    private PieceType type;
    private Color color;

    // Constructor for creating a piece
    public Piece(PieceType type, Color color) {
        this.type = type;
        this.color = color;
    }

    // Getters for type and color
    public PieceType getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    public String getSymbol() {
        switch (type) {
            case PAWN:
                return (color == Color.WHITE) ? "P" : "p";
            case ROOK:
                return (color == Color.WHITE) ? "R" : "r";
            case KNIGHT:
                return (color == Color.WHITE) ? "N" : "n";
            case BISHOP:
                return (color == Color.WHITE) ? "B" : "b";
            case QUEEN:
                return (color == Color.WHITE) ? "Q" : "q";
            case KING:
                return (color == Color.WHITE) ? "K" : "k";
            default:
                return "?";
        }
    }

    // Method to check if the piece can move from (startRow, startCol) to (endRow, endCol)
    public boolean canMove(int startRow, int startCol, int endRow, int endCol, Piece[][] board) {
        switch (type) {
            case KING:
                return canMoveKing(startRow, startCol, endRow, endCol);
            case QUEEN:
                return canMoveQueen(startRow, startCol, endRow, endCol, board);
            case ROOK:
                return canMoveRook(startRow, startCol, endRow, endCol, board);
            case BISHOP:
                return canMoveBishop(startRow, startCol, endRow, endCol, board);
            case KNIGHT:
                return canMoveKnight(startRow, startCol, endRow, endCol);
            case PAWN:
                return canMovePawn(startRow, startCol, endRow, endCol, board);
            default:
                return false;
        }
    }

    // King moves one square in any direction
    public boolean canMoveKing(int startRow, int startCol, int endRow, int endCol) {
        return Math.abs(startRow - endRow) <= 1 && Math.abs(startCol - endCol) <= 1;
    }

    // Queen moves like both a rook and a bishop
    public boolean canMoveQueen(int startRow, int startCol, int endRow, int endCol, Piece[][] board) {
        return canMoveRook(startRow, startCol, endRow, endCol, board) || canMoveBishop(startRow, startCol, endRow, endCol, board);
    }

    // Rook moves in straight lines (either row or column must be the same)
    public boolean canMoveRook(int startRow, int startCol, int endRow, int endCol, Piece[][] board) {
        if (startRow != endRow && startCol != endCol) return false;
        // Check if path is clear (no pieces between start and end)
        if (startRow == endRow) {  // Horizontal movement
            int minCol = Math.min(startCol, endCol);
            int maxCol = Math.max(startCol, endCol);
            for (int col = minCol + 1; col < maxCol; col++) {
                if (board[startRow][col] != null) return false;
            }
        } else {  // Vertical movement
            int minRow = Math.min(startRow, endRow);
            int maxRow = Math.max(startRow, endRow);
            for (int row = minRow + 1; row < maxRow; row++) {
                if (board[row][startCol] != null) return false;
            }
        }
        return true;
    }

    // Bishop moves diagonally
    public boolean canMoveBishop(int startRow, int startCol, int endRow, int endCol, Piece[][] board) {
        if (Math.abs(startRow - endRow) != Math.abs(startCol - endCol)) return false;
        // Check if path is clear
        int rowDirection = (endRow > startRow) ? 1 : -1;
        int colDirection = (endCol > startCol) ? 1 : -1;
        int row = startRow + rowDirection, col = startCol + colDirection;
        while (row != endRow && col != endCol) {
            if (board[row][col] != null) return false;
            row += rowDirection;
            col += colDirection;
        }
        return true;
    }

    // Knight moves in an "L" shape
    public boolean canMoveKnight(int startRow, int startCol, int endRow, int endCol) {
        int rowDiff = Math.abs(startRow - endRow);
        int colDiff = Math.abs(startCol - endCol);
        return (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
    }

    public boolean canMovePawn(int startRow, int startCol, int endRow, int endCol, Piece[][] board) {
        int direction = (color == Color.WHITE) ? 1 : -1;  // White pawns move up (1), Black pawns move down (-1)
        
        // System.out.println("Checking Pawn Move: startRow = " + startRow + ", startCol = " + startCol + ", endRow = " + endRow + ", endCol = " + endCol);
        // System.out.println("Pawn color: " + color + ", direction: " + direction);
    
        // Moving forward (not capturing)
        if (startCol == endCol) {
            // System.out.println("Attempting forward move...");
            // System.out.println("Intermediate square (2, 0): " + board[2][0]);
            // System.out.println("Target square (3, 0): " + board[3][0]);

            if (board[endRow][endCol] == null) {  // Only move forward if the target square is empty
                System.out.println("End square is empty.");
                // One square forward move
                if (startRow + direction == endRow) {
                    System.out.println("One-square move is valid.");
                    return true;
                }
                // Two squares forward move
                // else if (startRow == (color == Color.WHITE ? 6 : 1)) {
                else if ((startRow == 1 && color == Color.WHITE) || (startRow == 6 && color == Color.BLACK)) {
                    int intermediateRow = startRow + direction;
                    System.out.println("Intermediate row: " + intermediateRow);
                    if (startRow + 2 * direction == endRow && board[intermediateRow][startCol] == null) {
                        System.out.println("Two-square move is valid.");
                        return true;
                    } else {
                        System.out.println("Path is blocked or endRow is incorrect for two-square move.");
                    }
                }
             } 
            //else {
            //     System.out.println("End square is occupied.");
            // }
        }
        
        // Capturing diagonally
        else if (Math.abs(startCol - endCol) == 1 && startRow + direction == endRow) {
            System.out.println("Attempting diagonal capture...");
            if (board[endRow][endCol] != null && board[endRow][endCol].getColor() != this.color) {
                System.out.println("Capture is valid.");
                return true;
            } else {
                System.out.println("No valid capture.");
            }
        }
    
        // System.out.println("Move is invalid.");
        return false;
    }

    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Piece[][] board) {
        //return false is placeholder, find new logic to make it save a lot more space.
        return canMove(startRow, startCol, endRow, endCol, board);
    } 

}