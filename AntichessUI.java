import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AntichessUI {
    private ChessBoard chessBoard;
    private JButton[][] boardButtons; // 8x8 array of buttons representing the board
    private int[] selectedSquare = null; // To store the selected square (piece to move)

    // Constructor to set up the UI
    public AntichessUI() {
        initializeUI(); // Create and set up the GUI
        chessBoard = new ChessBoard(this); // Pass UI reference to the ChessBoard
        
        chessBoard.startGame(); // Start the game
    }

    // Method to initialize the UI
    private void initializeUI() {
        JFrame frame = new JFrame("Antichess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        JPanel panel = new JPanel(new GridLayout(8, 8));

        loadImages();

        // Create board buttons and set up action listeners
        boardButtons = new JButton[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton button = new JButton();
                boardButtons[row][col] = button;

                if ((row + col) % 2 == 0) {
                    button.setBackground(Color.GRAY); // Dark square
                } else {
                    button.setBackground(Color.WHITE);// Light square
                    
                }

                button.setOpaque(true);
                button.setBorderPainted(false);

                // Add action listener to each button to handle user clicks
                final int currentRow = row;
                final int currentCol = col;
                button.addActionListener(e -> handleBoardClick(currentRow, currentCol));

                panel.add(button); // Add each button to the panel
            }
        }

        frame.add(panel);
        frame.setVisible(true);
    }

    // Method to handle board button clicks
    private void handleBoardClick(int row, int col) {
        if (selectedSquare == null) {
            System.out.println("Piece Selected");
            selectedSquare = new int[]{row, col};
            List<int[]> validMoves = chessBoard.getValidMoves(row, col); // Get valid moves for the clicked piece
            highlightMoves(validMoves); // Highlight the possible moves
        } else {
            // Second click: attempt to move the piece
            boolean moveSuccessful = chessBoard.handleMove(selectedSquare[0], selectedSquare[1], row, col);
            if (moveSuccessful) {
                selectedSquare = null; // Reset after a successful move
            } else {
                // Handle invalid move (optional feedback to the user)
                selectedSquare = null; // Reset after an invalid attempt
                System.out.println("Move Not Successful");
            }
        }
    }

    // Method to update the board buttons based on the current state of the chessboard
    public void updateBoard(Piece[][] board) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];;
                 if (piece != null) {
                        int index = piece.getType().ordinal() + (piece.getColor() == Piece.Color.WHITE ? 0 : 6);
                        boardButtons[row][col].setIcon(scaleImageIcon(pieceImages[index], boardButtons[row][col].getWidth(), boardButtons[row][col].getHeight()));
                } else {
                        boardButtons[row][col].setIcon(null); // Clear icon for empty squares
                }
            }
        }
    }

    private ImageIcon[] pieceImages = new ImageIcon[12];

    private void loadImages() {
        pieceImages[0] = new ImageIcon("/Users/ekuo25/MyScripts/SSP_Anti-Chess/WhiteKing.png");
        pieceImages[1] = new ImageIcon("/Users/ekuo25/MyScripts/SSP_Anti-Chess/WhiteQueen.png");
        pieceImages[2] = new ImageIcon("/Users/ekuo25/MyScripts/SSP_Anti-Chess/WhiteRook.png");
        pieceImages[3] = new ImageIcon("/Users/ekuo25/MyScripts/SSP_Anti-Chess/WhiteBishop.png");
        pieceImages[4] = new ImageIcon("/Users/ekuo25/MyScripts/SSP_Anti-Chess/WhiteKnight.png");      
        pieceImages[5] = new ImageIcon("/Users/ekuo25/MyScripts/SSP_Anti-Chess/WhitePawn.png");
        pieceImages[6] = new ImageIcon("/Users/ekuo25/MyScripts/SSP_Anti-Chess/BlackKing.png");
        pieceImages[7] = new ImageIcon("/Users/ekuo25/MyScripts/SSP_Anti-Chess/BlackQueen.png");
        pieceImages[8] = new ImageIcon("/Users/ekuo25/MyScripts/SSP_Anti-Chess/BlackRook.png");
        pieceImages[9] = new ImageIcon("/Users/ekuo25/MyScripts/SSP_Anti-Chess/BlackBishop.png");
        pieceImages[10] = new ImageIcon("/Users/ekuo25/MyScripts/SSP_Anti-Chess/BlackKnight.png");
        pieceImages[11] = new ImageIcon("/Users/ekuo25/MyScripts/SSP_Anti-Chess/BlackPawn.png");
    }

    private ImageIcon scaleImageIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }

    public void highlightMoves(List<int[]> validMoves) {
        // Reset the board first
        resetBoardColors();
        
        // Highlight the valid moves
        for (int[] move : validMoves) {
            int row = move[0];
            int col = move[1];
            boardButtons[row][col].setBackground(Color.GREEN); // Use green for valid moves
        }
    }



    public void resetBoardColors() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                // Reset to default colors (e.g., white and gray for a chessboard pattern)
                if ((row + col) % 2 == 0) {
                    boardButtons[row][col].setBackground(Color.GRAY);
                } else {
                    boardButtons[row][col].setBackground(Color.WHITE);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AntichessUI::new);
    }
}
