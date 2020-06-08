package Server.Commands;

import Client.CommandObject;
import Server.Collection.CollectWorker;
import Server.Collection.ControlUnit;
import Server.Request;

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
    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param p- переменная для управления командами
     * @param collection- переменнаяи для работы с коллекцией
     */
    public PrintAscendingCommand(ControlUnit p, CollectWorker collection){
        p.addCommand("print_ascending",this);
        this.coll=collection;
        LOGGER=Logger.getLogger(PrintAscendingCommand.class.getName());
        type=TypeCommand.INFORM;
    }
    /**
     * Функция выполнения команды
     */
    @Override
    public Request execute(CommandObject user) throws IOException {
        LOGGER.log(Level.INFO,"Отправка результата выполнения команды на сервер");
        return new Request(coll.print_ascending(),null);
    }
}
