import javax.swing.JFrame;

public class Game {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Campus Quest");
        GamePanel gamePanel = new GamePanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(gamePanel);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        gamePanel.startGame();
    }
}
 