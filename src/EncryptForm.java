import encrypt.CryptoException;
import encrypt.CryptoUtils;

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
    private String outputPath = System.getProperty("user.home")+"/FileEncrypter/output";

    public EncryptForm() {
        makeOutputDir();

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
                if (validadeFields()) {
                    try {
                        File file = new File(filePathTextField.getText());
                        if (file.isDirectory()) {
                            encryptFilesFromFolder(file);
                        } else {
                            encryptFile(file);
                        }
                    } catch (CryptoException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    private String getExtension() {
        String extension;
        if (extensionTextField.getText().isEmpty()) {
            extension = ".crypt";
        } else {
            extension = extensionTextField.getText();
        }

        return extension;
    }

    private void encryptFile(File file) throws CryptoException {
        File outputFile = new File(outputPath);
        String fileOutputName = file.getName().replaceFirst("[.][^.]+$", "");
        String extension = getExtension();

        CryptoUtils.INSTANCE.encrypt(
                keyTextField.getText(),
                file,
                new File(outputPath+"/"+fileOutputName+extension));
    }

    private void encryptFilesFromFolder(File file) throws CryptoException {
        String extension = getExtension();
        File[] files = file.listFiles();
        String fileOutputPath = outputPath+"/"+file.getName();
        File outputPath = new File(fileOutputPath);
        outputPath.mkdir();

        if(files != null) {
            for (File fileEntry : files) {
                if (!fileEntry.isDirectory()) {
                    String fileOutputName = fileEntry.getName().replaceFirst("[.][^.]+$", "");

                    CryptoUtils.INSTANCE.encrypt(
                            keyTextField.getText(),
                            fileEntry,
                            new File(outputPath+"/"+fileOutputName+extension));
                }
            }
        }
    }

    private void makeOutputDir() {
        File file = new File(outputPath);
        file.mkdirs();
    }

    private boolean validadeFields() {
        boolean isValid = true;
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

        if (key.getBytes().length != 16  && key.getBytes().length != 24 && key.getBytes().length != 32) {
            JOptionPane.showMessageDialog(
                    keyTextField,
                    "The key needs to be 16, 24 or 32 bytes\nYour key lenght is: "+key.getBytes().length, "ERROR",
                    JOptionPane.ERROR_MESSAGE
            );
            isValid = false;
        }

        return isValid;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("File AES Encrypter");
        frame.setContentPane(new EncryptForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
