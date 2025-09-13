import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard ;
import java.util.Random;


public class PasswordGeneratorGUI extends JFrame {

    // Hardcoded character pools
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-_=+[]{}|;:,.<>?";  

    // GUI Components
    private JTextField lengthField;
    private JCheckBox lowercaseBox, uppercaseBox, numbersBox, symbolsBox;
    private JTextArea passwordArea;
    private JLabel strengthLabel;
    private JButton generateButton;
    private JButton copyButton;

    public PasswordGeneratorGUI() {

        // Set title
        setTitle("Password Generator v2.0");

        // Manage close
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set window size
        setSize(450, 500);
        setResizable(false);

        // Center Screen
        setLocationRelativeTo(null);

        initializeComponents();
        layoutComponents();
        addEventListeners();

        // Make the window visible
        setVisible(true);
    }

    private void initializeComponents() {
        lengthField = new JTextField("12", 10);

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

        // Copy button
        copyButton = new JButton("Copy to Clipboard");
        copyButton.setPreferredSize(new Dimension(150, 30));
        copyButton.setEnabled(false);
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
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(generateButton);
        buttonPanel.add(copyButton);

        centerPanel.add(buttonPanel, gbc);

        add(centerPanel, BorderLayout.CENTER);

        // Results panel
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        bottomPanel.add(new JLabel("Generated Password:"), BorderLayout.NORTH);
        bottomPanel.add(new JScrollPane(passwordArea), BorderLayout.CENTER);
        bottomPanel.add(strengthLabel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void addEventListeners() {
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generatePassword();
            }
        });

        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copyToClipboard();
            }
        });
    }

    private void generatePassword() {
        try {

            // Get length from length field
            int length = Integer.parseInt(lengthField.getText());

            // Check to make sure length is greater than 4
            if (length < 4) {
                JOptionPane.showMessageDialog(this, "Password length must be at least 4 characters!", "Invalid Length", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Get checkbox inputs
            boolean hasLower = lowercaseBox.isSelected();
            boolean hasUpper = uppercaseBox.isSelected();
            boolean hasNumbers = numbersBox.isSelected();
            boolean hasSymbols = symbolsBox.isSelected();

            // Validate that at least one type is selected
            if (!hasLower && !hasUpper && !hasNumbers && !hasSymbols) {
                JOptionPane.showMessageDialog(this, "You must select at least one character type!", "No Character Types Selected", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Build character pool
            String characterPool = buildCharacterPool(hasLower, hasUpper, hasNumbers, hasSymbols);

            // Generate password
            String password = generatePasswordString(length, characterPool, hasLower, hasUpper, hasNumbers, hasSymbols);

            // Display results
            passwordArea.setText(password);
            strengthLabel.setText("Password Strength: " + getPasswordStrength(length, hasLower, hasUpper, hasNumbers, hasSymbols));

            // Endable copy button
            copyButton.setEnabled(true);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for the password length!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Function for copying generated password to clipboard
    private void copyToClipboard() {
        String password = passwordArea.getText();

        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No password to copy! Generate a password first.", "No Password", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection selection = new StringSelection(password);

            clipboard.setContents(selection, null);

            JOptionPane. showMessageDialog(this, "Password copied to clipboard!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to copy passwoed to clipboard: " + ex.getMessage(), "Copy Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Function builds character pool for the password based on the selected character types
    private String buildCharacterPool(boolean hasLower, boolean hasUpper, boolean hasNumbers, boolean hasSymbols) {
        StringBuilder pool = new StringBuilder();
        if (hasLower) pool.append(LOWERCASE);
        if (hasUpper) pool.append(UPPERCASE);
        if (hasNumbers) pool.append(NUMBERS);
        if (hasSymbols) pool.append(SYMBOLS);

        return pool.toString();
    }

    // Helper function used in the password generation that shuffles characters in a string
    private static String shuffleString(String input) {
        Random random = new Random();

        // Convert the string into a character array
        char[] characters = input.toCharArray();

        // For each index in the character array, swap it's value with another index's value
        for (int i = characters.length - 1; i > 0; i--) {
            int randomIndex = random.nextInt(i + 1);
            char temp = characters[i];
            characters[i] = characters[randomIndex];
            characters[randomIndex] = temp;
        }

        // Convert character array back to a string and return it
        return new String(characters);
    }

    private String generatePasswordString(int length, String characterPool, boolean hasLower, boolean hasUpper, boolean hasNumbers, boolean hasSymbols) {
        Random random = new Random();
        StringBuilder password = new StringBuilder();

        // Garentee inclusion of each selected character type
        if (hasLower) password.append(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        if (hasUpper) password.append(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        if (hasNumbers) password.append(NUMBERS.charAt(random.nextInt(NUMBERS.length())));
        if (hasSymbols) password.append(SYMBOLS.charAt(random.nextInt(SYMBOLS.length())));

        // Add random characters to the password until the desired length is achieved
        while (password.length() < length) {
            int randomIndex = random.nextInt(characterPool.length());
            password.append(characterPool.charAt(randomIndex));
        }

        // Return the shuffled password
        return shuffleString(password.toString());
    }

    // Function that calculates and returns the strength of the created password
    private static String getPasswordStrength(int length, boolean hasLower, boolean hasUpper, boolean hasNumbers, boolean hasSymbols) {
        int strength = 0;

        // Calculate strength score using password criteria
        if (length >= 8) strength++;
        if (length >= 12) strength++;
        if (hasLower) strength++;
        if (hasUpper) strength++;
        if (hasNumbers) strength++;
        if (hasSymbols) strength++;

        // Determine strength classification based on score
        if (strength <= 2) return "Weak";
        else if (strength <= 4) return "Medium";
        else return "Strong";
    }

    public static void main(String[] args) {

        // Call application
        SwingUtilities.invokeLater(() -> new PasswordGeneratorGUI());
    }
}