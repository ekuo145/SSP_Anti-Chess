import java.util.Random;

public class Player {
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
    public void makeRandomMove(ChessBoard board) {
        Random random = new Random();
        // Implement logic to make a random move on the board
        // This is a placeholder for the actual move logic
        // Example: board.movePiece(randomMove);
    }
}