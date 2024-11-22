public class AntichessGame {

    public static void main(String[] args) {
        AntichessUI ui = new AntichessUI();
        ChessBoard chessBoard = new ChessBoard(ui);
        Player player = new Player(chessBoard);

        while (!chessBoard.isGameOver()) {
            if (player.isBotTurn()) {
                player.makeRandomMove(null);
            } else {
            }
        }
        System.out.println("Game over!");
    }
}