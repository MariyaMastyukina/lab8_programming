package Server.ConnectionUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SendResponse {
    private SocketChannel channel;
    public SendResponse(SocketChannel channel){
        this.channel=channel;
    }

    public void sendResponse(Request answer) {
        Runnable run=()-> {
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            ObjectOutputStream oos;
            try {
                oos = new ObjectOutputStream(baos);
                oos.writeObject(answer);
                ByteBuffer byteBuffer=ByteBuffer.wrap(baos.toByteArray());
                    channel.write(byteBuffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        };
            Thread thread=new Thread(run);
            thread.start();
    }
}
