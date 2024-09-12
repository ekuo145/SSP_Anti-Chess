import java.util.Scanner;


// Define the ChessBoard class
public class ChessBoard {
    private Piece[][] board = new Piece[8][8];
    private static Piece.Color currentPlayer = Piece.Color.WHITE;
    private boolean gameOver = false;

    //En Passant Logic TBA
    private int[] enPassantTarget = null;

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

    private void endGame() {
        gameOver = true;
        System.out.println("Thank you for playing! The game has ended.");
        // You can reset the game here or exit based on your requirements
    }

    private boolean isWithinBounds(int row, int col) {
        return row >= 0 && row < board.length && col >= 0 && col < board[0].length;
    }
    

    // Method to check if a move is valid based on the piece's movement rules
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol) {
        // Ensure the starting and ending positions are within the bounds of the board
        if (!isWithinBounds(startRow, startCol) || !isWithinBounds(endRow, endCol)) {
            System.out.println("Move is out of bounds.");
            return false;
        }
    
        Piece piece = board[startRow][startCol];
    
        // Check if there's a piece at the start position
        if (piece == null) {
            System.out.println("No piece at the start position.");
            return false;
        }
    
        // Only allow the current player to move their own pieces
        if (piece.getColor() != currentPlayer) {
            System.out.println("It's not your turn!");
            System.out.println("It's " + currentPlayer + "'s Turn");
            System.out.println("You tried to move " + piece.getColor() + "'s piece");
            return false;
        }
    
        boolean hasCapture = hasMandatoryCapture(currentPlayer, board);

    
        // If a capture is mandatory, allow only capturing moves
        if (hasCapture) {
            if (canCapture(piece, startRow, startCol, board)) {
                // System.out.println("A piece has mandatory capture");
                return piece.canMove(startRow, startCol, endRow, endCol, board);
            } else {
                // System.out.println("Capture is mandatory, but this move doesn't capture.");
                return false;
            }
        } else {
            // If no captures are mandatory, allow any valid move according to the piece's rules
            if (!piece.canMove(startRow, startCol, endRow, endCol, board)) {
                //Problem is that canMove function is not being passed properly
                System.out.println("This move doesn't follow the move rules");
                return false;
            }
        }
        return true;
    }
    
    

    public boolean hasMandatoryCapture(Piece.Color currentPlayerColor, Piece[][] board) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                Piece piece = board[row][col];
                if (piece != null && piece.getColor() == currentPlayerColor) {
                    if (canCapture(piece, row, col, board)) {
                        return true;  // If any capture is possible, return true
                    }
                }
            }
        }
        return false;  // No captures found
    }

    private boolean canCapture(Piece piece, int startRow, int startCol, Piece[][] board) {
        // Check all possible moves for this piece and see if any involve capturing
        for (int endRow = 0; endRow < board.length; endRow++) {
            for (int endCol = 0; endCol < board[endRow].length; endCol++) {
                // The piece can only capture if there is an opponent's piece at the target location
                if (board[endRow][endCol] != null && board[endRow][endCol].getColor() != piece.getColor()) {
                    if (piece.canMove(startRow, startCol, endRow, endCol, board)) {
                        // System.out.println("Capture available for piece at (" + startRow + ", " + startCol + ")");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean movePiece(int[] move) {
        int startRow = move[0];
        int startCol = move[1];
        int endRow = move[2];
        int endCol = move[3];

        if (gameOver) {
            System.out.println("Game is over. No more moves allowed.");
            return false;
        }

        if (isValidMove(startRow, startCol, endRow, endCol)) {
            // Move the piece
            board[endRow][endCol] = board[startRow][startCol];
            board[startRow][startCol] = null;
    
            System.out.println("Move made from (" + startRow + ", " + startCol + ") to (" + endRow + ", " + endCol + ")");

            if (board[endRow][endCol] instanceof Piece) {

                checkPawnPromotion(endRow, endCol);
            }
            
            // Switch player after the move
            switchPlayer();
    
            // Check if the next player has valid moves or if the game should end
            checkGameEnd();
            
            return true;
        } else {
            System.out.println("Invalid move. Try again.");
            return false;
        }
    }

    private boolean isCaptureMove(int startRow, int startCol, int endRow, int endCol) {
        Piece startPiece = board[startRow][startCol];
        Piece endPiece = board[endRow][endCol];
    
        // A capture move happens when the target square has an opponent's piece
        return endPiece != null && startPiece.getColor() != endPiece.getColor();
    }
    
    private void checkPawnPromotion(int endRow, int endCol) {
        Piece piece = board[endRow][endCol];
        if (piece instanceof Piece) {
            if (piece.getType() == Piece.PieceType.PAWN) {
            // Check if the pawn has reached the last row (opposite side)
            if ((piece.getColor() == Piece.Color.WHITE && endRow == 0) || 
                (piece.getColor() == Piece.Color.BLACK && endRow == 7)) {
    
                // Promotion logic - ask the player what they want to promote to
                String choice = getPromotionChoice(piece.getColor());
                switch (choice.toUpperCase()) {
                    case "Q":
                        board[endRow][endCol] = new Piece(Piece.PieceType.QUEEN, piece.getColor());
                        break;
                    case "R":
                        board[endRow][endCol] = new Piece(Piece.PieceType.ROOK, piece.getColor());
                        break;
                    case "B":
                        board[endRow][endCol] = new Piece(Piece.PieceType.BISHOP, piece.getColor());
                        break;
                    case "N":
                        board[endRow][endCol] = new Piece(Piece.PieceType.KNIGHT, piece.getColor());
                        break;
                    case "K":
                        board[endRow][endCol] = new Piece(Piece.PieceType.KING, piece.getColor());
                        break;
                    default:
                        System.out.println("Invalid choice. Promoting to Queen by default.");
                        board[endRow][endCol] = new Piece(Piece.PieceType.QUEEN, piece.getColor());
                        break;
                }
                System.out.println("Pawn promoted to " + choice);
            }
            }
        }
    }
    
    // This method asks the player for their promotion choice
    private String getPromotionChoice(Piece.Color color) {
        // In a text-based system, prompt the player for input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Pawn has reached the other side! Choose a piece to promote to (Q, R, B, N, K):");
        scanner.close();
        return scanner.nextLine();
    }
    

    private boolean hasValidMove(Piece.Color playerColor) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                Piece piece = board[row][col];
                if (piece != null && piece.getColor() == playerColor) {
                    for (int endRow = 0; endRow < board.length; endRow++) {
                        for (int endCol = 0; endCol < board[endRow].length; endCol++) {
                            if (isValidMove(row, col, endRow, endCol)) {
                                return true;  // Player has at least one valid move
                            }
                        }
                    }
                }
            }
        }
        return false;  // No valid moves found
    }

    private boolean hasPieces(Piece.Color playerColor) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                Piece piece = board[row][col];
                if (piece != null && piece.getColor() == playerColor) {
                    return true;  // The player still has pieces left
                }
            }
        }
        return false;  // No pieces left for the player
    }
    
    public void switchPlayer() {
        currentPlayer = (currentPlayer == Piece.Color.WHITE) ? Piece.Color.BLACK : Piece.Color.WHITE;
    }

    public static void printTurn() {
        System.out.println("It's " + (currentPlayer == Piece.Color.WHITE ? "White" : "Black") + "'s turn.");
    }

    private void checkGameEnd() {
        // Check if the current player has any valid moves
        if (!hasValidMove(currentPlayer)) {
            System.out.println("Player " + (currentPlayer == Piece.Color.WHITE ? "White" : "Black") + " has no valid moves left!");
            System.out.println("Game over! " + (currentPlayer == Piece.Color.WHITE ? "Black" : "White") + " wins!");
            endGame();
            return;
        }
    
        // Check if the current player has any pieces left
        if (!hasPieces(currentPlayer)) {
            System.out.println("Player " + (currentPlayer == Piece.Color.WHITE ? "White" : "Black") + " has no pieces left!");
            System.out.println("Game over! " + (currentPlayer == Piece.Color.WHITE ? "Black" : "White") + " wins!");
            endGame();
            return;
        }
    }
    
    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        while (!gameOver) {
            System.out.println("Current Board:");
            printBoard();
    
            // Indicate whose turn it is
            System.out.println(currentPlayer + "'s move.");
    
            // Get user input for the move
            System.out.print("Enter your move (e.g., 'e2 e4' or '0 1 2 1'): ");
            String input = scanner.nextLine();
            
            // Parse the input
            int[] move = parseMoveInput(input);
            if (move == null) {
                System.out.println("Invalid move format. Please try again.");
                continue;
            }
    
                // Make the move
            movePiece(move);
        }
        System.out.println("Game over!");
        scanner.close();
    }
    

    public int[] parseMoveInput(String input) {
        // Simple format handling like "e2 e4"
        String[] parts = input.split(" ");
        if (parts.length != 2) {
            return null; // Invalid input
        }
    
        int startRow = parts[0].charAt(1) - '1';  // Converts '2' to 1
        int startCol = parts[0].charAt(0) - 'a';  // Converts 'e' to 4
        int endRow = parts[1].charAt(1) - '1';
        int endCol = parts[1].charAt(0) - 'a';
    
        return new int[] {startRow, startCol, endRow, endCol};
    }
    // public int[] parseMoveInput(int startRow, int startCol, int endRow, int endCol) {
    //     // Ensure the input values are valid (e.g., within bounds of the board)
    //     if (startRow < 0 || startRow > 7 || startCol < 0 || startCol > 7 || 
    //         endRow < 0 || endRow > 7 || endCol < 0 || endCol > 7) {
    //         return null; // Invalid input, out of bounds
    //     }
    
    //     // Return the parsed input as an array of integers
    //     return new int[] {startRow, startCol, endRow, endCol};
    // }
    
    

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