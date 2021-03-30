package Server.ConnectionUtils;

import Server.Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Класс, отвечающий за соединение сервера с клиентом
 */
public class ClientConnection {
    ServerSocketChannel ssc;
    Selector selector;
    SocketChannel channel;
    /**
     * Конструктор соединения с клиентом
     * @param PORT-порт
     */
    public void connect(Integer PORT) throws IOException {
        //создаем канал сервер сокета
        ssc=ServerSocketChannel.open();
        ssc.configureBlocking(false);
        //создаем адрес сокета и связываем его
        ssc.socket().bind(new InetSocketAddress(PORT));
        //создаем селектор
        selector=Selector.open();
        //регистрируем
        ssc.register(selector,SelectionKey.OP_ACCEPT);
    }
    /**
     * Функция получения селектора
     * @return селектор
     */
    public Selector getSelector(){
        return selector;
    }
    /**
     * Функция подтверждения соединения с клиентом
     */
    public void acceptConnection(SelectionKey key) throws IOException {
        //принимаем соединение к сокету канала
        ServerSocketChannel sschannel=(ServerSocketChannel)key.channel();
        channel=sschannel.accept();
        channel.configureBlocking(false);
        channel.register(selector,SelectionKey.OP_READ);
    }
    /**
     * Функция получения текущего канала
     * @return канал
     */
    public SocketChannel getChannel(){
        return channel;
    }
    public void sscClose() throws IOException {
        ssc.socket().close();
    }
}
