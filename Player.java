import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {
    private PlayerType currentPlayer = PlayerType.WHITE;
    private Piece.Color color = currentPlayer.getColor();
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

    public List<Move> getLegalMoves(Piece.Color color) {
        List<Move> legalMoves = new ArrayList<>();
        if (chessBoard == null) {
            System.err.println("Error: ChessBoard is not initialized.");
            return legalMoves;
        }

        Piece[][] board = chessBoard.getBoard();

        for (int startRow = 0; startRow < 8; startRow++) {
            for (int startCol = 0; startCol < 8; startCol++) {
                Piece piece = board[startRow][startCol];
                if (piece != null && piece.getColor() == color) {
                    for (int endRow = 0; endRow < 8; endRow++) {
                        for (int endCol = 0; endCol < 8; endCol++) {
                            Move move = new Move(startRow, startCol, endRow, endCol, piece);
                            if (piece.canMove(startRow, startCol, endRow, endCol, board)) {
                                legalMoves.add(move);
                            }
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
        int startRow, startCol, endRow, endCol;
        boolean moveMade = false;

        List<Move> possibleMoves = getLegalMoves(currentPlayer.getColor());
        System.out.println(possibleMoves);
        if (!possibleMoves.isEmpty()) {
            Move randomMove = possibleMoves.get(rand.nextInt(possibleMoves.size()));
            if (randomMove != null) {
            chessBoard.handleMove(randomMove);
        }
        }

        while (!moveMade) {
            startRow = rand.nextInt(8);
            startCol = rand.nextInt(8);
            endRow = rand.nextInt(8);
            endCol = rand.nextInt(8);
            Piece piece = board[startRow][startCol];
            if (piece != null && piece.getColor() == this.color) {
                Move move = new Move(startRow, startCol, endRow, endCol, piece);
                if (this.chessBoard != null && this.chessBoard.handleMove(move)) {
                    moveMade = true;
                }
            }
        }
    }

    public boolean isGameOver() {
        return getLegalMoves(currentPlayer.getColor()).isEmpty();
    }
}