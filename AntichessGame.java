public class AntichessGame {

    public static void main(String[] args) {
        GameState game = new GameState();
        RandomBot bot = new RandomBot();
        ChessBoard chessBoard = new ChessBoard(game);

        while (!chessBoard.isGameOver()) {
            if (game.isBotTurn()) {
                bot.makeRandomMove(game);
            } else {
            }
        }
        System.out.println("Game over!");
    }
}