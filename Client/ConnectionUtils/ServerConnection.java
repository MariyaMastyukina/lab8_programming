package Client.ConnectionUtils;

import Client.Client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Класс для соединения клиента с сервером
 */
public class ServerConnection {
    Socket socket;

    /**
     * Функция соединения клиента с сервером
     * @param host- имя хоста
     * @param PORT- порт
     */
    public Socket connection(String host, String PORT, Component component) throws IOException {
        try {
            //создает сокет, соединение с сервером
            socket = new Socket(host, Integer.parseInt(PORT));
            System.out.println("Соединение с сервером установлено");
            return socket;
        }
        catch(ConnectException|UnknownHostException e){
            JOptionPane.showMessageDialog(component,"Не удалось установить соединение с сервером","ОШИБКА", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        catch(NumberFormatException e){
            JOptionPane.showMessageDialog(component,"Введен некорректный порт","ОШИБКА", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    /**
     * Функция получения потока ввода
     */
    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }
    /**
     * Функция получения потока вывода
     */
    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }

    /**
     * Функция закрытия сокета
     */
    public void close() throws IOException {
        socket.close();
    }


}
