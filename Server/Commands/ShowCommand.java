package Server.Commands;

import Client.DataUtils.CommandObject;
import Server.Launch.CollectWorker;
import Server.Launch.ControlUnit;
import Server.ConnectionUtils.Request;

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
    static int argsSize;
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
        argsSize=0;

    }
    /**
     * Функция выполнения команды
     */
    @Override
    public Request execute(CommandObject CO) throws IOException {
        LOGGER.log(Level.INFO,"Отправка результата выполнения команды на сервер");
        return new Request(coll.show(),null,null);
        }
    @Override
    public String getName() {
        return "remove_last";
    }

    @Override
    public int getargsSize() {
        return argsSize;
    }
    }
