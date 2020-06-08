package Server.Commands;

import Client.CommandObject;
import Server.Collection.CollectWorker;
import Server.Collection.ControlUnit;
import Server.Request;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс команды show-вывод элементов коллекции
 */
public class ShowCommand implements Command {
    CollectWorker coll;
    static Logger LOGGER;
    TypeCommand type;
    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param p- переменная для управления командами
     * @param collection- переменнаяи для работы с коллекцией
     */
    public ShowCommand(ControlUnit p, CollectWorker collection) {
        p.addCommand("show", this);
        this.coll = collection;
        LOGGER=Logger.getLogger(ShowCommand.class.getName());
        type=TypeCommand.INFORM;

    }
    /**
     * Функция выполнения команды
     */
    @Override
    public Request execute(CommandObject CO) throws IOException {
        LOGGER.log(Level.INFO,"Отправка результата выполнения команды на сервер");
        return new Request(coll.show(),null);
        }
    }
