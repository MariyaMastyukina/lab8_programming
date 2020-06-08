package Client;

import java.io.IOException;

/**
 * {@code Client.IOInterfaceStream} содержит команды потока ввода/вывода
 */
public interface IOInterfaceStream {
    /**
     * Функция записи строки в поток
     * @param str-строка
     */
    void writeln(String str) throws IOException;
    /**
     * Функция считывания строки
     * @return считанную строку
     */
    String readLine() throws IOException;
    /**
     * Функция проверки готовности потока
     * @return готовность потока
     */
    boolean ready() throws IOException;
    /**
     * Функция записи сериализованного объекта
     * @param obj-заданный объект
     */
    void writeObj(Object obj) throws IOException;
    /**
     * Функция чтения сериализованного объекта
     * @return  получение сериализованного объекта и его десериализация
     */
    Object readObj() throws IOException, ClassNotFoundException;
    /**
     * Функция закрытия потока
     */
    void close() throws IOException;
}
