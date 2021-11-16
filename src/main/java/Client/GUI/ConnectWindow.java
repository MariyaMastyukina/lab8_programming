package Client.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PipedWriter;

public class ConnectWindow extends JFrame {
    PipedWriter writer;
    JTextField port;
    JTextField host;

    public ConnectWindow(PipedWriter writer) {
        super("Подключение к серверу");
        this.writer = writer;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        setBounds(toolkit.getScreenSize().width / 5, toolkit.getScreenSize().height / 5, toolkit.getScreenSize().width / 5, toolkit.getScreenSize().height / 5);
        port = new JTextField();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(new JLabel("Введите необходимые данные"), BorderLayout.NORTH);
        JPanel panel = new JPanel(new GridLayout(2, 2, 0, 10));
        panel.add(new JLabel("Host"));
        panel.add(host = new JTextField());
        panel.add(new JLabel("PORT"));
        panel.add(port = new JTextField());
        add(panel, BorderLayout.CENTER);
        JButton button = new JButton("connect");
        button.addActionListener(e -> {
            try {
                writer.write(host.getText() + "\n");
                writer.write(port.getText() + "\n");
                writer.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        add(button, BorderLayout.SOUTH);
    }
}
