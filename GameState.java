import java.util.ArrayList;
import java.util.List;

public class GameState {
    private static PlayerType currentPlayer = PlayerType.WHITE;

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

    public List<Move> getLegalMoves() {
        List<Move> legalMoves = new ArrayList<>();
        // Add logic to calculate legal moves for the piece
        return legalMoves;
    }

    public boolean isBotTurn() {
        // For example, assume the bot is always the BLACK player
        return currentPlayer == PlayerType.BLACK;
    }

    /**
     * Switches the turn to the other player.
     */
    private void switchTurn() {
        currentPlayer = (currentPlayer == PlayerType.WHITE) ? PlayerType.BLACK : PlayerType.WHITE;
    }

    private ChessBoard chessBoard;
    private Piece[][] board = new Piece[8][8];

    /**
     * Initializes the game state with a new ChessBoard and sets the first player.
     */
    public GameState() {
        this.chessBoard = new ChessBoard(this);
        this.currentPlayer = currentPlayer;  // Assuming WHITE always starts
        chessBoard.startGame();
    }

    public boolean isGameOver() {
        // A simple check for Giveaway Chess: if the current player has no legal moves,
        // the game is over, as they cannot make any further moves.
        return getLegalMoves().isEmpty();
    }
}
