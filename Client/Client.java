package Client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Мастюкина Мария
 * Класс, работающий в качестве клиента
 */
public class Client {
    static Logger LOGGER;
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        LOGGER=Logger.getLogger(Client.class.getName());
        IOInterfaceStream ioClient = new IOTerminal(System.in, System.out);
        ServerConnection serverConnection = new ServerConnection();
        LOGGER.log(Level.INFO,"Начало работы клиента");
        if (args.length == 2) {
            LOGGER.log(Level.INFO,"Подключение к серверу");
            serverConnection.connection(args[0], args[1]);
        } else {
            LOGGER.log(Level.INFO,"Ошибка в соединении с сервером, неверный хост, порт");
            ioClient.writeln("введите корректный хост и порт");
            System.exit(0);
        }

        IOInterfaceStream ioServer = new IOTerminal(serverConnection.getInputStream(), serverConnection.getOutputStream());
        LOGGER.log(Level.INFO,"Ждем готовности сервера");
        LOGGER.log(Level.INFO,"Получаем спсиок команд с сервера");
        TransferObject transferObject = new TransferObject(ioServer, ioClient, serverConnection);
        User user= User.createUser(ioServer);
        ioClient.writeln("Здравстуйте! Введите \"help\", чтобы увидеть список доступных команд");
        ioClient.writeln("Введите команду");
        LOGGER.log(Level.INFO,"Считываем команду с консоли");
        while(true) {
            try {
                CommandObject command;
               String  line = ioClient.readLine();
                LOGGER.log(Level.INFO, "Создаем объект текущей команды");
                    if (line.equals("exit")){
                        user=User.createUser(ioServer);
                        ioClient.writeln("Здравстуйте! Введите \"help\", чтобы увидеть список доступных команд");
                        ioClient.writeln("Введите команду");
                        continue;
                    }
                    command = new CommandObject(line, null);
                    command.setLogin(user.getLogin());
                    command.setPassword(user.getPassword());
                    if (command.getChecker()) {
                        try {
                            LOGGER.log(Level.INFO, "Запускаем метод отправки командв на севрер");
                            transferObject.transfer(command);
                        } catch (StackOverflowError e) {
                            ioClient.writeln("Произошла зацикливнаие команды execute_script. Выполнение команды прекращено");
                        }
                    }
                    ioClient.writeln("Введите команду");
                    LOGGER.log(Level.INFO, "Считываем команду с консоли");
            } catch (NullPointerException e) {
                ioClient.writeln("Введите команду еще раз");
            }
        }
    }
}