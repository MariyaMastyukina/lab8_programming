package Client.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PipedWriter;

public class LoginWindow extends JFrame {
    private JTextField loginField;
    private JPasswordField passwordField;
    private PipedWriter commandWriter;
    private JPanel loginPanel;
    private JButton signIn;

    public LoginWindow(PipedWriter commandWriter) {
        super("Вход");
        this.commandWriter = commandWriter;
        initComponents();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        setBounds(toolkit.getScreenSize().width / 2, toolkit.getScreenSize().height / 5, toolkit.getScreenSize().width / 2, toolkit.getScreenSize().height / 5);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(loginPanel, BorderLayout.CENTER);
        add(signIn, BorderLayout.SOUTH);
    }

    private void initComponents() {
        loginPanel = new JPanel(new GridLayout(2, 2, 0, 10));
        loginField = new JTextField();
        loginPanel.add(new JLabel("Введите имя пользователя:\n" + "Пример: evreechka\n"));
        loginPanel.add(loginField);
        loginPanel.add(new JLabel("Введите пароль:\n" + "Пример: evreechka\n"));
        passwordField = new JPasswordField();
        passwordField.setEchoChar('*');
        loginPanel.add(passwordField);
        signIn = new JButton("sign_in");
        signIn.addActionListener(e -> {
            try {
                commandWriter.write(loginField.getText() + "\n");
                commandWriter.write(passwordField.getText() + "\n");
                commandWriter.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
