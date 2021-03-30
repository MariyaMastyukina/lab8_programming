package Client.IOClient;

import java.io.*;

/**
 * Класс, работающий с потоками ввода/вывода
 */
public class IOTerminal implements IOInterfaceStream {
    private OutputStream out;
    private InputStream in;
    private Writer writer;
    private Reader reader;
    private transient BufferedReader bufferedReader;
    public IOTerminal(OutputStream out, InputStream in) {
        this.out=out;
        this.in=in;
        reader=new InputStreamReader(in);
        writer= new OutputStreamWriter(out);
        bufferedReader = new BufferedReader(reader);
    }
    /**
     * Функция записи строки в заданный поток
     * @param str-строка
     */
    @Override
    public void writeln(String str) throws IOException {
        writer.write(str+"\n");
        writer.flush();
    }
    /**
     * Функция считывания строки в буфер ввода
     * @return считанную строку
     */
    @Override
    public String readLine() throws IOException {
        //читатет текст из потока и буферизирует прочитанные символы
        return bufferedReader.readLine();
    }

    /**
     * Функция проверки готовности ввода с потока
     * @return готовность ввода с потока
     */
    @Override
    public boolean ready() throws IOException {
        //готов ли поток быть считанным
        return bufferedReader.ready();
    }
    /**
     * Функция записи сериализованного объекта в поток
     * @param obj-заданный объект
     */
    @Override
    public void writeObj(Object obj) throws IOException {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        //записываем сериализованный объект в массив байтов, отправляем
        ObjectOutputStream oos=new ObjectOutputStream(baos);
        oos.writeObject(obj);
        //Создаем новый буферизованный поток вывода
        BufferedOutputStream bos=new BufferedOutputStream(out);
        bos.write(baos.toByteArray(),0,baos.toByteArray().length);
        //записываем из массива в поток
        bos.flush();
    }
    /**
     * Функция чтения сериализованного объекта с потока
     * @return  получение сериализованного объекта и его десериализация
     */
    @Override
    public Object readObj() throws IOException, ClassNotFoundException {
        //счиытваем сериализованный объект, десериализуем
        ObjectInputStream ois=new ObjectInputStream(in);
        return ois.readObject();
    }
    /**
     * Функция закрытия потока ввода/вывода
     */
    @Override
    public void close() throws IOException {
        out.close();
        in.close();
    }
}