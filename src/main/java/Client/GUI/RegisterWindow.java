package Client.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PipedWriter;

public class RegisterWindow extends JFrame {
    JTextField login;
    JPasswordField password;
    PipedWriter writer;

    public RegisterWindow(PipedWriter writer) {
        super("Регистрация");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        setBounds(toolkit.getScreenSize().width / 2, toolkit.getScreenSize().height / 5, toolkit.getScreenSize().width / 2, toolkit.getScreenSize().height / 5);
        this.writer = writer;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new GridLayout(2, 2, 0, 10));
        panel.add(new JLabel("Введите имя пользователя:\n" + "Пример: evreechka\n"));
        login = new JTextField();
        panel.add(login);
        panel.add(new JLabel("Введите пароль:\n" + "Пример: evreechka\n"));
        password = new JPasswordField();
        password.setEchoChar('*');
        panel.add(password);
        add(panel, BorderLayout.CENTER);
        JButton button = new JButton("check_in");
        button.addActionListener(e -> {
            try {
                writer.write(login.getText() + "\n");
                writer.write(password.getText() + "\n");
                writer.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        add(button, BorderLayout.SOUTH);
    }

}
