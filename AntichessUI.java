import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AntichessUI {
    private ChessBoard chessBoard;
    private JButton[][] boardButtons; // 8x8 array of buttons representing the board
    private int[] selectedSquare = null; // To store the selected square (piece to move)
    private JTextArea moveHistoryArea; // Text area to store and display the move history
    private JScrollPane scrollPane;    // Scroll pane to allow scrolling through move history

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
        frame.setSize(1000, 800); // Adjust to fit the board and labels snugly
        frame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout()); // Use BorderLayout for main panel
        JPanel boardPanel = new JPanel(new GridLayout(9,9));
        // Create board buttons and set up action listeners
        boardButtons = new JButton[8][8];

        // Add the top-left empty corner
        boardPanel.add(new JLabel("")); // Top-left corner is empty

        char[] columns = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        for (char column : columns) {
            JLabel colLabel = new JLabel(Character.toString(column), SwingConstants.CENTER);
            colLabel.setPreferredSize(new Dimension(80, 80)); // Adjust to match button size
            boardPanel.add(colLabel); // Add to the top row
        }

        loadImages();
        
        
        // Add row labels and board buttons
        for (int row = 0; row < 8; row++) {
            // Add row label on the left side
            JLabel rowLabel = new JLabel(Integer.toString(1 + row), SwingConstants.CENTER); // Row label (reverse order)
            rowLabel.setPreferredSize(new Dimension(80, 80)); // Adjust size to match the board buttons
            boardPanel.add(rowLabel);

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
                button.setPreferredSize(new Dimension(80, 80)); // Make sure the buttons are square and compact

                // Add action listener to each button to handle user clicks
                final int currentRow = row;
                final int currentCol = col;
                button.addActionListener(e -> handleBoardClick(currentRow, currentCol));

                boardPanel.add(button);
            }
        }

        // Create and set up the move history area
        moveHistoryArea = new JTextArea(15, 20); // 15 rows, 20 columns for display
        moveHistoryArea.setEditable(false); // Users cannot edit move history
        moveHistoryArea.setFont(new Font("Arial", Font.PLAIN, 12)); // Set font style

        // Add a scroll pane for the move history area
        scrollPane = new JScrollPane(moveHistoryArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Add components to the main panel
        mainPanel.add(boardPanel, BorderLayout.CENTER); // Add the board to the center
        mainPanel.add(scrollPane, BorderLayout.EAST);   // Add move history to the right

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // Method to handle board button clicks
    private void handleBoardClick(int row, int col) {
        if (selectedSquare == null) {
            System.out.println("Piece Selected");
            selectedSquare = new int[]{row, col};
            List<int[]> validMoves = chessBoard.getValidMoves(row, col);
            System.out.println(validMoves);
            chessBoard.highlightMoves(validMoves);
        } else {
            // Second click: attempt to move the piece
            boolean moveSuccessful = chessBoard.handleMove(selectedSquare[0], selectedSquare[1], row, col);
            if (moveSuccessful) {
                // Update the move history after a successful move
                addMoveToHistory(selectedSquare[0], selectedSquare[1], row, col);
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
                        int index = piece.getType().ordinal() + (piece.getColor() == Piece.Color.WHITE ? 0 : 6); //don't really understand this part, could be what is causing display issues
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

    private void addMoveToHistory(int startRow, int startCol, int endRow, int endCol) {
        char[] columns = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        String move = String.format("%s%d -> %s%d", columns[startCol], 1 + startRow, columns[endCol], 1 + endRow);
        moveHistoryArea.append(move + "\n"); // Append move to history area
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AntichessUI::new);
    }
}
