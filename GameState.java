import java.util.List;

public class GameState {
    private ChessBoard chessBoard;
    private Player currentPlayer;

    /**
     * Initializes the game state with a new ChessBoard and sets the first player.
     */
    public GameState() {
        this.chessBoard = new ChessBoard();
        this.currentPlayer = Player.WHITE;  // Assuming WHITE always starts
        initializeBoard();
    }

    /**
     * Initializes the board with pieces in their starting positions.
     */
    private void initializeBoard() {
        chessBoard.setUpPieces();  // Assuming this method sets up the initial positions on the board
    }

    /**
     * Returns a list of legal moves for the current player.
     * Assumes board has a method to get all legal moves for a player.
     * 
     * @return a list of legal moves
     */
    public List<Move> getLegalMoves() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                Piece piece = board[row][col];
                if (piece != null && piece.getColor() == playerColor) {
                    for (int endRow = 0; endRow < board.length; endRow++) {
                        for (int endCol = 0; endCol < board[endRow].length; endCol++) {
        return board.getValidMoves(currentPlayer);
            }
        }
    }

    /**
     * Makes the given move, updates the board and switches the turn to the other player.
     * 
     * @param move The move to make
     */
    public void makeMove(Move move) {
        chessBoard.handleMove(move);
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
        chessBoard.display();
    }
}
