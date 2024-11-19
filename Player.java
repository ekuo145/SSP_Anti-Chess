import java.util.ArrayList;
import java.util.List;
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

    public List<Move> getLegalMoves(Piece.Color color) {
        List<Move> legalMoves = new ArrayList<>();
        // Add logic to calculate legal moves for the piece
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
    public void makeRandomMove(ChessBoard board) {
        Random random = new Random();
        List<Move> possibleMoves = getLegalMoves(currentPlayer.getColor());
        if (!possibleMoves.isEmpty()) {
            Move randomMove = possibleMoves.get(random.nextInt(possibleMoves.size()));
            board.handleMove(randomMove);
        }
    }

    public boolean isGameOver() {
        return getLegalMoves(currentPlayer.getColor()).isEmpty();
    }
}