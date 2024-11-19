public class Player {
    private static Piece.Color currentPlayer = Piece.Color.WHITE;
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
}
