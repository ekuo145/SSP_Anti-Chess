public class AntichessGame {
    public static void main(String[] args) {
        ChessBoard chessBoard = new ChessBoard();
        chessBoard.printBoard();

        // Test movement of a black pawn from (1, 0) to (3, 0)
        System.out.println("Move from (1, 0) to (3, 0) [Black Pawn Two-Square Move]: " + chessBoard.isValidMove(1, 0, 3, 0));  // Should be true (pawn double move)

        System.out.println("Move from (1, 0) to (2, 0) [Black Pawn One-Square Move]: " + chessBoard.isValidMove(1, 0, 2, 0));  // Should be true (pawn double move)
        
        //problem with pawn movements for some reason
        // Test movement of a white pawn from (6, 0) to (4, 0)
        System.out.println("Move from (6, 0) to (4, 0) [White Pawn Two-Square Move]: " + chessBoard.isValidMove(6, 0, 4, 0));  // Should be true (pawn double move)

        System.out.println("Move from (0,1) to (2,2) [Black Knight Two-Square More]: " + chessBoard.isValidMove(0, 1, 2, 2));

        CurrentPlayer.printTurn();
    }
}