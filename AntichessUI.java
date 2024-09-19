import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AntichessUI {
    private JFrame frame;
    private JButton[][] boardButtons; // 8x8 grid of buttons representing the board
    private ChessBoard chessBoard; // The ChessBoard model to manage the game logic
    private boolean isPieceSelected = false;
    private int selectedRow = -1;
    private int selectedCol = -1;

    public AntichessUI() {
        chessBoard = new ChessBoard(); // Initialize the chessboard
        initializeUI();
    }

    // Initialize the main UI components
    private void initializeUI() {
        frame = new JFrame("Antichess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new GridLayout(8, 8)); // 8x8 grid for the chessboard

        boardButtons = new JButton[8][8];

        // Initialize board buttons
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton button = new JButton();
                button.setFont(new Font("Arial", Font.BOLD, 24));
                boardButtons[row][col] = button;
                frame.add(button);
                button.addActionListener(new ButtonClickListener(row, col));
            }
        }

        updateBoard(); // Draw the initial board setup
        frame.setVisible(true);
    }

    // Update the board display based on the current game state
    private void updateBoard() {
        Piece[][] board = chessBoard.getBoard(); // Assuming ChessBoard has a getBoard method
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

    // Handle button clicks for piece selection and movement
    private class ButtonClickListener implements ActionListener {
        private int row, col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!isPieceSelected) {
                // First click: select a piece
                if (chessBoard.getBoard()[row][col] != null) {
                    isPieceSelected = true;
                    selectedRow = row;
                    selectedCol = col;
                    boardButtons[row][col].setBackground(Color.YELLOW); // Highlight the selected piece
                }
            } else {
                // Second click: move the selected piece
                if (chessBoard.makeMove(selectedRow, selectedCol, row, col)) {
                    updateBoard();
                } else {
                    System.out.println("Invalid move!");
                }
                // Reset selection
                boardButtons[selectedRow][selectedCol].setBackground(null); // Remove highlight
                isPieceSelected = false;
                selectedRow = -1;
                selectedCol = -1;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AntichessUI::new);
    }
}
