import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class RegisterPage implements ActionListener {
    passwordHashing passwordHashing;
    JPanel panel = new JPanel();
    JFrame frame = new JFrame();
    private static JTextField userText;
    private static JPasswordField passwordText;
    private static JPasswordField passwordTextAgain;
    private static JButton cancelButton;
    private static JButton registerButton;
    RegisterPage(passwordHashing passwordHashing) throws IOException {
        this.passwordHashing = passwordHashing;
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(panel);

        panel.setLayout(null);

        JLabel userLabel = new JLabel("User");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        userText = new JTextField();
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        passwordText = new JPasswordField();
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        JLabel passwordLabelAgain = new JLabel("Password again:");
        passwordLabelAgain.setBounds(10, 80, 80, 25);
        panel.add(passwordLabelAgain);

        passwordTextAgain = new JPasswordField();
        passwordTextAgain.setBounds(100, 80, 165, 25);
        panel.add(passwordTextAgain);

        registerButton = new JButton("Register");
        registerButton.setBounds(10, 120, 100, 25);
        registerButton.addActionListener(this);
        panel.add(registerButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(120, 120, 80, 25);
        cancelButton.addActionListener(this);
        panel.add(cancelButton);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancelButton) {
            frame.dispose();
        } else if (e.getSource() == registerButton) {
            if (!Arrays.equals(passwordText.getPassword(), passwordTextAgain.getPassword())) {
                JOptionPane.showMessageDialog(frame, "Passwords do not match!");
            } else if (!userText.getText().isEmpty() &&
                    passwordText.getPassword().length != 0 &&
                    passwordTextAgain.getPassword().length != 0) {
                frame.dispose();
                try {
                    passwordHashing.saveAccount(userText.getText(), String.valueOf(passwordText.getPassword()));
                } catch (IOException | NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
