package Utils.ConnectionUtils;

import IO.IOInterfaceStream;

import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ClientConnectionUtils {
    private ServerSocketChannel ssc;
    private Selector selector;
    private SocketChannel channel;

    public int connect(IOInterfaceStream ioServer, Integer port) throws IOException {
        if (port == null)
            port = Integer.parseInt(readPort(ioServer));
        while (true) {
            try {
                ssc = ServerSocketChannel.open();
                ssc.configureBlocking(false);
                ssc.socket().bind(new InetSocketAddress(port));
                selector = Selector.open();
                ssc.register(selector, SelectionKey.OP_ACCEPT);
                break;
            } catch (BindException e) {
                port++;
            }
        }
        return port;
    }

    public Selector getSelector() {
        return selector;
    }

    public void acceptConnection(SelectionKey key) throws IOException {
        ServerSocketChannel sschannel = (ServerSocketChannel) key.channel();
        channel = sschannel.accept();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);
    }

    public void sscClose() throws IOException {
        ssc.socket().close();
    }

    private String readPort(IOInterfaceStream ioServer) throws IOException {
        ioServer.writeln("Введите порт сервера: ");
        String port = ioServer.readLine();
        while (!port.matches("[0-9]{4}")) {
            ioServer.writeln("Неверный формат!");
            ioServer.writeln("Введите порт сервера: ");
            port = ioServer.readLine();
        }
        return port;
    }
}
