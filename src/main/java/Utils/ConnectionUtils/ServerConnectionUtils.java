package Utils.ConnectionUtils;

import Client.GUI.ConnectWindow;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerConnectionUtils {
    private Socket socket;

    public Socket connection(ConnectWindow connectWindow, BufferedReader bufferedReader) throws IOException {
        while (true) {
            try {
                String PORT = readPort(connectWindow, bufferedReader);
                socket = new Socket("localhost", Integer.parseInt(PORT));
                System.out.println("Соединение с сервером установлено");
                return socket;
            } catch (ConnectException | UnknownHostException e) {
                JOptionPane.showMessageDialog(connectWindow, "Не удалось установить соединение с сервером! Попробуйте ввести другой порт или exit для заверщения: ", "ОШИБКА", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }

    public void close() throws IOException {
        socket.close();
    }

    private String readPort(ConnectWindow connectWindow, BufferedReader bufferedReader) throws IOException {
        while (!bufferedReader.ready()) {
        }
        String PORT = bufferedReader.readLine();
        while (!PORT.matches("[0-9]{4}")) {
            JOptionPane.showMessageDialog(connectWindow, "Неверный формат!", "ОШИБКА!", JOptionPane.ERROR_MESSAGE);
            while (!bufferedReader.ready()) {
            }
            PORT = bufferedReader.readLine();
        }
        return PORT;
    }

}
