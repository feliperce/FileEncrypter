import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class EncryptForm {
    private JTextField filePathTextField;
    private JButton encryptButton;
    private JPanel mainPanel;
    private JTextField extensionTextField;
    private JButton decryptButton;
    private JButton fileButton;
    private JTextField keyTextField;

    private int selectedFileType = 0;

    public EncryptForm() {
        fileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                selectedFileType = fileChooser.showOpenDialog(fileButton);
                File selectedFile = fileChooser.getSelectedFile();
                filePathTextField.setText(selectedFile.getAbsolutePath());
            }
        });


        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validadeFields();
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    private boolean validadeFields() {
        boolean isValid = false;
        String key = keyTextField.getText();
        String filePath = filePathTextField.getText();

        if (filePath.isEmpty()) {
            JOptionPane.showMessageDialog(keyTextField, "Enter the file path", "ERROR", JOptionPane.ERROR_MESSAGE);
            isValid = false;
        }

        if (key.isEmpty()) {
            JOptionPane.showMessageDialog(keyTextField, "Enter the key", "ERROR", JOptionPane.ERROR_MESSAGE);
            isValid = false;
        }

        if (key.length() != 16  && key.length() != 24 && key.length() != 32) {
            JOptionPane.showMessageDialog(keyTextField, "The key needs to be 16, 24 or 32 bytes", "ERROR", JOptionPane.ERROR_MESSAGE);
            isValid = false;
        }

        return isValid;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("File Encrypter");
        frame.setContentPane(new EncryptForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
