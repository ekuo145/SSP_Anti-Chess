import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {
    private PlayerType currentPlayer = PlayerType.WHITE;
    private ChessBoard chessBoard;

    public Player(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
        this.currentPlayer = PlayerType.WHITE; 
    }

    public enum PlayerType {
        WHITE,
        BLACK;

        public Piece.Color getColor() {
            if (this == WHITE) {
                return Piece.Color.WHITE;
            } else {
                return Piece.Color.BLACK;
            }
        }
    }

    public List<Move> getLegalMoves(Piece.Color playerColor) {
        List<Move> legalMoves = new ArrayList<>();
        
        // Iterate through the board to find all pieces of the current player
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = chessBoard.getPieceAt(row, col); // Assuming `chessBoard` has this method
                if (piece != null && piece.getColor() == playerColor) {
                    // Get all potential moves for this piece and validate them
                    List<Move> candidateMoves = piece.generatePossibleMoves(row, col);
                    for (Move move : candidateMoves) {
                        int startRow = move.getFromRow();
                        int startCol = move.getFromCol();
                        int endRow = move.getToRow();
                        int endCol = move.getToCol();
                        if (chessBoard.isValidMove(startRow, startCol, endRow, endCol)) { // Efficient validation for each move
                            legalMoves.add(move);
                        }
                    }
                }
            }
        }
        return legalMoves;
    }
    


    public boolean isBotTurn() {
        // Assume the bot is always the BLACK player
        return currentPlayer == PlayerType.BLACK;
    }

    /**
     * Switches the turn to the other player.
     */
    public void switchTurn() {
        currentPlayer = (currentPlayer == PlayerType.WHITE) ? PlayerType.BLACK : PlayerType.WHITE;
    }

    /**
     * Makes a random move for the bot.
     */
    public void makeRandomMove(Piece[][] board) {
        Random rand = new Random();
    
        // Get all legal moves for the current player
        List<Move> possibleMoves = getLegalMoves(currentPlayer.getColor());
    
        // If there are valid moves, randomly select one and execute it
        if (!possibleMoves.isEmpty()) {
            Move randomMove = possibleMoves.get(rand.nextInt(possibleMoves.size()));
            chessBoard.handleMove(randomMove); // Execute the selected move
        } else {
            System.out.println("No legal moves available for the bot.");
        }
    }    

    public boolean isGameOver() {
        return getLegalMoves(currentPlayer.getColor()).isEmpty();
    }
}