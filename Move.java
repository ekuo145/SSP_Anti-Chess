public class Move {
    int startRow;
    int startCol;
    int endRow;
    int endCol;
    Piece movedPiece;

    public Move(int startRow, int startCol, int endRow, int endCol, Piece movedPiece) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
        this.movedPiece = movedPiece;
    }

    public boolean isPawnMove() {
        if (movedPiece instanceof Piece) {
            if (movedPiece.getType() == Piece.PieceType.PAWN) {
                return true;
            }
        }
            return false;
    }

    public int getFromRow() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFromRow'");
    }

    public int getFromCol() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFromCol'");
    }

    public int getToRow() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getToRow'");
    }

    public int getToCol() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getToCol'");
    }
}