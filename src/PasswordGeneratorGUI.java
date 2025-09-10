import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;


public class PasswordGeneratorGUI extends JFrame {

    // GUI Components
    private JTextField lengthField;
    private JCheckBox lowercaseBox, uppercaseBox, numbersBox, symbolsBox;
    private JTextArea passwordArea;
    private JLabel strengthLabel;
    private JButton generateButton;

    public PasswordGeneratorGUI() {

        // Set title
        setTitle("Password Generator v2.0");

        // Manage close
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set window size
        setSize(450, 450);
        setResizable(false);

        // Center Screen
        setLocationRelativeTo(null);

        initializeComponents();
        layoutComponents();

        // Make the window visible
        setVisible(true);
    }

    private void initializeComponents() {
        lengthField = new JTextField("12", 5);

        // Character type checkboxes
        lowercaseBox = new JCheckBox("Lowercase letters (a-z)", true);
        uppercaseBox = new JCheckBox("Uppercase letters (A-Z)", true);
        numbersBox = new JCheckBox("Numbers (0-9)", true);
        symbolsBox = new JCheckBox("Symbols (!@#$%^&*)", true);

        // Password display
        passwordArea = new JTextArea(3,30);
        passwordArea.setEditable(false);
        passwordArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        passwordArea.setBorder(BorderFactory.createLoweredBevelBorder());
        passwordArea.setBackground(Color.WHITE);

        // Strength label
        strengthLabel = new JLabel("Password strength will appear here");
        strengthLabel.setHorizontalAlignment(JLabel.CENTER);

        // Generate password button
        generateButton = new JButton("Generate Password");
        generateButton.setPreferredSize(new Dimension(150, 30));
    }

    private void layoutComponents() {
        setLayout(new BorderLayout(10, 10));

        // Title panel
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Password Generator v2.0", JLabel.CENTER));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(topPanel, BorderLayout.NORTH);

        // Input Panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10,20, 10,20));

        // Length input
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 0, 5, 10);
        centerPanel.add(new JLabel("Password Length:"), gbc);

        gbc.gridx = 1;
        centerPanel.add(lengthField, gbc);

        // Character Checkboxes
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 0, 5, 0);
        centerPanel.add(new JLabel("Include:"), gbc);

        gbc.gridy = 2; gbc.insets = new Insets(2, 0, 2, 0);
        centerPanel.add(lowercaseBox, gbc);

        gbc.gridy = 3;
        centerPanel.add(uppercaseBox, gbc);
        
        gbc.gridy = 4;
        centerPanel.add(numbersBox, gbc);

        gbc.gridy = 5;
        centerPanel.add(symbolsBox, gbc);

        // Generate password button
        gbc.gridy = 6;
        gbc.insets = new Insets(15, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(generateButton, gbc);

        add(centerPanel, BorderLayout.CENTER);

        // Results panel
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        bottomPanel.add(new JLabel("Generated Password:"), BorderLayout.NORTH);
        bottomPanel.add(new JScrollPane(passwordArea), BorderLayout.CENTER);
        bottomPanel.add(strengthLabel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {

        // Call application
        SwingUtilities.invokeLater(() -> new PasswordGeneratorGUI());
    }
}