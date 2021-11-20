package IO;

import java.io.IOException;

public interface IOInterfaceStream {

    void writeln(String str) throws IOException;

    String readLine() throws IOException;

    boolean ready() throws IOException;

    void writeObj(Object obj) throws IOException;

    Object readObj() throws IOException, ClassNotFoundException;

    void close() throws IOException;
}
