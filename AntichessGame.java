public class AntichessGame {
    public static void main(String[] args) {
        ChessBoard chessBoard = new ChessBoard();
        chessBoard.printBoard();

        // Test movement of a black pawn from (1, 0) to (3, 0)
        System.out.println("Move from (1, 0) to (3, 0)  [White Pawn Two-Square Move]: " + chessBoard.isValidMove(1, 1, 2, 1));  // Should be true (pawn double move)
        //problem with pawn movements for some reason
        

        ChessBoard.printTurn();
    }
}