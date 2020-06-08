package Server;

import Client.*;
import Server.Collection.CollectWorker;
import Server.Collection.ControlUnit;
import Server.Commands.Command;
import com.sun.javaws.Launcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.BindException;
import java.net.ConnectException;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Мастюкина Мария
 * Класс, работающий в качестве сервера
 */
public class Server {
    private static ControlUnit cu=new ControlUnit();
    public CommandObject currentCommand;
    private static ForkJoinPool readRequest=ForkJoinPool.commonPool();
    private static ExecutorService handleRequest= Executors.newCachedThreadPool();
    static Logger LOGGER;
//    private static String URL="jdbc:postgresql://pg/studs";
    private static String URL="jdbc:postgresql://localhost:5432/test1";
//    private static String login="s285598";
    private static String login="postgres";
//    private static String password="phn768";
    private static String password="270212";
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        ClientConnection clientConnection = null;
        IOInterfaceStream ioServer=new IOTerminal(System.in,System.out);
        LOGGER=Logger.getLogger(Server.class.getName());
        Integer PORT=Integer.parseInt(args[0]);
        try{
            LOGGER.log(Level.INFO,"Подключение к клиенту");
            clientConnection=new ClientConnection();
            clientConnection.connect(PORT);
        }
        catch(IndexOutOfBoundsException e){
            LOGGER.log(Level.WARNING,"Ошибка в подключении к клиенту, не указан порт");
            System.out.println("Нужно указать порт");
            System.exit(0);
        }
        catch (NumberFormatException e){
            LOGGER.log(Level.WARNING,"Ошибка в подключении к клиенту, формат порта указан неверно");
            System.out.println("Формат порта не верен");
            System.exit(0);
        }
        catch (BindException e){
            PORT++;
            clientConnection.connect(PORT);
        }
        HashMap<SelectionKey,Future<CommandObject>>to=new HashMap<>();
        HashMap<SelectionKey,Future<Request>>res=new HashMap<>();
        DBConnection dbConnection=new DBConnection();
        CollectionDB collectionDB=null;
        UserDB userDB=null;
        collectionDB=new CollectionDB(dbConnection.connectDB(URL,login,password),"CITIES");
        userDB=new UserDB(dbConnection.connectDB(URL,login,password),"USERS");
        CollectWorker collection=new CollectWorker(collectionDB.loadListFromDB());
        while(true) {
            try {
                if (ioServer.ready()) {
                    if (ioServer.readLine().equals("help")) {
                        System.out.println("help:вывести справку по доступным командам\n" +
                                "info:вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                                "show:вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                                "add:добавить новый элемент в коллекцию\n" +
                                "update id :обновить значение элемента коллекции, id которого равен заданному\n" +
                                "remove_by_id id:удалить элемент из коллекции по его id\n" +
                                "clear:очистить коллекцию\n" +
                                "save:сохранить коллекцию в файл\n" +
                                "execute_script file_name:считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                                "exit:завершить программу (без сохранения в файл)\n" +
                                "remove_last:удалить последний элемент из коллекции\n" +
                                "sort:отсортировать коллекцию в естественном порядке\n" +
                                "history:вывести последние 8 команд (без их аргументов)\n" +
                                "remove_all_by_meters_above_sea_level metersAboutSeaLevel:удалить из коллекции все элементы, значение поля metersAboveSeaLevel которого эквивалентно заданному\n" +
                                "group_counting_by_population:сгруппировать элементы коллекции по значению поля population, вывести количество элементов в каждой группе\n" +
                                "print_ascending:вывести элементы коллекции в порядке возрастания");
                    }

                }
            }catch(NullPointerException e){
                System.out.println("Завершение работы сервера");
                System.exit(1);
            }
            //к чему готов канал, получаем доступ к селектору
            clientConnection.getSelector().selectNow();
            Iterator iterator = clientConnection.getSelector().selectedKeys().iterator();
            if (iterator.hasNext()) {
                LOGGER.log(Level.INFO, "Получение текущего селектора");
                SelectionKey selectionKey = (SelectionKey) iterator.next();
                iterator.remove();
                try {
                    LOGGER.log(Level.INFO, "Проверка ключа селектора");
                    if (!selectionKey.isValid()) {
                        continue;
                    }
                    if (selectionKey.isWritable()) {
                            SocketChannel channel=(SocketChannel)selectionKey.channel();
                            LOGGER.log(Level.INFO, "Запуск полученный от клиента команды");
                            if (to.containsKey(selectionKey)&&to.get(selectionKey).isDone()){
                                CommandObject currentCommand=to.get(selectionKey).get();
                                    collection.setCollection(collectionDB.loadListFromDB());
                                res.put(selectionKey,handleRequest.submit(new Launch(collection,currentCommand,cu)));
                                to.remove(selectionKey);
                            }
                            if (res.containsKey(selectionKey)&&res.get(selectionKey).isDone()) {
                                new SendResponse(channel).sendResponse(res.get(selectionKey).get());
                                res.remove(selectionKey);
                                LOGGER.log(Level.INFO, "Ответ отправлен клиенту");
                                selectionKey.interestOps(SelectionKey.OP_READ);
                            }

                            }

                    if (selectionKey.isReadable()) {
                        SocketChannel channel=(SocketChannel) selectionKey.channel();
                        LOGGER.log(Level.INFO, "Чтение полученной от клиента команды");
                        to.put(selectionKey,readRequest.submit(new ReadRequest(channel)));
                        selectionKey.interestOps(SelectionKey.OP_WRITE);

                    }
                    if (selectionKey.isAcceptable()) {
                        LOGGER.log(Level.INFO, "Разрешение подключение клиента к севреру");
                        clientConnection.acceptConnection(selectionKey);
                    }
                } catch (ConnectException e) {
                    userDB.closeConnection();
                    collectionDB.closeConnection();

                    System.out.println(e.getMessage());
                }
                catch (CancelledKeyException e){
                    ioServer.writeln("Работа с текущем клиентом закончена. Ожидается подключение нового клиента");
                    clientConnection.sscClose();
                    clientConnection.connect(Integer.parseInt(args[0]));
                    collection=new CollectWorker(collectionDB.loadListFromDB());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
