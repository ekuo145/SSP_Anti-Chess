import java.util.List;
import java.util.Random;

public class RandomBot {

    private Random random;

    public RandomBot() {
        this.random = new Random();
    }

    /**
     * Selects a random legal move from the list of available moves.
     * 
     * @param game The current game state, assumed to have getLegalMoves() and makeMove() methods.
     */
    public void makeRandomMove(GameState game) {
        // Retrieve all legal moves for the bot's current position
        List<Move> legalMoves = game.getLegalMoves();

        // Check if there are any legal moves available
        class Move {
            private int fromRow;
            private int fromCol;
            private int toRow;
            private int toCol;

            // Constructor and other methods

            public int getFromRow() {
                return fromRow;
            }

            // Other getter methods

            // Rest of the code
        }
    }
}
