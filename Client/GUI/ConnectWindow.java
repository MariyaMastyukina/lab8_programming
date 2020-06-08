package Client.GUI;

import Client.IOInterfaceStream;
import Client.IOTerminal;
import Client.ServerConnection;
import Client.User;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

public class ConnectWindow extends JFrame{
    static PipedReader reader=new PipedReader();
    PipedWriter writer=new PipedWriter(reader);
    static BufferedReader br=new BufferedReader(reader);
    JTextField port;
    JTextField host;
    public ConnectWindow() throws IOException {
        super("Подключение к серверу");
        Toolkit toolkit=Toolkit.getDefaultToolkit();
        setBounds(toolkit.getScreenSize().width/5,toolkit.getScreenSize().height/5,toolkit.getScreenSize().width/5,toolkit.getScreenSize().height/5);
        port=new JTextField();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(new JLabel("Введите необходимые данные"),BorderLayout.NORTH);
        JPanel panel = new JPanel(new GridLayout(2,2,0,10));
        panel.add(new JLabel("Host"));
        panel.add(host = new JTextField());
        panel.add(new JLabel("PORT"));
        panel.add(port=new JTextField());
        add(panel,BorderLayout.CENTER);
        JButton button=new JButton("connect");
        button.addActionListener(e->{
            try {
                writer.write(host.getText()+"\n");
                writer.write(port.getText()+"\n");
                writer.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        add(button,BorderLayout.SOUTH);
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ConnectWindow app = new ConnectWindow();
        app.setVisible(true);
        while(!br.ready()){}
        ServerConnection serverConnection=new ServerConnection();
        serverConnection.connection(br.readLine(),br.readLine());
        IOInterfaceStream ioServer = new IOTerminal(serverConnection.getInputStream(), serverConnection.getOutputStream());
        app.setVisible(false);
        User.createUser(ioServer);

    }
}
