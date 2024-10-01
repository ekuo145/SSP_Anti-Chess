import java.util.Scanner;
import javax.swing.SwingUtilities;
import java.util.List;
import java.util.ArrayList;


// Define the ChessBoard class
public class ChessBoard {
    private Piece[][] board = new Piece[8][8];
    private static Piece.Color currentPlayer = Piece.Color.WHITE;
    private boolean gameOver = false;
    private AntichessUI ui; // Reference to the UI

    // Constructor initializes the board with pieces
    public ChessBoard(AntichessUI ui) {
        this.ui = ui;
        setUpPieces();
        ui.updateBoard(board);
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

    public Piece[][] getBoard() {
        return board;
    }

    public List<int[]> getValidMoves(int row, int col) {
        List<int[]> validMoves = new ArrayList<>();
        Piece piece = board[row][col];
        
        if (piece == null) {
            return validMoves; // No piece at this location
        }

        switch (piece.getType()) {
            case PAWN:
                validMoves = getPawnMoves(row, col, piece);
                break;
            case ROOK:
                validMoves = getRookMoves(row, col, piece);
                break;
            case KNIGHT:
                validMoves = getKnightMoves(row, col, piece);
                break;
            case BISHOP:
                validMoves = getBishopMoves(row, col, piece);
                break;
            case QUEEN:
                validMoves = getQueenMoves(row, col, piece);
                break;
            case KING:
                validMoves = getKingMoves(row, col, piece);
                break;
        }

        return validMoves;
    }

    private List<int[]> getPawnMoves(int row, int col, Piece piece) {
        List<int[]> moves = new ArrayList<>();
        int direction = piece.getColor() == Piece.Color.WHITE ? -1 : 1; // White pawns move up, black down

        // Normal move forward (one square)
        if (isValidMove(row, col, row + direction, col)) {
            moves.add(new int[]{row + direction, col});
        }

        // First move, two squares forward
        if ((piece.getColor() == Piece.Color.WHITE && row == 6) || (piece.getColor() == Piece.Color.BLACK && row == 1)) {
        // Pawns can move two squares if they are in their starting position
            if (isValidMove(row, col, row + 2 * direction, col)) {
            moves.add(new int[]{row + 2 * direction, col});
            }
        }

        // Capture diagonally
        if (isValidMove(row, col, row + direction, col - 1)) {
            moves.add(new int[]{row + direction, col - 1});
        }
        if (isValidMove(row, col, row + direction, col + 1)) {
            moves.add(new int[]{row + direction, col + 1});
        }

        // En passant capture (check if the previous move allows for en passant)
        if (canCaptureEnPassant(row, col)) {
        moves.add(new int[]{row + direction, col + 1}); // Example: Add en passant move (to right)
        moves.add(new int[]{row + direction, col - 1}); // Example: Add en passant move (to left)
        }

        // Add more complex pawn logic here (e.g., promotion, en passant, double move on first turn)

        return moves;
    }

    private boolean canCaptureEnPassant(int row, int col) {
        // Check if the last move was a two-square pawn move and if the target square allows en passant
        Move lastMove = getLastMove();
    
        if (lastMove != null && lastMove.isPawnMove()) {
            Piece movedPawn = board[lastMove.endRow][lastMove.endCol];
            // Check if the pawn moved two squares and is next to the current pawn
            if (movedPawn.getColor() != currentPlayer && Math.abs(lastMove.startRow - lastMove.endRow) == 2) {
                if (lastMove.endRow == row && (lastMove.endCol == col + 1 || lastMove.endCol == col - 1)) {
                    // En passant is allowed
                    return true;
                }
            }
        }
    
        return false;
    }

    private List<int[]> getRookMoves(int row, int col, Piece piece) {
        List<int[]> moves = new ArrayList<>();
        
        // Directions: up, down, left, right
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    
        for (int[] direction : directions) {
            int newRow = row;
            int newCol = col;
            
            while (true) {
                newRow += direction[0];
                newCol += direction[1];
    
                if (!isWithinBounds(newRow, newCol)) {
                    break; // Out of bounds
                }
    
                // If the move is valid (not blocked by a piece of the same color)
                if (isValidMove(row, col, newRow, newCol)) {
                    moves.add(new int[]{newRow, newCol});
    
                    // Stop if we encounter an opponent's piece (we can capture it)
                    if (board[newRow][newCol] != null) {
                        break;
                    }
                } else {
                    break; // Blocked by own piece
                }
            }
        }
    
        return moves;
    }
    

    private List<int[]> getKnightMoves(int row, int col, Piece piece) {
        List<int[]> moves = new ArrayList<>();
        
        // The possible knight move offsets
        int[][] knightMoves = {
            {2, 1}, {2, -1}, {-2, 1}, {-2, -1}, // Vertical "L" moves
            {1, 2}, {1, -2}, {-1, 2}, {-1, -2}  // Horizontal "L" moves
        };
        
        // Iterate over all possible knight moves
        for (int[] move : knightMoves) {
            int newRow = row + move[0];
            int newCol = col + move[1];
    
            // Check if the move is within the board bounds and is valid
            if (isWithinBounds(newRow, newCol) && isValidMove(row, col, newRow, newCol)) {
                moves.add(new int[]{newRow, newCol});
            }
        }
    
        return moves;
    }
    
    private List<int[]> getBishopMoves(int row, int col, Piece piece) {
        List<int[]> moves = new ArrayList<>();
        
        // Directions: top-left, top-right, bottom-left, bottom-right
        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
    
        for (int[] direction : directions) {
            int newRow = row;
            int newCol = col;
    
            while (true) {
                newRow += direction[0];
                newCol += direction[1];
    
                if (!isWithinBounds(newRow, newCol)) {
                    break; // Out of bounds
                }
    
                // If the move is valid (not blocked by a piece of the same color)
                if (isValidMove(row, col, newRow, newCol)) {
                    moves.add(new int[]{newRow, newCol});
    
                    // Stop if we encounter an opponent's piece (we can capture it)
                    if (board[newRow][newCol] != null) {
                        break;
                    }
                } else {
                    break; // Blocked by own piece
                }
            }
        }
    
        return moves;
    }

    private List<int[]> getQueenMoves(int row, int col, Piece piece) {
        List<int[]> moves = new ArrayList<>();
    
        // Queen moves are a combination of rook and bishop moves
        moves.addAll(getRookMoves(row, col, piece)); // Add Rook's horizontal and vertical moves
        moves.addAll(getBishopMoves(row, col, piece)); // Add Bishop's diagonal moves
    
        return moves;
    }

    private List<int[]> getKingMoves(int row, int col, Piece piece) {
        List<int[]> moves = new ArrayList<>();
        
        // Directions: all 8 possible directions (vertical, horizontal, diagonal)
        int[][] kingMoves = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1},  // Vertical and horizontal
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1} // Diagonal
        };
    
        for (int[] move : kingMoves) {
            int newRow = row + move[0];
            int newCol = col + move[1];
    
            // Check if the move is within bounds and valid
            if (isWithinBounds(newRow, newCol) && isValidMove(row, col, newRow, newCol)) {
                moves.add(new int[]{newRow, newCol});
            }
        }
    
        return moves;
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
    
        boolean hasCapture = hasMandatoryCapture(currentPlayer, board);

    
        // If a capture is mandatory, allow only capturing moves
        if (hasCapture) {
            Piece movingPiece = board[startRow][startCol];

            if (movingPiece == null || !movingPiece.canMove(startRow, startCol, endRow, endCol, board)) {
                System.out.println("Invalid move! You must make a capturing move.");
                return false;
            }
            if (board[endRow][endCol] == null || board[endRow][endCol].getColor() == movingPiece.getColor()) {
                System.out.println("Invalid move! You must make a capturing move.");
                return false;
            }


            if (canCapture(piece, startRow, startCol, board)) {
                System.out.println("A piece has mandatory capture" );
                return piece.canMove(startRow, startCol, endRow, endCol, board);
            } else {
                System.out.println("Capture is mandatory, but this move doesn't capture.");
                return false;
            }
        } else {
            // If no captures are mandatory, allow any valid move according to the piece's rules
            if (!piece.canMove(startRow, startCol, endRow, endCol, board)) {
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
        ui.updateBoard(board);
    }
    
    public boolean handleMove(int startRow, int startCol, int endRow, int endCol) {
        Piece piece = board[startRow][startCol];

        if (gameOver) {
            System.out.println("Game is over. No more moves allowed.");
            return false;
        }

        // Check if it's the current player's turn and if the move is valid
        if (piece != null && piece.getColor() == currentPlayer && piece.isValidMove(startRow, startCol, endRow, endCol, board)) {
            board[endRow][endCol] = piece;  // Move the piece
            board[startRow][startCol] = null;  // Clear the original square

            // Record the move
            recordMove(startRow, startCol, endRow, endCol, piece);

            if (board[endRow][endCol] instanceof Piece) {
                checkPawnPromotion(endRow, endCol);
            }

            // Alternate between players
            switchPlayer();
    
            // Check if the next player has valid moves or if the game should end
            checkGameEnd();

            // Update the UI after the move
            SwingUtilities.invokeLater(() -> ui.updateBoard(board));

            return true;
        }

        return false;
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

private List<Move> moveHistory = new ArrayList<>();

    // Method to return the last move made
    public Move getLastMove() {
        if (moveHistory.isEmpty()) {
            return null; // No moves have been made yet
        }
        return moveHistory.get(moveHistory.size() - 1);
    }

    // Add this method to record a move after it's successfully made
    private void recordMove(int startRow, int startCol, int endRow, int endCol, Piece piece) {
        Move move = new Move(startRow, startCol, endRow, endCol, piece);
        moveHistory.add(move);
    }

}