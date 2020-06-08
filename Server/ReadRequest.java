package Server;

import Client.CommandObject;
import Server.Collection.CollectWorker;
import Server.Collection.ControlUnit;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ConnectException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class ReadRequest extends RecursiveTask<CommandObject> {
    private SocketChannel channel;
    private static Object object;
    public ReadRequest(SocketChannel channel){
        this.channel=channel;
    }


    @Override
    protected CommandObject compute() {
            //Выделяем новый буфер байта
            ByteBuffer byteBuffer=ByteBuffer.allocate(5*1024);
            try{
                channel.read(byteBuffer);
                object=new ObjectInputStream(new ByteArrayInputStream(byteBuffer.array())).readObject();
                return (CommandObject) object;
            }
            catch(IOException | ClassNotFoundException e){
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
