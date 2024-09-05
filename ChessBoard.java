// Define the ChessBoard class
public class ChessBoard {
    private Piece[][] board = new Piece[8][8];

    // Constructor initializes the board with pieces
    public ChessBoard() {
        setUpPieces();
    }

    // Method to set up the pieces
    private void setUpPieces() {
        // Set up white pieces
        board[0][0] = new Piece(Piece.PieceType.ROOK, Piece.Color.WHITE);
        board[0][1] = new Piece(Piece.PieceType.KNIGHT, Piece.Color.WHITE);
        board[0][2] = new Piece(Piece.PieceType.BISHOP, Piece.Color.WHITE);
        board[0][3] = new Piece(Piece.PieceType.QUEEN, Piece.Color.WHITE);
        board[0][4] = new Piece(Piece.PieceType.KING, Piece.Color.WHITE);
        board[0][5] = new Piece(Piece.PieceType.BISHOP, Piece.Color.WHITE);
        board[0][6] = new Piece(Piece.PieceType.KNIGHT, Piece.Color.WHITE);
        board[0][7] = new Piece(Piece.PieceType.ROOK, Piece.Color.WHITE);

        // Set up white pawns
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Piece(Piece.PieceType.PAWN, Piece.Color.WHITE);
        }

        // Set up black pieces
        board[7][0] = new Piece(Piece.PieceType.ROOK, Piece.Color.BLACK);
        board[7][1] = new Piece(Piece.PieceType.KNIGHT, Piece.Color.BLACK);
        board[7][2] = new Piece(Piece.PieceType.BISHOP, Piece.Color.BLACK);
        board[7][3] = new Piece(Piece.PieceType.QUEEN, Piece.Color.BLACK);
        board[7][4] = new Piece(Piece.PieceType.KING, Piece.Color.BLACK);
        board[7][5] = new Piece(Piece.PieceType.BISHOP, Piece.Color.BLACK);
        board[7][6] = new Piece(Piece.PieceType.KNIGHT, Piece.Color.BLACK);
        board[7][7] = new Piece(Piece.PieceType.ROOK, Piece.Color.BLACK);

        // Set up black pawns
        for (int i = 0; i < 8; i++) {
            board[6][i] = new Piece(Piece.PieceType.PAWN, Piece.Color.BLACK);
        }
    }

    // Print the board
    public void printBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] == null) {
                    System.out.print("[    ] ");
                } else {
                    System.out.print("[" + board[row][col].getType().toString().charAt(0) + " " + board[row][col].getColor().toString().charAt(0) + "] ");
                }
            }
            System.out.println();
        }
    }
}