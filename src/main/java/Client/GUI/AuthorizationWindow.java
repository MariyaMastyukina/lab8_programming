package Client.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PipedWriter;

public class AuthorizationWindow extends JFrame {
    private PipedWriter commandWriter;
    private JLabel label;
    private JPanel buttonPanel;
    private JButton checkIn;
    private JButton signIn;
    private JPanel authPanel;
    private JButton exit;

    public AuthorizationWindow(PipedWriter commandWriter) {
        super("Авторизация");
        this.commandWriter = commandWriter;
        initComponents();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        setBounds(toolkit.getScreenSize().width / 3, toolkit.getScreenSize().height / 3, toolkit.getScreenSize().width / 3, toolkit.getScreenSize().height / 3);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(authPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void initComponents() {
        authPanel = new JPanel(new GridLayout(4, 1));
        label = new JLabel("Приветствую, дорогой пользователь!");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        authPanel.add(label);
        label = new JLabel("Если ты хочешь зарегаться, жми кнопку check_in");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        authPanel.add(label);
        label = new JLabel("Если ты хочешь зайти в свой аккаунт, жми кнопку sign_in");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        authPanel.add(label);
        label = new JLabel("Если же ты хочешь покинуть нас, придется нажать кнопку exit");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        authPanel.add(label);
        initButtons();
    }

    private void initButtons() {
        buttonPanel = new JPanel(new GridLayout(1, 3));
        checkIn = new JButton("check_in");
        signIn = new JButton("sign_in");
        exit = new JButton("exit");
        checkIn.addActionListener(e -> {
            try {
                commandWriter.write("check_in\n");
                commandWriter.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        signIn.addActionListener(e -> {
            try {
                commandWriter.write("sign_in\n");
                commandWriter.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        exit.addActionListener(e -> System.exit(1));
        buttonPanel.add(checkIn);
        buttonPanel.add(signIn);
        buttonPanel.add(exit);
    }

}
