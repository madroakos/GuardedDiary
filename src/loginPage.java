import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class loginPage implements ActionListener {
    JPanel panel = new JPanel();
    JFrame frame = new JFrame();
    passwordHashing passwordHashing;

    private static JTextField userText;
    private static JPasswordField passwordText;
    private static JButton loginButton;
    private static JButton registerButton;
    loginPage(passwordHashing passwordHashing) {
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

        loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        loginButton.addActionListener(this);
        panel.add(loginButton);

        registerButton = new JButton("Register");
        registerButton.setBounds(100, 80, 100, 25);
        registerButton.addActionListener(this);
        panel.add(registerButton);

        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {

            String user = userText.getText();
            String password = String.valueOf(passwordText.getPassword());

            try {
                if (passwordHashing.getUser().equals(user) &&
                        passwordHashing.getPassword().equals(passwordHashing.getHashOf(password))) {
                    this.frame.dispose();
                    new defaultPage();
                }
            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == registerButton) {
            try {
                RegisterPage registerPage = new RegisterPage(passwordHashing);
                registerPage.frame.setAlwaysOnTop(true);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
