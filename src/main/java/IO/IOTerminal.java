package IO;

import java.io.*;

public class IOTerminal implements IOInterfaceStream {
    private OutputStream out;
    private InputStream in;
    private Writer writer;
    private Reader reader;
    private transient BufferedReader bufferedReader;

    public IOTerminal(OutputStream out, InputStream in) {
        this.out = out;
        this.in = in;
        reader = new InputStreamReader(in);
        writer = new OutputStreamWriter(out);
        bufferedReader = new BufferedReader(reader);
    }

    @Override
    public void writeln(String str) throws IOException {
        writer.write(str + "\n");
        writer.flush();
    }

    @Override
    public String readLine() throws IOException {
        return bufferedReader.readLine();
    }

    @Override
    public boolean ready() throws IOException {
        return bufferedReader.ready();
    }

    @Override
    public void writeObj(Object obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        BufferedOutputStream bos = new BufferedOutputStream(out);
        bos.write(baos.toByteArray(), 0, baos.toByteArray().length);
        bos.flush();
    }

    @Override
    public Object readObj() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(in);
        return ois.readObject();
    }

    @Override
    public void close() throws IOException {
        out.close();
        in.close();
    }
}