import javax.swing.JFrame;
import java.awt.Font;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame(); // Window frame
        GamePlay gamePlay = new GamePlay(); // Main Game Logic

        frame.setSize(705,602);
        frame.setFont(new Font("System", Font.BOLD, 14));
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(gamePlay);

        frame.setTitle("BrickBreaker");
    }
}
