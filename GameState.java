import java.util.List;

public class GameState {
    private ChessBoard board;
    private Player currentPlayer;

    /**
     * Initializes the game state with a new ChessBoard and sets the first player.
     */
    public GameState() {
        this.board = new ChessBoard();
        this.currentPlayer = Player.WHITE;  // Assuming WHITE always starts
        initializeBoard();
    }

    /**
     * Initializes the board with pieces in their starting positions.
     */
    private void initializeBoard() {
        board.setupBoard();  // Assuming this method sets up the initial positions on the board
    }

    /**
     * Returns a list of legal moves for the current player.
     * Assumes board has a method to get all legal moves for a player.
     * 
     * @return a list of legal moves
     */
    public List<Move> getLegalMoves() {
        return board.getLegalMoves(currentPlayer);
    }

    /**
     * Makes the given move, updates the board and switches the turn to the other player.
     * 
     * @param move The move to make
     */
    public void makeMove(Move move) {
        board.executeMove(move);
        switchTurn();
    }

    /**
     * Checks if the game is over based on Giveaway Chess rules.
     * This is usually when a player has no legal moves left or has lost all pieces.
     * 
     * @return true if the game is over, otherwise false
     */
    public boolean isGameOver() {
        // A simple check for Giveaway Chess: if the current player has no legal moves,
        // the game is over, as they cannot make any further moves.
        return getLegalMoves().isEmpty();
    }

    /**
     * Checks if it's the bot's turn to play.
     * Assumes bot is always one of the players, either WHITE or BLACK.
     * 
     * @return true if it's the bot's turn, otherwise false
     */
    public boolean isBotTurn() {
        // For example, assume the bot is always the BLACK player
        return currentPlayer == Player.BLACK;
    }

    /**
     * Switches the turn to the other player.
     */
    private void switchTurn() {
        currentPlayer = (currentPlayer == Player.WHITE) ? Player.BLACK : Player.WHITE;
    }

    /**
     * Outputs the current state of the board for debugging or display purposes.
     */
    public void displayBoard() {
        board.display();
    }
}
