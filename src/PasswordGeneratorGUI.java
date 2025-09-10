import javax.swing.*;


public class PasswordGeneratorGUI extends JFrame {

    public PasswordGeneratorGUI() {

        // Set title
        setTitle("Password Generator v2.0");

        // Manage close
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set window size
        setSize(400, 300);

        // Center Screen
        setLocationRelativeTo(null);

        //Make welcome label
        JLabel welcomeLabel = new JLabel("Welcome to the Passweord Generator v2.0!", JLabel.CENTER);
        add(welcomeLabel);

        // Make the window visible
        setVisible(true);
    }

    public static void main(String[] args) {

        // Call application
        SwingUtilities.invokeLater(() -> new PasswordGeneratorGUI());
    }
}