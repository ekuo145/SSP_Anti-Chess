import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AntichessUI {
    private ChessBoard chessBoard;
    private JButton[][] boardButtons; // 8x8 array of buttons representing the board
    private int[] selectedSquare = null; // To store the selected square (piece to move)

    // Constructor to set up the UI
    public AntichessUI() {
        chessBoard = new ChessBoard(this); // Pass UI reference to the ChessBoard
        initializeUI(); // Create and set up the GUI
        chessBoard.startGame(); // Start the game
    }

    // Method to initialize the UI
    private void initializeUI() {
        JFrame frame = new JFrame("Antichess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        JPanel panel = new JPanel(new GridLayout(8, 8));

        // Create board buttons and set up action listeners
        boardButtons = new JButton[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton button = new JButton();
                boardButtons[row][col] = button;

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
            // First click: select a piece
            selectedSquare = new int[]{row, col};
        } else {
            // Second click: attempt to move the piece
            boolean moveSuccessful = chessBoard.handleMove(selectedSquare[0], selectedSquare[1], row, col);
            if (moveSuccessful) {
                selectedSquare = null; // Reset after a successful move
            } else {
                // Handle invalid move (optional feedback to the user)
                selectedSquare = null; // Reset after an invalid attempt
            }
        }
    }

    // Method to update the board buttons based on the current state of the chessboard
    public void updateBoard(Piece[][] board) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece != null) {
                    boardButtons[row][col].setText(piece.getSymbol());
                } else {
                    boardButtons[row][col].setText("");
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AntichessUI::new);
    }
}
