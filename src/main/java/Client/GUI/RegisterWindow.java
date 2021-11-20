package Client.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PipedWriter;

public class RegisterWindow extends JFrame {
    private JTextField loginField;
    private JPasswordField passwordField;
    private PipedWriter commandWriter;
    private JPanel registerPanel;
    private JButton checkIn;
    public RegisterWindow(PipedWriter commandWriter) {
        super("Регистрация");
        this.commandWriter = commandWriter;
        initComponents();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        setBounds(toolkit.getScreenSize().width / 2, toolkit.getScreenSize().height / 5, toolkit.getScreenSize().width / 2, toolkit.getScreenSize().height / 5);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(registerPanel, BorderLayout.CENTER);
        add(checkIn, BorderLayout.SOUTH);
    }
    private void initComponents() {
        registerPanel = new JPanel(new GridLayout(2, 2, 0, 10));
        loginField = new JTextField();
        passwordField = new JPasswordField();
        passwordField.setEchoChar('*');
        registerPanel.add(new JLabel("Введите имя пользователя:\n" + "Пример: evreechka\n"));
        registerPanel.add(loginField);
        registerPanel.add(new JLabel("Введите пароль:\n" + "Пример: evreechka\n"));
        registerPanel.add(passwordField);
        checkIn = new JButton("check_in");
        checkIn.addActionListener(e -> {
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
