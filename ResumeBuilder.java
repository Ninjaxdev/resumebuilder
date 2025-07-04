import java.awt.*;
import java.io.*;
import javax.swing.*;

public class ResumeBuilder extends JFrame {
    private JTextField nameField, emailField, phoneField;
    private JTextArea educationArea, skillsArea, experienceArea, outputArea;
    private JButton generateButton, clearButton, saveButton;
    private JTabbedPane tabbedPane;

    public ResumeBuilder() {
        setTitle("Resume Builder");
        setSize(600, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();
        JPanel inputPanel = new JPanel(new GridLayout(8, 2));
        JPanel outputPanel = new JPanel(new BorderLayout());

        // Input Fields
        nameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        educationArea = new JTextArea(3, 20);
        skillsArea = new JTextArea(3, 20);
        experienceArea = new JTextArea(3, 20);

        // Input Form Layout
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Email:"));
        inputPanel.add(emailField);
        inputPanel.add(new JLabel("Phone:"));
        inputPanel.add(phoneField);
        inputPanel.add(new JLabel("Education:"));
        inputPanel.add(new JScrollPane(educationArea));
        inputPanel.add(new JLabel("Skills:"));
        inputPanel.add(new JScrollPane(skillsArea));
        inputPanel.add(new JLabel("Experience:"));
        inputPanel.add(new JScrollPane(experienceArea));

        // Buttons
        generateButton = new JButton("Generate Resume");
        clearButton = new JButton("Clear All");
        inputPanel.add(generateButton); inputPanel.add(clearButton);

        // Output Panel
        outputArea = new JTextArea(15, 50);
        outputArea.setEditable(false);
        outputPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        saveButton = new JButton("Save to File");
        outputPanel.add(saveButton, BorderLayout.SOUTH);

        // Tabs
        tabbedPane.addTab("Input", inputPanel);
        tabbedPane.addTab("Resume", outputPanel);
        add(tabbedPane);

        // Button Actions
        generateButton.addActionListener(e -> {
            if (validateForm()) {
                generateResume();
                tabbedPane.setSelectedIndex(1);
            }
        });

        clearButton.addActionListener(e -> clearForm());

        saveButton.addActionListener(e -> saveToFile());
    }

    // Form Validation
    private boolean validateForm() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()
                || educationArea.getText().trim().isEmpty()
                || skillsArea.getText().trim().isEmpty()
                || experienceArea.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!email.matches("^\\S+@\\S+\\.\\S+$")) {
            JOptionPane.showMessageDialog(this, "Invalid email format.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!phone.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Phone number must be 10 digits.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    // Resume Generation
    private void generateResume() {
        String resume = "Resume:\n" +
                "-------------------------------\n" +
                "Name: " + nameField.getText() + "\n" +
                "Email: " + emailField.getText() + "\n" +
                "Phone: " + phoneField.getText() + "\n\n" +
                "Education:\n" + educationArea.getText() + "\n\n" +
                "Skills:\n" + skillsArea.getText() + "\n\n" +
                "Experience:\n" + experienceArea.getText() + "\n";
        outputArea.setText(resume);
    }

    // Clear Form Fields
    private void clearForm() {
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        educationArea.setText("");
        skillsArea.setText("");
        experienceArea.setText("");
    }

    // Save Resume to Text File
    private void saveToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Resume");
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (PrintWriter writer = new PrintWriter(fileToSave)) {
                writer.println(outputArea.getText());
                JOptionPane.showMessageDialog(this, "Resume saved successfully.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ResumeBuilder frame = new ResumeBuilder();
            frame.setVisible(true);
        });
    }
}
