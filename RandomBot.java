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
        if (legalMoves.isEmpty()) {
            System.out.println("No legal moves available. Game over.");
            return;
        }

        // Select a random move from the list
        Move selectedMove = legalMoves.get(random.nextInt(legalMoves.size()));

        // Make the selected move
        ChessBoard.handleMove(selectedMove);

        // Output the chosen move for debugging purposes
        System.out.println("Bot chose move: " + selectedMove);
    }
}
