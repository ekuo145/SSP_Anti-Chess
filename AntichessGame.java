public class AntichessGame {

    // Initialize players and pass the ChessBoard to them
        Player whitePlayer = new Player(Piece.Color.WHITE, false, null);
        Player blackPlayer = new Player(Piece.Color.BLACK, true, null);

        // Example: set the chessboard after construction (optional)
        whitePlayer.setChessBoard(board);
        blackPlayer.setChessBoard(board);

    public static void main(String[] args) {
    boolean gameOver = false;
    GameManager gameManager = new GameManager(whitePlayer, blackPlayer);

    while (!gameOver) {
    Player currentPlayer = gameManager.getCurrentPlayer();

    if (currentPlayer.isBot()) {
        currentPlayer.makeRandomMove();
    } else {
        Move playerMove = getPlayerInput();
        if (chessBoard.handleMove(playerMove, gameManager)) {
            System.out.println("Move executed!");
        } else {
            System.out.println("Invalid move. Try again.");
            continue;
        }
    }

    if (isGameOver()) {
        gameOver = true;
        System.out.println("Game Over! Winner: " +
            (gameManager.getCurrentPlayer().getColor() == Piece.Color.WHITE ? "Black" : "White"));
    }
}
    }
}
