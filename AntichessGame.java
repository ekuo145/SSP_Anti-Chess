public class AntichessGame {

    public static void main(String[] args) {
        GameState game = new GameState();
        RandomBot bot = new RandomBot();

        while (!ChessBoard.isGameOver()) {
            if (game.isBotTurn()) {
                bot.makeRandomMove(game);
            } else {
                // Handle human player move or other logic
            }
        }
        System.out.println("Game over!");
    }
}