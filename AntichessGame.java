public class AntichessGame {
    public static void main(String[] args) {
        // Launch the Antichess UI
        javax.swing.SwingUtilities.invokeLater(() -> {
            new AntichessUI(); // Start the UI and the game
        });
    }
}