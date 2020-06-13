package Server.Commands;

import Client.CommandObject;
import Server.Collection.CollectWorker;
import Server.Collection.ControlUnit;
import Server.Request;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс команды history-вывод последних 8 команд
 */
public class HistoryCommand implements Command {
    static Logger LOGGER;
    private CollectWorker coll;
    TypeCommand type;
    static int argsSize;
    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param p- переменная для управления командами
     */
    private ControlUnit pusk;
    public HistoryCommand(ControlUnit p, CollectWorker coll){
        p.addCommand("history",this);
        this.pusk=p;
        this.coll=coll;
        LOGGER=Logger.getLogger(HistoryCommand.class.getName());
        type=TypeCommand.INFORM;
        argsSize=0;

    }
    /**
     * Функция выполнения команды
     */
    @Override
    public Request execute(CommandObject user) throws IOException {
        LOGGER.log(Level.INFO,"Отправка результата выполнения команды на сервер");
        return new Request(coll.history(pusk),null,null);
    }

    @Override
    public String getName() {
        return "history";
    }

    @Override
    public int getargsSize() {
        return argsSize;
    }
}
