package Client.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PipedWriter;

public class ConnectWindow extends JFrame {
    private PipedWriter commandWriter;
    private JTextField port;
    private JPanel connectionPanel;
    private JButton connectButton;

    public ConnectWindow(PipedWriter commandWriter) {
        super("Подключение к серверу");
        this.commandWriter = commandWriter;
        initComponents();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        setBounds(toolkit.getScreenSize().width / 5, toolkit.getScreenSize().height / 5, toolkit.getScreenSize().width / 5, toolkit.getScreenSize().height / 5);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(new JLabel("Введите необходимые данные"), BorderLayout.NORTH);
        add(connectionPanel, BorderLayout.CENTER);
        add(connectButton, BorderLayout.SOUTH);
    }

    private void initComponents() {
        port = new JTextField();
        connectionPanel = new JPanel(new GridLayout(1, 2, 0, 10));
        connectionPanel.add(new JLabel("PORT"));
        connectionPanel.add(port = new JTextField());
        connectButton = new JButton("connect");
        connectButton.addActionListener(e -> {
            try {
                commandWriter.write(port.getText() + "\n");
                commandWriter.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
