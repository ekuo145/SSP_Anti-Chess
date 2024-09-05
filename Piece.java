// Define the Piece class and related enums
public class Piece {
    // Enum for piece type
    public enum PieceType {
        KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN;
    }

    // Enum for piece color
    public enum Color {
        WHITE, BLACK;
    }

    private PieceType type;
    private Color color;

    // Constructor for creating a piece
    public Piece(PieceType type, Color color) {
        this.type = type;
        this.color = color;
    }

    // Getters for type and color
    public PieceType getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return color + " " + type;
    }
}