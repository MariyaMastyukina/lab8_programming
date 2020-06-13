package Server.Commands;

import Client.CommandObject;
import Server.Collection.CollectWorker;
import Server.Collection.ControlUnit;
import Server.CollectionDB;
import Server.Request;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс команды clear-очистка коллекции
 */
public class ClearCommand implements Command {
    CollectWorker coll;
    static Logger LOGGER;
    TypeCommand type;
    static int argsSize;
    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param p- переменная для управления командами
     * @param collection- переменнаяи для работы с коллекцией
     */
    public ClearCommand(ControlUnit p, CollectWorker collection){
        p.addCommand("clear",this);
        this.coll=collection;
        LOGGER=Logger.getLogger(ClearCommand.class.getName());
        type=TypeCommand.EDIT;
        argsSize=0;

    }
    /**
     * Функция выполнения команды
     */
    @Override
    public Request execute(CommandObject user) throws IOException, SQLException {
        LOGGER.log(Level.INFO, "Отправка результата выполнения команды на сервер");
        String result = CollectionDB.clearDB(user.getLogin());
        if (coll.getSize() == 0) {
            return new Request("Команда clear не выполнена. Коллекция пуста",null,null);
        } else {
            coll.clear(user.getLogin());
            if (result.isEmpty()) {
                return new Request("Команда clear выполнена. Коллекция очищена",coll.getCollection(),null);
            } else {
                return new Request("Команда clear выполнена, но было отказано в доступе к объектам с именами и id: \n" + result,coll.getCollection(),null);
            }
        }
    }
    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public int getargsSize() {
        return argsSize;
    }
    }
