package Client;

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
    public Socket connection(String host,String PORT) throws IOException {
        try {
            //создает сокет, соединение с сервером
            socket = new Socket(host, Integer.parseInt(PORT));
            System.out.println("Соединение с сервером установлено");
            return socket;
        }
        catch(ConnectException|UnknownHostException e){
            System.out.println("Не удалось установить соединение с сервером");
            System.exit(0);
            return null;
        }
        catch(NumberFormatException e){
            System.out.println("Введен некорректный порт");
            System.exit(0);
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
