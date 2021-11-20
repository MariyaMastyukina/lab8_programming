package Utils.ConnectionUtils;

import Server.Launch.ControlUnit;
import Utils.DataUtils.CommandUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.RecursiveTask;

public class ReadRequestUtils extends RecursiveTask<CommandUtils> {
    private SocketChannel channel;
    private static Object object;

    public ReadRequestUtils(SocketChannel channel) {
        this.channel = channel;
    }


    @Override
    protected CommandUtils compute() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(5 * 1024);
        try {
            channel.read(byteBuffer);
            object = new ObjectInputStream(new ByteArrayInputStream(byteBuffer.array())).readObject();
            return (CommandUtils) object;
        } catch (IOException | ClassNotFoundException e) {
            new ControlUnit().clearListCommand();
            System.out.println("Соединение с клиентом разорвано :(");
            try {
                channel.close();
            } catch (IOException ex) {
                System.out.println("Не удалось закрыть канал");
            }
            return null;

        }
    }

    public static Object getObject() {
        return object;
    }
}
