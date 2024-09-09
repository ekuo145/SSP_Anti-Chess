public class CurrentPlayer {
    // Start with White's turn

    public void switchPlayer() {
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private void printTurn() {
        System.out.println("It's " + (currentPlayer == Color.WHITE ? "White" : "Black") + "'s turn.");
    }
    
}