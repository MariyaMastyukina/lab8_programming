package Server.Commands;

import Client.DataUtils.CommandObject;
import Server.ConnectionUtils.Request;
import Server.Launch.CollectWorker;
import Server.Launch.ControlUnit;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс команды print_ascending-вывод отсортированной коллекции
 */
public class PrintAscendingCommand implements Command {

    CollectWorker coll;
    static Logger LOGGER;
    TypeCommand type;
    static int argsSize;

    /**
     * Конструктор - создание нового объекта с определенными значениями
     *
     * @param p-          переменная для управления командами
     * @param collection- переменнаяи для работы с коллекцией
     */
    public PrintAscendingCommand(ControlUnit p, CollectWorker collection) {
        p.addCommand("print_ascending", this);
        this.coll = collection;
        LOGGER = Logger.getLogger(PrintAscendingCommand.class.getName());
        type = TypeCommand.INFORM;
        argsSize = 0;
    }

    /**
     * Функция выполнения команды
     */
    @Override
    public Request execute(CommandObject user) throws IOException {
        LOGGER.log(Level.INFO, "Отправка результата выполнения команды на сервер");
        return null;
    }

    @Override
    public String getName() {
        return "print_ascending";
    }

    @Override
    public int getargsSize() {
        return argsSize;
    }

}
