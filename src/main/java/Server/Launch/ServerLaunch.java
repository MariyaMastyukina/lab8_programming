package Server.Launch;

import IO.IOInterfaceStream;
import IO.IOTerminal;
import Server.Server;
import Utils.ConnectionUtils.ClientConnectionUtils;
import Utils.ConnectionUtils.ReadRequestUtils;
import Utils.ConnectionUtils.Request;
import Utils.ConnectionUtils.SendResponseUtils;
import Utils.DBUtils.CityDAO;
import Utils.DBUtils.DBConnection;
import Utils.DBUtils.UserDAO;
import Utils.DataUtils.CommandUtils;

import java.io.IOException;
import java.net.ConnectException;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.*;

public class ServerLaunch {
    private static final Properties props = new Properties();
    private static final ControlUnit controlUnit = new ControlUnit();
    private static Integer port = null;
    private static final ForkJoinPool readRequest = ForkJoinPool.commonPool();
    private static final ExecutorService handleRequest = Executors.newCachedThreadPool();
    private static String URL;
    private static String login;
    private static String password;
    private static CityService cityService;
    private static ClientConnectionUtils clientConnectionUtils;
    private static IOInterfaceStream ioServer;
    private static HashMap<SelectionKey, Future<CommandUtils>> direction;
    private static HashMap<SelectionKey, Future<Request>> result;
    private static DBConnection dbConnection;
    private static CityDAO cityDAO;
    private static UserDAO userDAO;

    private static void init() throws IOException, SQLException {
        cityService = new CityService();
        clientConnectionUtils = new ClientConnectionUtils();
        ioServer = new IOTerminal(System.out, System.in);
        try {
            port = clientConnectionUtils.connect(ioServer, port);
            ioServer.writeln("Порт сервера: " + port);
            ioServer.writeln("Ожидается подключение клиента");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Нужно указать порт");
            System.exit(0);
        } catch (NumberFormatException e) {
            System.out.println("Формат порта не верен");
            System.exit(0);
        }
        initDB();
        direction = new HashMap<>();
        result = new HashMap<>();
    }

    private static void initDB() throws IOException, SQLException {
        props.load(Server.class.getClassLoader().getResourceAsStream("project.properties"));
        login = props.getProperty("DB.USERNAME");
        URL = props.getProperty("DB.URL");
        password = System.getProperty("DB.PASSWORD");
        dbConnection = new DBConnection();
        cityDAO = new CityDAO(dbConnection.connectDB(URL, login, password), "CITIES");
        userDAO = new UserDAO(dbConnection.connectDB(URL, login, password), "USERS");
    }

    public static void startServer() throws SQLException, IOException {
        init();
        while (true) {
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
            } catch (NullPointerException e) {
                System.out.println("Завершение работы сервера");
                System.exit(1);
            }
            //к чему готов канал, получаем доступ к селектору
            clientConnectionUtils.getSelector().selectNow();
            Iterator<SelectionKey> iterator = clientConnectionUtils.getSelector().selectedKeys().iterator();
            if (iterator.hasNext()) {
                SelectionKey selectionKey = (SelectionKey) iterator.next();
                iterator.remove();
                try {
                    if (!selectionKey.isValid()) {
                        continue;
                    }
                    if (selectionKey.isWritable()) {
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        if (direction.containsKey(selectionKey) && direction.get(selectionKey).isDone()) {
                            CommandUtils command = direction.get(selectionKey).get();
                            result.put(selectionKey, handleRequest.submit(new CommandLaunch(cityService, command, controlUnit)));
                            direction.remove(selectionKey);
                        }
                        if (result.containsKey(selectionKey) && result.get(selectionKey).isDone()) {
                            new SendResponseUtils(channel).sendResponse(result.get(selectionKey).get());
                            result.remove(selectionKey);
                            selectionKey.interestOps(SelectionKey.OP_READ);
                        }

                    }

                    if (selectionKey.isReadable()) {
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        direction.put(selectionKey, readRequest.submit(new ReadRequestUtils(channel)));
                        selectionKey.interestOps(SelectionKey.OP_WRITE);

                    }
                    if (selectionKey.isAcceptable()) {
                        clientConnectionUtils.acceptConnection(selectionKey);
                    }
                } catch (ConnectException e) {
                    userDAO.closeConnection();
                    cityDAO.closeConnection();

                    System.out.println(e.getMessage());
                } catch (CancelledKeyException e) {
                    ioServer.writeln("Работа с текущем клиентом закончена. Ожидается подключение нового клиента");
                    clientConnectionUtils.sscClose();
                    clientConnectionUtils.connect(ioServer, port);
                    cityService = new CityService();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
