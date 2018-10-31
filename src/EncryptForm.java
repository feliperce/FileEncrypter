import javax.swing.*;

public class EncryptForm {
    private JTextField fileTextField;
    private JButton encryptButton;
    private JPanel mainPanel;
    private JTextField cryptTextField;
    private JButton decryptButton;

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("EncryptForm");
        frame.setContentPane(new EncryptForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
